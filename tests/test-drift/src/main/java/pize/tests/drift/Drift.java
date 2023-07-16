package pize.tests.drift;

import pize.Pize;
import pize.app.AppAdapter;
import pize.graphics.camera.CenteredOrthographicCamera;
import pize.graphics.gl.Gl;
import pize.graphics.texture.Texture;
import pize.graphics.util.batch.TextureBatch;
import pize.io.glfw.Key;

public class Drift extends AppAdapter{

    public static void main(String[] args){
        Pize.create("Drift", 2000, 1000);
        Pize.run(new Drift());
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
            Pize.exit();

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
