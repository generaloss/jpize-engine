package jpize.tests.mcose.client.renderer;

import jpize.io.context.JpizeApplication;
import jpize.tests.mcose.client.Minecraft;
import jpize.tests.mcose.client.renderer.chat.ChatRenderer;
import jpize.tests.mcose.client.renderer.infopanel.ChunkInfoRenderer;
import jpize.tests.mcose.client.renderer.infopanel.InfoRenderer;
import jpize.tests.mcose.client.renderer.level.LevelRenderer;
import jpize.tests.mcose.client.renderer.text.TextComponentBatch;

public class GameRenderer extends JpizeApplication{
    
    private final Minecraft session;
    
    private final TextComponentBatch textComponentBatch;
    private final LevelRenderer levelRenderer;
    private final InfoRenderer infoRenderer;
    private final ChunkInfoRenderer chunkInfoRenderer;
    private final ChatRenderer chatRenderer;

    public GameRenderer(Minecraft session){
        this.session = session;
        
        textComponentBatch = new TextComponentBatch();
        levelRenderer = new LevelRenderer(this);
        infoRenderer = new InfoRenderer(this);
        chunkInfoRenderer = new ChunkInfoRenderer(this);
        chatRenderer = new ChatRenderer(this);
    }
    
    public Minecraft getSession(){
        return session;
    }
    
    
    @Override
    public void render(){
        textComponentBatch.updateScale();
        levelRenderer.render();
        infoRenderer.render();
        chatRenderer.render();
        chunkInfoRenderer.render();
    }
    
    @Override
    public void resize(int width, int height){
        levelRenderer.resize(width, height);
        chunkInfoRenderer.resize(width, height);
    }
    
    @Override
    public void dispose(){
        textComponentBatch.dispose();
        levelRenderer.dispose();
        infoRenderer.dispose();
        chatRenderer.dispose();
        chunkInfoRenderer.dispose();
    }
    
    
    public final TextComponentBatch getTextComponentBatch(){
        return textComponentBatch;
    }
    
    public LevelRenderer getWorldRenderer(){
        return levelRenderer;
    }
    
    public InfoRenderer getInfoRenderer(){
        return infoRenderer;
    }

    public ChunkInfoRenderer getChunkInfoRenderer(){
        return chunkInfoRenderer;
    }

    public ChatRenderer getChatRenderer(){
        return chatRenderer;
    }
    
}
