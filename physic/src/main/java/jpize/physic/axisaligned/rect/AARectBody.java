package jpize.physic.axisaligned.rect;

import jpize.math.vecmath.vector.Vec2f;

public class AARectBody{

    private final AARect rect;
    private final Vec2f position;

    public AARectBody(AARect rect){
        this.rect = rect;
        position = new Vec2f();
    }

    public AARectBody(AARectBody body){
        rect = body.rect.copy();
        position = body.getPosition().copy();
    }


    public Vec2f getMin(){
        return position.copy().add(rect.getMin());
    }

    public Vec2f getMax(){
        return position.copy().add(rect.getMax());
    }


    public AARect rect(){
        return rect;
    }

    public Vec2f getPosition(){
        return position;
    }


    public AARectBody copy(){
        return new AARectBody(this);
    }

}
