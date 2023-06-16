package pize.physic;

import pize.math.vecmath.point.Point3f;
import pize.math.vecmath.tuple.Tuple3f;

public class BoundingBox implements Cloneable{
    
    private final Point3f min, max;
    
    
    public BoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ){
        this.min = new Point3f(minX, minY, minZ);
        this.max = new Point3f(maxX, maxY, maxZ);
    }
    
    public BoundingBox(Point3f min, Point3f max){
        this.min = min;
        this.max = max;
    }
    
    public BoundingBox(Tuple3f min, Tuple3f max){
        this.min = new Point3f(min);
        this.max = new Point3f(max);
    }
    
    public BoundingBox(BoundingBox box){
        this.min = box.min.clone();
        this.max = box.max.clone();
    }
    
    
    public void resize(double minX, double minY, double minZ, double maxX, double maxY, double maxZ){
        this.min.set(minX, minY, minZ);
        this.max.set(maxX, maxY, maxZ);
    }
    
    public void resize(Tuple3f min, Tuple3f max){
        this.min.set(min);
        this.max.set(max);
    }
    
    
    public void expand(double negativeX, double negativeY, double negativeZ, double positiveX, double positiveY, double positiveZ){
        resize(min.x - negativeX, min.y - negativeY, min.z - negativeZ, max.x + positiveX, max.y + positiveY, max.z + positiveZ);
    }
    
    public void expand(Tuple3f negative, Tuple3f positive){
        expand(negative.x, negative.y, negative.z, positive.x, positive.y, positive.z);
    }
    
    public void expand(double expandX, double expandY, double expandZ){
        expand(expandX, expandY, expandZ, expandX, expandY, expandZ);
    }
    
    public void expand(double expand){
        expand(expand, expand, expand);
    }
    
    
    public void shift(double shiftX, double shiftY, double shiftZ){
        resize(min.x + shiftX, min.y + shiftY, min.z + shiftZ, max.x + shiftX, max.y + shiftY, max.z + shiftZ);
    }
    
    public void shift(Tuple3f shift){
        shift(shift.x, shift.y, shift.z);
    }
    
    
    public Point3f getMin(){
        return min;
    }
    
    public Point3f getMax(){
        return max;
    }
    
    
    public float getSizeX(){
        return max.x - min.x;
    }
    
    public float getSizeY(){
        return max.y - min.y;
    }
    
    public float getSizeZ(){
        return max.z - min.z;
    }
    
    
    public float getVolume(){
        return getSizeY() * getSizeX() * getSizeZ();
    }
    
    public float getCenterX(){
        return (min.x + getSizeX() * 0.5F);
    }
    
    public float getCenterY(){
        return (min.y + getSizeY() * 0.5F);
    }
    
    public float getCenterZ(){
        return (min.z + getSizeZ() * 0.5F);
    }
    
    public Point3f getCenter(){
        return new Point3f(getCenterX(), getCenterY(), getCenterZ());
    }
    
    
    @Override
    public BoundingBox clone(){
        return new BoundingBox(this);
    }
    
}
