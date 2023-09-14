package jpize.physic;

import jpize.math.vecmath.vector.Vec2f;

public class RectBody{

    private final AxisAlignedRect rect;
    private final Vec2f position;

    public RectBody(AxisAlignedRect rect){
        this.rect = rect;
        position = new Vec2f();
    }

    public RectBody(RectBody body){
        rect = body.rect.copy();
        position = body.getPosition().copy();
    }


    public Vec2f getMin(){
        return position.copy().add(rect.getMin());
    }

    public Vec2f getMax(){
        return position.copy().add(rect.getMax());
    }


    public AxisAlignedRect rect(){
        return rect;
    }

    public Vec2f getPosition(){
        return position;
    }


    public RectBody copy(){
        return new RectBody(this);
    }

}
