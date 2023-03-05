package glit.graphics.camera;

import glit.math.vecmath.vector.Vec2f;

public abstract class Camera2D implements Camera{

    protected final Vec2f position;

    public Camera2D(){
        position = new Vec2f();
    }


    public float getX(){
        return position.x;
    }

    public float getY(){
        return position.y;
    }

    public Vec2f getPos(){
        return position;
    }


    public abstract float getRot();

    public abstract float getScale();

}
