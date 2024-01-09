package test.devs;

import jpize.Jpize;
import jpize.gl.Gl;
import jpize.graphics.camera.Camera3D;
import jpize.graphics.camera.PerspectiveCamera;
import jpize.graphics.camera.ctrl.MotionCtrl;
import jpize.graphics.camera.ctrl.EulerRotCtrl;
import jpize.io.context.JpizeApplication;

import java.util.ArrayList;
import java.util.List;

public class Scene3D extends JpizeApplication{

    private final Camera3D camera;
    private final MotionCtrl motionCtrl;
    private final EulerRotCtrl rotCtrl;
    private final List<MeshInstance> meshes;

    public Scene3D(){
        this.camera = new PerspectiveCamera(0.1, 1000, 70);
        this.motionCtrl = new MotionCtrl();
        this.rotCtrl = new EulerRotCtrl(camera.getRotation());
        this.meshes = new ArrayList<>();
    }


    @Override
    public void render(){
        Gl.clearColorDepthBuffers();
        Gl.clearColor(1, 1, 1);

        rotCtrl.update();

        motionCtrl.update(camera.getRotation().yaw);
        camera.getPosition().add(motionCtrl.getDirectedMotion().mul(Jpize.getDt()));

        camera.update();

        for(MeshInstance instance: meshes){
            instance.getShader().bind();
            instance.getShader().setMatrices(camera);
            instance.getMesh().render();
        }
    }


    @Override
    public void dispose(){
        for(MeshInstance instance: meshes)
            instance.getMesh().dispose();
    }


    public void putMesh(MeshInstance instance){
        meshes.add(instance);
    }

    public void removeMesh(MeshInstance instance){
        meshes.remove(instance);
    }


    public Camera3D getCamera(){
        return camera;
    }

    public MotionCtrl getMotionCtrl(){
        return motionCtrl;
    }

    public EulerRotCtrl getRotCtrl(){
        return rotCtrl;
    }

}
