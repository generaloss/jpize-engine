package pize.graphics.util;

import pize.Pize;
import pize.util.Disposable;
import pize.graphics.camera.OrthographicCamera;
import pize.gl.Gl;
import pize.gl.glenum.GlTarget;
import pize.graphics.texture.Pixmap;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;

public class Canvas extends Pixmap implements Disposable{

    private final TextureBatch batch;
    private final OrthographicCamera camera;
    private final Texture frameTexture;


    public Canvas(int width, int height){
        super(width, height);

        batch = new TextureBatch();
        camera = new OrthographicCamera();
        batch.flip(false, true);
        frameTexture = new Texture(this);
    }

    public Canvas(){
        this(Pize.getWidth(), Pize.getHeight());
    }


    public void render(){
        boolean cullFace = Gl.isEnabled(GlTarget.CULL_FACE);
        if(cullFace)
            Gl.disable(GlTarget.CULL_FACE);

        frameTexture.setPixmap(this);
        camera.update();
        batch.begin(camera);
        batch.draw(frameTexture, 0, 0, Pize.getWidth(), Pize.getHeight());
        batch.end();

        if(cullFace)
            Gl.enable(GlTarget.CULL_FACE);
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
        frameTexture.resize(width, height);
        camera.resize(width, height);
    }

    @Override
    public void dispose(){
        batch.dispose();
        frameTexture.dispose();
    }

}
