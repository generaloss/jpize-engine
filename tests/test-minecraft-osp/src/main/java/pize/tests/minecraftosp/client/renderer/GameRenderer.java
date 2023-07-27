package pize.tests.minecraftosp.client.renderer;

import pize.app.AppAdapter;
import pize.tests.minecraftosp.Minecraft;
import pize.tests.minecraftosp.client.renderer.chat.ChatRenderer;
import pize.tests.minecraftosp.client.renderer.infopanel.InfoPanelRenderer;
import pize.tests.minecraftosp.client.renderer.level.LevelRenderer;
import pize.tests.minecraftosp.client.renderer.text.TextComponentBatch;

public class GameRenderer extends AppAdapter{
    
    private final Minecraft session;
    
    private final TextComponentBatch textComponentBatch;
    private final LevelRenderer levelRenderer;
    private final InfoPanelRenderer infoPanelRenderer;
    private final ChatRenderer chatRenderer;
    
    public GameRenderer(Minecraft session){
        this.session = session;
        
        textComponentBatch = new TextComponentBatch();
        levelRenderer = new LevelRenderer(this);
        infoPanelRenderer = new InfoPanelRenderer(this);
        chatRenderer = new ChatRenderer(this);
    }
    
    public Minecraft getSession(){
        return session;
    }
    
    
    @Override
    public void render(){
        textComponentBatch.updateScale();
        levelRenderer.render();
        infoPanelRenderer.render();
        chatRenderer.render();
    }
    
    @Override
    public void resize(int width, int height){
        levelRenderer.resize(width, height);
    }
    
    @Override
    public void dispose(){
        textComponentBatch.dispose();
        levelRenderer.dispose();
        infoPanelRenderer.dispose();
        chatRenderer.dispose();
    }
    
    
    public final TextComponentBatch getTextComponentBatch(){
        return textComponentBatch;
    }
    
    public LevelRenderer getWorldRenderer(){
        return levelRenderer;
    }
    
    public InfoPanelRenderer getInfoRenderer(){
        return infoPanelRenderer;
    }
    
    public ChatRenderer getChatRenderer(){
        return chatRenderer;
    }
    
}
