package pize.tests.voxelgame.client.chat;

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
            font.drawText(batch, enteringText, chatX, chatY);
            batch.draw(TextureUtils.quadTexture(), chatX + font.getLineWidth(enteringText), chatY, font.getScale(), font.getScaledLineHeight());
        }
        
        
        int pointer = 0;
        for(ChatMessage message: messages){
            if(!chat.isOpened()){
                if(message.getSeconds() < MSG_LIFE_TIME_SEC)
                    batch.setAlpha(Math.min(1, MSG_LIFE_TIME_SEC - message.getSeconds()));
                else
                    continue;
            }
            
            font.drawText(batch, message.getMessage(), chatX, chatY + pointer * font.getScaledLineHeight() + (chat.isOpened() ? font.getScaledLineHeight() + 10 : 0));
            pointer++;
        }
        
        batch.end();
        batch.setAlpha(1);
    }
    
    @Override
    public void dispose(){
        batch.dispose();
        font.dispose();
    }
    
}
