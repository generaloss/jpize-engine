package jpize.tests.minecraft.renderer;

import jpize.graphics.util.batch.TextureBatch;
import jpize.tests.minecraft.Session;

public class GameRenderer implements Renderer{

    private final Session session;
    private final TextureBatch batch;

    public GameRenderer(Session session){
        this.session = session;

        batch = new TextureBatch();
    }

    @Override
    public void render(){
        batch.begin();
        session.getScreenManager().render(batch);
        session.getIngameGui().render(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height){ }

    @Override
    public void dispose(){
        batch.dispose();
    }
    
}
