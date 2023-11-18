package jpize.graphics.camera.controller;

import jpize.math.vecmath.vector.Vec3f;
import jpize.math.vecmath.vector.Vec3i;
import jpize.sdl.input.Key;

public class Motion3DController{

    private final Vec3i motion; // {-1, 0, 1}
    private final Vec3f directedMotion;

    public Motion3DController(){
        motion = new Vec3i();
        directedMotion = new Vec3f();
    }

    public void update(float yawDegrees){
        motion.zero();

        if(Key.W.isPressed()) motion.x += 1;
        if(Key.A.isPressed()) motion.z += 1;
        if(Key.S.isPressed()) motion.x -= 1;
        if(Key.D.isPressed()) motion.z -= 1;

        if(Key.SPACE.isPressed()) motion.y += 1;
        if(Key.LSHIFT.isPressed()) motion.y -= 1;

        directedMotion.set(motion).rotY(yawDegrees);
    }

    public Vec3i getMotion(){
        return motion;
    }

    public Vec3f getDirectedMotion(){
        return directedMotion;
    }

}
