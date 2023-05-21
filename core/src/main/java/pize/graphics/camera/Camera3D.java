package pize.graphics.camera;

import pize.math.util.EulerAngle;
import pize.math.vecmath.vector.Vec3f;

public abstract class Camera3D extends Camera{

    protected final Vec3f position;
    protected final EulerAngle rotation;


    public Camera3D(int width, int height){
        super(width, height);
        
        position = new Vec3f();
        rotation = new EulerAngle();
    }


    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public float getZ(){
        return position.z;
    }

    public Vec3f getPos(){
        return position;
    }

    public EulerAngle getRot(){
        return rotation;
    }


    public abstract float getFov();

    public abstract void setFov(float fieldOfView);


    public abstract float getFar();

    public abstract void setFar(float far);

    public abstract float getNear();

    public abstract void setNear(float near);

}