package test;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.graphics.camera.controller.Rotation3DController;
import jpize.graphics.util.SkyBox;
import jpize.io.context.JpizeApplication;
import jpize.math.util.EulerAngles;
import jpize.math.vecmath.matrix.Matrix4f;
import jpize.sdl.input.Key;

public class VecMathTest extends JpizeApplication{

    SkyBox skybox;

    Rotation3DController controller;
    EulerAngles rotation;

    Matrix4f projection;
    Matrix4f view;


    public void init(){
        skybox = new SkyBox();

        controller = new Rotation3DController();
        controller.setSpeed(0.25F);
        rotation = controller.getRotation();

        projection = new Matrix4f().setPerspective(Jpize.getAspect(), 1, 500, 70);
        view = new Matrix4f().setLookAt(rotation.getDir());

        // System.out.println(new Vec4f(1, 1, 1, 1).mulMat4(projection));

        // Gl.disable(GlTarget.CULL_FACE);
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
