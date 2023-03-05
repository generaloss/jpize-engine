package glit.tests.minecraft.client.game;

import glit.graphics.util.batch.TextureBatch;
import glit.tests.minecraft.client.world.world.renderer.WorldRenderer;

public class GameRenderer implements Renderer{

    private final Session session;

    private final WorldRenderer worldRenderer;
    private final TextureBatch batch;


    public GameRenderer(Session session){
        this.session = session;

        worldRenderer = new WorldRenderer(session);
        batch = new TextureBatch();
    }


    @Override
    public void render(){
        worldRenderer.render();
        batch.begin();
        session.getScreenManager().render(batch);
        session.getIngameGui().render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height){
        worldRenderer.resize(width, height);
    }

    @Override
    public void dispose(){
        worldRenderer.dispose();
        batch.dispose();
    }


    public WorldRenderer getWorldRenderer(){
        return worldRenderer;
    }

}
