package pize.physic;

import pize.math.vecmath.vector.Vec2f;

public class RectBody{

    private final BoundingBox2f rect;
    private final Vec2f position;

    public RectBody(BoundingBox2f rect){
        this.rect = rect;
        position = new Vec2f();
    }

    public RectBody(RectBody body){
        rect = body.rect.copy();
        position = body.pos().copy();
    }


    public Vec2f getMin(){
        return position.copy().add(rect.getMin());
    }

    public Vec2f getMax(){
        return position.copy().add(rect.getMax());
    }


    public BoundingBox2f rect(){
        return rect;
    }

    public Vec2f pos(){
        return position;
    }


    public RectBody copy(){
        return new RectBody(this);
    }

}
