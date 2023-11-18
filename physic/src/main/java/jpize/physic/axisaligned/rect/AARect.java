package jpize.physic.axisaligned.rect;

import jpize.math.vecmath.vector.Vec2f;

public class AARect{

    private final Vec2f min, max;


    public AARect(double minX, double minY, double maxX, double maxY){
        this.min = new Vec2f(minX, minY);
        this.max = new Vec2f(maxX, maxY);
    }

    public AARect(Vec2f min, Vec2f max){
        this.min = min;
        this.max = max;
    }

    public AARect(AARect box){
        this.min = box.min.copy();
        this.max = box.max.copy();
    }


    public void resize(double minX, double minY, double maxX, double maxY){
        this.min.set(minX, minY);
        this.max.set(maxX, maxY);
    }

    public void resize(Vec2f min, Vec2f max){
        this.min.set(min);
        this.max.set(max);
    }
    
    public void resize(AARect box){
        resize(box.min, box.max);
    }


    public void expand(double negativeX, double negativeY, double positiveX, double positiveY){
        resize(min.x - negativeX, min.y - negativeY, max.x + positiveX, max.y + positiveY);
    }

    public void expand(Vec2f negative, Vec2f positive){
        expand(negative.x, negative.y, positive.x, positive.y);
    }

    public void expand(double expandX, double expandY){
        expand(expandX, expandY, expandX, expandY);
    }

    public void expand(double expand){
        expand(expand, expand);
    }


    public void shift(double shiftX, double shiftY){
        resize(min.x + shiftX, min.y + shiftY, max.x + shiftX, max.y + shiftY);
    }

    public void shift(Vec2f shift){
        shift(shift.x, shift.y);
    }


    public Vec2f getMin(){
        return min;
    }

    public Vec2f getMax(){
        return max;
    }


    public float getWidth(){
        return max.x - min.x;
    }

    public float getHeight(){
        return max.y - min.y;
    }


    public float getArea(){
        return getHeight() * getWidth();
    }

    public float getCenterX(){
        return (min.x + getWidth() * 0.5F);
    }

    public float getCenterY(){
        return (min.y + getHeight() * 0.5F);
    }

    public Vec2f getCenter(){
        return new Vec2f(getCenterX(), getCenterY());
    }


    public AARect copy(){
        return new AARect(this);
    }

}
