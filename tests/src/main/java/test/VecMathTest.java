package test;

import jpize.Jpize;
import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.io.EulerRotCtrl;
import jpize.graphics.util.SkyBox;
import jpize.sdl.input.Key;
import jpize.util.math.util.EulerAngles;
import jpize.util.math.vecmath.matrix.Matrix4f;

public class VecMathTest extends JpizeApplication{

    SkyBox skybox;

    EulerRotCtrl controller;
    EulerAngles rotation;

    Matrix4f projection;
    Matrix4f view;


    public void init(){
        skybox = new SkyBox();

        rotation = new EulerAngles();

        controller = new EulerRotCtrl(rotation);
        controller.setSpeed(0.25F);

        projection = new Matrix4f().setPerspective(Jpize.getAspect(), 1, 500, 70);
        view = new Matrix4f().setLookAt(rotation.getDir());
    }

    @Override
    public void update(){
        controller.update();

        if(Key.NUM_9.isPressed()) rotation.roll += 0.2F;
        if(Key.NUM_0.isPressed()) rotation.roll -= 0.2F;
        if(Key.ESCAPE.isDown()) Jpize.exit();
    }

    @Override
    public void render(){
        Gl.clearColorDepthBuffers();
        // Quaternion quaternion = new Quaternion();
        // quaternion.set(rotation);
        // view.identity().rotate(quaternion);
        view.setLookAt(rotation.getDir());

        skybox.render(projection, view);
    }

}
