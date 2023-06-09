package pize.tests.voxelgame.client;

import pize.tests.voxelgame.Main;
import pize.tests.voxelgame.client.world.render.WorldRenderer;
import pize.activity.ActivityListener;
import pize.graphics.gl.DepthFunc;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Target;

public class ClientGameRenderer implements ActivityListener{
    
    private final Main sessionOF;
    private final WorldRenderer worldRenderer;
    private final InfoRenderer infoRenderer;
    
    public ClientGameRenderer(Main sessionOF){
        this.sessionOF = sessionOF;
        
        worldRenderer = new WorldRenderer(sessionOF);
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
        worldRenderer.render();
        infoRenderer.render();
    }
    
    @Override
    public void resize(int width, int height){
        worldRenderer.resize(width, height);
    }
    
    @Override
    public void dispose(){
        worldRenderer.dispose();
        infoRenderer.dispose();
    }
    
    
    public WorldRenderer getWorldRenderer(){
        return worldRenderer;
    }
    
    public InfoRenderer getInfoRenderer(){
        return infoRenderer;
    }
    
}
