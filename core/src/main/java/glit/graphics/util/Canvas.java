package glit.graphics.util;

import glit.Glit;
import glit.context.Disposable;
import glit.graphics.gl.Gl;
import glit.graphics.gl.Target;
import glit.graphics.texture.Pixmap;
import glit.graphics.texture.Texture;
import glit.graphics.util.batch.TextureBatch;

public class Canvas extends Pixmap implements Disposable{

    private final TextureBatch batch;
    private final Texture frameTexture;


    public Canvas(int width, int height){
        super(width, height);

        batch = new TextureBatch();
        batch.flip(false, true);
        frameTexture = new Texture(this);
    }

    public Canvas(){
        this(Glit.getWidth(), Glit.getHeight());
    }


    public void render(){
        boolean cullFace = Gl.isEnabled(Target.CULL_FACE);
        if(cullFace)
            Gl.disable(Target.CULL_FACE);

        frameTexture.setPixmap(this);
        batch.draw(frameTexture, 0, 0, Glit.getWidth(), Glit.getHeight());
        batch.end();

        if(cullFace)
            Gl.enable(Target.CULL_FACE);
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
        frameTexture.resize(width, height);
    }

    @Override
    public void dispose(){
        batch.dispose();
        frameTexture.dispose();
    }

}
