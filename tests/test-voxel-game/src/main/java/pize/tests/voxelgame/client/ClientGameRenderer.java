package pize.tests.voxelgame.client;

import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.level.render.LevelRenderer;
import pize.app.AppAdapter;
import pize.graphics.gl.DepthFunc;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Target;

public class ClientGameRenderer extends AppAdapter{
    
    private final Main sessionOF;
    private final LevelRenderer levelRenderer;
    private final InfoRenderer infoRenderer;
    
    public ClientGameRenderer(Main sessionOF){
        this.sessionOF = sessionOF;
        
        levelRenderer = new LevelRenderer(sessionOF);
        infoRenderer = new InfoRenderer(sessionOF);
    }
    
    public Main getSessionOf(){
        return sessionOF;
    }
    
    
    @Override
    public void init(){
        Gl.enable(Target.DEPTH_TEST);
        Gl.depthFunc(DepthFunc.LEQUAL);
    }
    
    @Override
    public void render(){
        levelRenderer.render();
        infoRenderer.render();
    }
    
    @Override
    public void resize(int width, int height){
        levelRenderer.resize(width, height);
    }
    
    @Override
    public void dispose(){
        levelRenderer.dispose();
        infoRenderer.dispose();
    }
    
    
    public LevelRenderer getWorldRenderer(){
        return levelRenderer;
    }
    
    public InfoRenderer getInfoRenderer(){
        return infoRenderer;
    }
    
}
