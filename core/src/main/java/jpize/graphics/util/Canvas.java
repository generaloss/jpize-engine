package jpize.graphics.util;

import jpize.Jpize;
import jpize.util.Disposable;
import jpize.graphics.camera.OrthographicCamera;
import jpize.gl.Gl;
import jpize.gl.glenum.GlTarget;
import jpize.graphics.texture.Pixmap;
import jpize.graphics.texture.Texture;
import jpize.graphics.util.batch.TextureBatch;

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
        this(Jpize.getWidth(), Jpize.getHeight());
    }


    public void render(){
        boolean cullFace = Gl.isEnabled(GlTarget.CULL_FACE);
        if(cullFace)
            Gl.disable(GlTarget.CULL_FACE);

        frameTexture.setPixmap(this);
        camera.update();
        batch.begin(camera);
        batch.draw(frameTexture, 0, 0, Jpize.getWidth(), Jpize.getHeight());
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
