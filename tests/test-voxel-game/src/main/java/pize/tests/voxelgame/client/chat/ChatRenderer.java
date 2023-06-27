package pize.tests.voxelgame.client.chat;

import pize.Pize;
import pize.app.Disposable;
import pize.graphics.util.TextureUtils;
import pize.graphics.util.batch.TextureBatch;
import pize.tests.voxelgame.VoxelGame;
import pize.tests.voxelgame.base.text.Component;
import pize.tests.voxelgame.base.text.TextComponentBatch;

import java.util.List;

public class ChatRenderer implements Disposable{
    
    private static final float MSG_LIFE_TIME_SEC = 6;
    
    private final VoxelGame session;
    private final TextureBatch batch;
    private final TextComponentBatch textBatch;
    private final int chatX, chatY;
    private float scroll;
    private float scrollMotion;
    
    public ChatRenderer(VoxelGame session){
        this.session = session;
        this.textBatch = session.getTextBatch();
        this.batch = textBatch.getBatch();
        
        chatX = 10;
        chatY = 10;
    }
    
    public VoxelGame getSession(){
        return session;
    }
    
    
    public void render(){
        batch.begin();
        
        final float chatHeight = Pize.getHeight() / 2F;
        final float chatWidth = Pize.getWidth() / 2F;
        final float lineHeight = textBatch.getFont().getScaledLineHeight();
        
        final Chat chat = session.getGame().getChat();
        final List<ChatMessage> messages = chat.getMessages();
        
        final float openedChatY = chatY + (chat.isOpened() ? lineHeight + 10 : 0);
        float chatMessagesHeight = 0;
        for(ChatMessage message: messages)
            chatMessagesHeight += textBatch.getFont().getBounds(message.getComponents().toString(), chatWidth).y;
        
        // Enter
        if(chat.isOpened()){
            final String enteringText = chat.getEnteringText();
            final float lineWidth = textBatch.getFont().getLineWidth(enteringText);
            
            batch.setColor(0, 0, 0, 0.3);
            batch.draw(TextureUtils.quadTexture(), chatX, chatY, Math.max(lineWidth, chatWidth), lineHeight);
            
            final float cursorLineWidth = textBatch.getFont().getLineWidth(enteringText.substring(0, chat.getCursorX()));
            
            textBatch.drawText(new Component().text(enteringText).toFlatList(), chatX, chatY, -1, 1);
            
            batch.resetColor();
            batch.draw(TextureUtils.quadTexture(), chatX + cursorLineWidth, chatY, textBatch.getFont().getScale(), lineHeight);
        }
        
        // Scroll
        if(chat.isOpened() && Pize.mouse().isInBounds(chatX, openedChatY, chatWidth, chatHeight))
            scrollMotion -= Pize.mouse().getScroll() * Pize.getDt() * lineHeight * 10;
        
        scroll += scrollMotion;
        scrollMotion *= 0.95;
        
        if(!chat.isOpened())
            scroll = 0;
        
        scroll = Math.min(0, scroll);
        scroll = Math.max(Math.min(0, chatHeight - chatMessagesHeight), scroll);
        
        textBatch.getBatch().getScissor().begin(0, chatX, openedChatY, chatWidth, chatHeight);
        
        // Chat
        int textAdvanceY = 0;
        for(int i = messages.size() - 1; i >= 0; i--){
            final ChatMessage message = messages.get(i);
            
            float alpha = 1F;
            if(!chat.isOpened()){
                if(message.getSeconds() < MSG_LIFE_TIME_SEC)
                    alpha = (float) Math.min(1, MSG_LIFE_TIME_SEC - message.getSeconds());
                else
                    continue;
            }
            
            final float textWidth = Math.min(chatWidth, textBatch.getFont().getLineWidth(message.getComponents().toString()));
            final float textHeight = textBatch.getFont().getBounds(message.getComponents().toString(), chatWidth).y;
            final float renderChatY = openedChatY + textAdvanceY + scroll;
            
            batch.setColor(0, 0, 0, 0.3 * alpha);
            batch.draw(TextureUtils.quadTexture(), chatX, renderChatY, Math.max(chatWidth, textWidth), textHeight);
            
            textBatch.drawText(message.getComponents(), chatX, renderChatY + textHeight - lineHeight, chatWidth, alpha);
            
            textAdvanceY += textHeight;
        }
        
        batch.end();
        textBatch.getBatch().getScissor().end(0);
    }
    
    @Override
    public void dispose(){
        batch.dispose();
    }
    
}
