package pize.tests.voxelgame.client.chat;

import pize.Pize;
import pize.app.Disposable;
import pize.graphics.font.BitmapFont;
import pize.graphics.font.FontLoader;
import pize.graphics.util.TextureUtils;
import pize.graphics.util.batch.TextureBatch;
import pize.tests.voxelgame.Main;

import java.util.Collection;

public class ChatRenderer implements Disposable{
    
    private static final float MSG_LIFE_TIME_SEC = 6;
    
    private final Main session;
    private final TextureBatch batch;
    private final BitmapFont font;
    private final int chatX, chatY;
    
    public ChatRenderer(Main session){
        this.session = session;
        batch = new TextureBatch();
        font = FontLoader.loadFnt("font/default.fnt");
        font.setScale(3);
        
        chatX = 10;
        chatY = 10;
    }
    
    public Main getSession(){
        return session;
    }
    
    
    public void render(){
        final Chat chat = session.getGame().getChat();
        final Collection<ChatMessage> messages = chat.getMessages();
        
        batch.begin();
        
        if(chat.isOpened()){
            final String enteringText = chat.getEnteringText();
            final float lineWidth = font.getLineWidth(enteringText);
            
            batch.setColor(0, 0, 0, 0.3);
            batch.draw(TextureUtils.quadTexture(), chatX, chatY, Math.max(lineWidth, Pize.getWidth() / 2F), font.getScaledLineHeight());
            
            final float cursorLineWidth = font.getLineWidth(enteringText.substring(0, chat.getCursorX()));
            
            drawText(enteringText, chatX, chatY, 1);
            batch.draw(TextureUtils.quadTexture(), chatX + cursorLineWidth, chatY, font.getScale(), font.getScaledLineHeight());
        }
        
        int pointer = 0;
        for(ChatMessage message: messages){
            double alpha = 1;
            if(!chat.isOpened()){
                if(message.getSeconds() < MSG_LIFE_TIME_SEC)
                    alpha = Math.min(1, MSG_LIFE_TIME_SEC - message.getSeconds());
                else
                    continue;
            }
            
            final float lineWidth = font.getLineWidth(message.getMessage());
            
            batch.setColor(0, 0, 0, 0.3 * alpha);
            batch.draw(TextureUtils.quadTexture(), chatX, chatY + pointer * font.getScaledLineHeight() + (chat.isOpened() ? font.getScaledLineHeight() + 10 : 0), Math.max(Pize.getWidth() / 2F, lineWidth), font.getScaledLineHeight());
            batch.resetColor();
            
            batch.setAlpha(alpha);
            drawText(message.getMessage(), chatX, chatY + pointer * font.getScaledLineHeight() + (chat.isOpened() ? font.getScaledLineHeight() + 10 : 0), alpha);
            pointer++;
        }
        
        batch.end();
        batch.setAlpha(1);
    }
    
    private void drawText(String text, float x, float y, double alpha){
        batch.setColor(0, 0, 0, alpha);
        font.drawText(batch, text, x + font.getScale(), y - font.getScale());
        
        batch.setColor(1, 1, 1, alpha);
        font.drawText(batch, text, x, y);
    }
    
    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }
    
}
