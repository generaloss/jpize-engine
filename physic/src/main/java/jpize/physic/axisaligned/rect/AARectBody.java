package jpize.physic.axisaligned.rect;

import jpize.math.vecmath.vector.Vec2f;

public class AARectBody{

    private final AARect rect;
    private final Vec2f position, min, max;

    public AARectBody(AARect rect, Vec2f position){
        this.rect = rect;
        this.position = position;
        this.min = new Vec2f();
        this.max = new Vec2f();
    }

    public AARectBody(AARectBody body){
        this(body.rect.copy(), body.position.copy());
    }

    public AARectBody(AARect rect){
        this(rect, new Vec2f());
    }

    public AARectBody(Vec2f position){
        this(new AARect(0, 0, 0, 0), position);
    }

    public AARectBody(){
        this(new AARect(0, 0, 0, 0), new Vec2f());
    }


    public Vec2f getMin(){
        min.set(rect.getMin()).add(position);
        return min;
    }

    public Vec2f getMax(){
        max.set(rect.getMax()).add(position);
        return max;
    }


    public AARect rect(){
        return rect;
    }

    public Vec2f getPosition(){
        return position;
    }


    public boolean intersects(AARectBody body){
        return (
                (this.getMin().x < body.getMax().x && this.getMax().x > body.getMin().x) &&
                (this.getMin().y < body.getMax().y && this.getMax().y > body.getMin().y)
        );
    }


    public AARectBody copy(){
        return new AARectBody(this);
    }

}
