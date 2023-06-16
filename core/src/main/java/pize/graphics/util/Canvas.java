package pize.graphics.util;

import pize.Pize;
import pize.app.Disposable;
import pize.graphics.camera.OrthographicCamera;
import pize.graphics.gl.Gl;
import pize.graphics.gl.Target;
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
        boolean cullFace = Gl.isEnabled(Target.CULL_FACE);
        if(cullFace)
            Gl.disable(Target.CULL_FACE);

        frameTexture.setPixmap(this);
        camera.update();
        batch.begin(camera);
        batch.draw(frameTexture, 0, 0, Pize.getWidth(), Pize.getHeight());
        batch.end();

        if(cullFace)
            Gl.enable(Target.CULL_FACE);
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
