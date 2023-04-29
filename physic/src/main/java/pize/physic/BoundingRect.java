package pize.physic;

import pize.math.vecmath.point.Point2f;
import pize.math.vecmath.tuple.Tuple2f;

public class BoundingRect implements Cloneable{

    private final Point2f min, max;


    public BoundingRect(double minX, double minY, double maxX, double maxY){
        this.min = new Point2f(minX, minY);
        this.max = new Point2f(maxX, maxY);
    }

    public BoundingRect(Tuple2f min, Tuple2f max){
        this.min = new Point2f(min);
        this.max = new Point2f(max);
    }

    public BoundingRect(Point2f min, Point2f max){
        this.min = min;
        this.max = max;
    }

    public BoundingRect(BoundingRect box){
        min = box.min.clone();
        max = box.max.clone();
    }


    public void resize(double minX, double minY, double maxX, double maxY){
        this.min.set(minX, minY);
        this.max.set(maxX, maxY);
    }

    public void resize(Tuple2f min, Tuple2f max){
        this.min.set(min);
        this.max.set(max);
    }


    public void expand(double negativeX, double negativeY, double positiveX, double positiveY){
        resize(min.x - negativeX, min.y - negativeY, max.x + positiveX, max.y + positiveY);
    }

    public void expand(Tuple2f negative, Tuple2f positive){
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

    public void shift(Tuple2f shift){
        shift(shift.x, shift.y);
    }


    public Point2f getMin(){
        return min;
    }

    public Point2f getMax(){
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

    public Point2f getCenter(){
        return new Point2f(getCenterX(), getCenterY());
    }


    @Override
    public BoundingRect clone(){
        return new BoundingRect(this);
    }

}
