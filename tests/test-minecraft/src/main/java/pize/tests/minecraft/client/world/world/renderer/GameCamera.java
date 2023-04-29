package pize.tests.minecraft.client.world.world.renderer;

import pize.Pize;
import pize.graphics.camera.PerspectiveCamera;
import pize.math.Maths;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.util.EulerAngle;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.minecraft.client.game.options.Options;

public class GameCamera{ // Adapter

    private final Options settings;

    private final PerspectiveCamera camera;
    private float newFov, prevFov;
    private float fovInterpolationTime;

    public GameCamera(Options settings){
        this.settings = settings;

        camera = new PerspectiveCamera(0.1, 1000, settings.getFov());
        newFov = camera.getFov();
    }

    public void update(){
        camera.setFov(Maths.lerp(prevFov, newFov, Maths.clamp(fovInterpolationTime, 0, 1)));

        fovInterpolationTime += Pize.getDeltaTime() * 6;

        camera.update();
    }

    public void setFov(float fov){
        prevFov = newFov;
        newFov = fov;
        fovInterpolationTime = 0;
    }


    public void resize(int width, int height){
        camera.resize(width, height);
    }

    public Matrix4f getProjection(){
        return camera.getProjection();
    }

    public Matrix4f getView(){
        return camera.getView();
    }

    public Vec3f getPos(){
        return camera.getPos();
    }

    public EulerAngle getRot(){
        return camera.getRot();
    }

    public PerspectiveCamera getCamera(){
        return camera;
    }

}
