package pize.tests.minecraftosp.client.renderer.infopanel;

import pize.app.Disposable;
import pize.app.Resizable;
import pize.graphics.camera.CenteredOrthographicCamera;
import pize.graphics.util.batch.TextureBatch;
import pize.tests.minecraftosp.client.control.camera.GameCamera;
import pize.tests.minecraftosp.client.renderer.GameRenderer;
import pize.tests.minecraftosp.server.level.ServerLevel;

import java.util.Collection;

public class ChunkInfoRenderer implements Resizable, Disposable{

    private final GameRenderer gameRenderer;
    private final TextureBatch batch;
    private final CenteredOrthographicCamera camera;
    private boolean open;

    public ChunkInfoRenderer(GameRenderer gameRenderer){
        this.gameRenderer = gameRenderer;
        this.batch = new TextureBatch();
        this.camera = new CenteredOrthographicCamera();
    }

    public GameRenderer getGameRenderer(){
        return gameRenderer;
    }


    public void render(){
        if(!open)
            return;

        final GameCamera gameCam = gameRenderer.getSession().getGame().getCamera();
        if(gameCam == null)
            return;

        final float size = 20;

        camera.update();
        camera.getPosition().set(gameCam.getPosition().xz().mul(1 / 16F * size));
        camera.setRotation(-gameCam.getRotation().yaw + 90);
        batch.begin(camera);

        batch.setAlpha(0.3);

        final Collection<ServerLevel> levels = gameRenderer.getSession().getIntegratedServer().getLevelManager().getLoadedLevels();
        for(ServerLevel level: levels)
            level.getChunkManager().render(batch, size);

        batch.end();
    }


    public boolean isOpen(){
        return open;
    }

    public void setOpen(boolean open){
        this.open = open;
    }


    @Override
    public void resize(int width, int height){
        camera.resize(width, height);
    }

    @Override
    public void dispose(){
        batch.dispose();
    }

}