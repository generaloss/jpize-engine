package pize.tests.voxelgame.client.renderer.chat;

import pize.Pize;
import pize.app.Disposable;
import pize.graphics.texture.Region;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.math.Maths;
import pize.tests.voxelgame.client.chat.Chat;
import pize.tests.voxelgame.client.chat.ChatMessage;
import pize.tests.voxelgame.client.renderer.ClientGameRenderer;
import pize.tests.voxelgame.client.renderer.text.TextComponentBatch;
import pize.tests.voxelgame.main.text.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatRenderer implements Disposable{
    
    private static final float MSG_LIFE_TIME_SEC = 6;
    
    private final ClientGameRenderer gameRenderer;
    private final TextureBatch batch;
    private final TextComponentBatch textBatch;
    private final int chatX, chatY;
    private float scroll;
    private float scrollMotion;
    
    private final Map<Integer, Texture> skins;
    private final Region headRegion, hatRegion;
    
    public ChatRenderer(ClientGameRenderer gameRenderer){
        this.gameRenderer = gameRenderer;
        this.textBatch = gameRenderer.getTextComponentBatch();
        this.batch = textBatch.getBatch();
        
        chatX = 10;
        chatY = 10;
        
        skins = new HashMap<>();
        for(int i = 1; i <= 21; i++)
            skins.put(i, new Texture("texture/skin/skin" + i + ".png"));
        
        headRegion = new Region(skins.get(1), 8, 8, 8, 8);
        hatRegion = new Region(skins.get(1), 40, 8, 8, 8);
    }
    
    
    public void render(){
        batch.begin();
        textBatch.setBackgroundColor(0, 0, 0, 0);
        
        final float chatHeight = Pize.getHeight() / 2F;
        final float chatWidth = Pize.getWidth() / 2F;
        final float lineAdvance = textBatch.getFont().getLineAdvanceScaled();
        
        final Chat chat = gameRenderer.getSession().getGame().getChat();
        final List<ChatMessage> messages = chat.getMessages();
        
        final float openedChatY = chatY + (chat.isOpened() ? lineAdvance + 10 : 0);
        float chatMessagesHeight = 0;
        for(ChatMessage message: messages)
            chatMessagesHeight += textBatch.getFont().getBounds(message.getComponents().toString(), chatWidth).y;
        
        // Enter
        if(chat.isOpened()){
            final String enteringText = chat.getEnteringText();
            final float lineWidth = textBatch.getFont().getLineWidth(enteringText);
            
            batch.drawQuad(0.4, chatX, chatY, Math.max(lineWidth, chatWidth), lineAdvance);
            
            final float cursorLineWidth = textBatch.getFont().getLineWidth(enteringText.substring(0, chat.getCursorX()));
            
            textBatch.drawComponent(new Component().text(enteringText), chatX, chatY);
            
            batch.drawQuad(1, 1, 1, 1, chatX + cursorLineWidth, chatY, textBatch.getFont().getScale(), lineAdvance);
        }
        
        // Scroll
        if(chat.isOpened() && Pize.mouse().isInBounds(chatX, openedChatY, chatWidth, chatHeight))
            scrollMotion -= Pize.mouse().getScroll() * Pize.getDt() * lineAdvance * 10;
        
        scroll += scrollMotion;
        scrollMotion *= 0.95;
        
        if(!chat.isOpened())
            scroll = 0;
        
        scroll = Math.min(0, scroll);
        scroll = Math.max(Math.min(0, chatHeight - chatMessagesHeight), scroll);
        
        batch.getScissor().begin(0, chatX, openedChatY, chatWidth, chatHeight); // Scissors begin
        
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
            
            final String messageText = message.getComponents().toString();
            final float textWidth = Math.min(chatWidth, textBatch.getFont().getLineWidth(messageText));
            final float textHeight = textBatch.getFont().getBounds(message.getComponents().toString(), chatWidth).y;
            final float renderChatY = openedChatY + textAdvanceY + scroll;
            final float lineWrapAdvanceY = textHeight - lineAdvance;
            
            
            final boolean isPlayer = message.getSource().isPlayer();
            final float headSize = lineAdvance;
            final float headAdvance = headSize * 1.5F;
            final float headDrawX = (headAdvance - headSize) / 2;
            final float headDrawY = (lineAdvance - headSize) / 2;
            
            // Render background
            batch.drawQuad(0.4 * alpha, chatX, renderChatY, Math.max(chatWidth, textWidth) + headAdvance, textHeight);
            
            // Render head
            if(isPlayer){
                final int skinID = Maths.abs(message.getSource().asPlayer().getPlayerName().hashCode()) % 20 + 1;
                final Texture skin = skins.get(skinID);
                
                batch.setAlpha(alpha);
                batch.draw(skin, chatX + headDrawX, renderChatY + lineWrapAdvanceY + headDrawY, headSize, headSize, headRegion);
                
                batch.setTransformOrigin(0.5, 0.5);
                batch.scale(1.1F);
                batch.draw(skin, chatX + headDrawX, renderChatY + lineWrapAdvanceY + headDrawY, headSize, headSize, hatRegion);
                batch.scale(1);
            }
            // Render text
            textBatch.drawComponents(message.getComponents(), chatX + headAdvance, renderChatY + lineWrapAdvanceY, chatWidth, alpha);
            
            textAdvanceY += textHeight;
        }
        
        batch.end();
        batch.getScissor().end(0); // Scissors end
    }
    
    @Override
    public void dispose(){
        batch.dispose();
    }
    
}
