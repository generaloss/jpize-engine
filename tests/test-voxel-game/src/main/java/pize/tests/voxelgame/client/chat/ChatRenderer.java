package pize.tests.voxelgame.client.chat;

import pize.Pize;
import pize.app.Disposable;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.util.TextureUtils;
import pize.graphics.util.batch.TextureBatch;
import pize.math.Maths;
import pize.tests.voxelgame.VoxelGame;

import java.util.List;

public class ChatRenderer implements Disposable{
    
    private static final float MSG_LIFE_TIME_SEC = 6;
    
    private final VoxelGame session;
    private final TextureBatch batch;
    private final BitmapFont font;
    private final int chatX, chatY;
    private float scroll;
    private float scrollMotion;
    
    public ChatRenderer(VoxelGame session){
        this.session = session;
        batch = new TextureBatch();
        font = FontLoader.loadFnt("font/default.fnt");
        
        chatX = 10;
        chatY = 10;
    }
    
    public VoxelGame getSession(){
        return session;
    }
    
    
    public void render(){
        font.setScale(Maths.round(Pize.getHeight() / 300F));
        
        final float chatHeight = Pize.getHeight() / 2F;
        final float chatWidth = Pize.getWidth() / 2F;
        final float lineHeight = font.getScaledLineHeight();
        
        final Chat chat = session.getGame().getChat();
        final List<ChatMessage> messages = chat.getMessages();
        
        final float openedChatY = chatY + (chat.isOpened() ? lineHeight + 10 : 0);
        float chatMessagesHeight = 0;
        for(ChatMessage message: messages)
            chatMessagesHeight += font.getBounds(message.getMessage(), chatWidth).y;
        
        batch.begin();
        
        // Enter
        if(chat.isOpened()){
            final String enteringText = chat.getEnteringText();
            final float lineWidth = font.getLineWidth(enteringText);
            
            batch.setColor(0, 0, 0, 0.3);
            batch.draw(TextureUtils.quadTexture(), chatX, chatY, Math.max(lineWidth, chatWidth), lineHeight);
            
            final float cursorLineWidth = font.getLineWidth(enteringText.substring(0, chat.getCursorX()));
            
            drawText(enteringText, chatX, chatY, 1, -1);
            batch.draw(TextureUtils.quadTexture(), chatX + cursorLineWidth, chatY, font.getScale(), lineHeight);
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
        
        batch.getScissor().begin(0, chatX, openedChatY, chatWidth, chatHeight);
        
        // Chat
        int textAdvanceY = 0;
        for(int i = messages.size() - 1; i >= 0; i--){
            final ChatMessage message = messages.get(i);
            
            double alpha = 1;
            if(!chat.isOpened()){
                if(message.getSeconds() < MSG_LIFE_TIME_SEC)
                    alpha = Math.min(1, MSG_LIFE_TIME_SEC - message.getSeconds());
                else
                    continue;
            }
            
            final float textWidth = Math.min(chatWidth, font.getLineWidth(message.getMessage()));
            final float textHeight = font.getBounds(message.getMessage(), chatWidth).y;
            final float renderChatY = openedChatY + textAdvanceY + scroll;
            
            batch.setColor(0, 0, 0, 0.3 * alpha);
            batch.draw(TextureUtils.quadTexture(), chatX, renderChatY, Math.max(chatWidth, textWidth), textHeight);
            
            batch.setAlpha(alpha);
            drawText(message.getMessage(), chatX, renderChatY + textHeight - lineHeight, alpha, chatWidth);
            
            textAdvanceY += textHeight;
        }
        
        batch.getScissor().end(0);
        batch.end();
        batch.setAlpha(1);
    }
    
    private void drawText(String text, float x, float y, double alpha, float width){
        batch.setColor(0, 0, 0, alpha);
        font.drawText(batch, text, x + font.getScale(), y - font.getScale(), width);
        
        batch.setColor(1, 1, 1, alpha);
        font.drawText(batch, text, x, y, width);
    }
    
    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }
    
}
