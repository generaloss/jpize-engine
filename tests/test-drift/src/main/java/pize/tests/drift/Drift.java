package pize.tests.drift;

import pize.Jize;
import pize.io.context.ContextAdapter;
import pize.graphics.camera.CenteredOrthographicCamera;
import pize.gl.Gl;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.glfw.key.Key;
import pize.io.context.ContextBuilder;

public class Drift extends ContextAdapter{

    public static void main(String[] args){
        ContextBuilder.newContext("Window")
                .size(2000, 1000)
                .create()
                .init(new Drift());

        Jize.runContexts();
    }


    private final Car car;
    private final TextureBatch batch;
    private final CenteredOrthographicCamera camera;
    private final Texture roadTexture;

    public Drift(){
        car = new Car();
        batch = new TextureBatch();
        camera = new CenteredOrthographicCamera();
        roadTexture = new Texture("road.png");
    }

    public void update(){
        if(Key.ESCAPE.isDown())
            Jize.exit();

        car.update();

        camera.getPosition().set(car.getPosition());//.rotDeg(-car.getRotation()).add(0, 130);
        //camera.setRotation(-car.getRotation());
        camera.setScale(0.4F);
        camera.update();
    }

    public void render(){
        Gl.clearColorBuffer();
        Gl.clearColor(0.2, 0.2, 0.3, 1);
        batch.begin(camera);

        batch.draw(roadTexture, -200, -2000, 400, 4000);
        car.render(batch);

        batch.end();
    }

    public void resize(int width, int height){
        camera.resize(width, height);
    }

    public void dispose(){
        car.dispose();
        batch.dispose();
        roadTexture.dispose();
    }

}
