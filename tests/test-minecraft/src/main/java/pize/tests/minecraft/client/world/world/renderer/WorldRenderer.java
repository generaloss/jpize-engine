package pize.tests.minecraft.client.world.world.renderer;

import pize.Pize;
import pize.io.glfw.Key;
import pize.tests.minecraft.client.game.Session;
import pize.tests.minecraft.client.game.Renderer;
import pize.tests.minecraft.client.game.options.KeyMapping;
import pize.tests.minecraft.client.world.world.ClientWorld;

public class WorldRenderer implements Renderer{

    private final Session session;

    private ClientWorld renderWorld;
    private final GameCamera camera;


    public WorldRenderer(Session session){
        this.session = session;

        camera = new GameCamera(session.getOptions());
    }

    @Override
    public void render(){
        Key zoomKey = session.getOptions().getKey(KeyMapping.ZOOM);
        Key sprintKey = session.getOptions().getKey(KeyMapping.SPRINT);
        float fov = session.getOptions().getFov();

        if(Pize.isDown(zoomKey))
            camera.setFov(fov * 0.3F);
        else if(Pize.isDown(sprintKey))
            camera.setFov(fov * 0.9F);
        else if(Pize.isReleased(zoomKey, sprintKey))
            camera.setFov(fov);
    }

    @Override
    public void resize(int width, int height){
        camera.resize(width, height);
    }

    @Override
    public void dispose(){

    }


    public ClientWorld getRenderWorld(){
        return renderWorld;
    }

    public void setRenderWorld(ClientWorld world){
        this.renderWorld = world;

        // generate some maps etc..
    }


    public GameCamera getCamera(){
        return camera;
    }

}