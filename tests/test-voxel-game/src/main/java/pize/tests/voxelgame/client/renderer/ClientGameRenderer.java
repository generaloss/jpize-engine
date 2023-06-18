package pize.tests.voxelgame.client.renderer;

import pize.app.AppAdapter;
import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.chat.ChatRenderer;
import pize.tests.voxelgame.client.level.render.LevelRenderer;

public class ClientGameRenderer extends AppAdapter{
    
    private final Main session;
    
    private final LevelRenderer levelRenderer;
    private final InfoRenderer infoRenderer;
    private final ChatRenderer chatRenderer;
    
    public ClientGameRenderer(Main session){
        this.session = session;
        
        levelRenderer = new LevelRenderer(session);
        infoRenderer = new InfoRenderer(session);
        chatRenderer = new ChatRenderer(session);
    }
    
    public Main getSession(){
        return session;
    }
    
    
    @Override
    public void render(){
        levelRenderer.render();
        infoRenderer.render();
        chatRenderer.render();
    }
    
    @Override
    public void resize(int width, int height){
        levelRenderer.resize(width, height);
    }
    
    @Override
    public void dispose(){
        levelRenderer.dispose();
        infoRenderer.dispose();
        chatRenderer.dispose();
    }
    
    
    public LevelRenderer getWorldRenderer(){
        return levelRenderer;
    }
    
    public InfoRenderer getInfoRenderer(){
        return infoRenderer;
    }
    
    public ChatRenderer getChatRenderer(){
        return chatRenderer;
    }
    
}
