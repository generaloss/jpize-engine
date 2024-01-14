package jpize.physic.axisaligned.box;

import jpize.util.math.vecmath.vector.Vec3f;
import jpize.physic.Ray3f;
import jpize.physic.utils.Intersector;

public class AABoxBody{

    private final AABox boundingBox;
    private final Vec3f position, min, max;

    public AABoxBody(AABox box, Vec3f position){
        this.boundingBox = box;
        this.position = position;
        this.min = new Vec3f();
        this.max = new Vec3f();
    }

    public AABoxBody(AABoxBody body){
        this(body.getBoundingBox().copy(), body.getPosition().copy());
    }

    public AABoxBody(AABox box){
        this(box, new Vec3f());
    }

    public AABoxBody(Vec3f position){
        this(new AABox(0, 0, 0, 0, 0, 0), position);
    }

    public AABoxBody(){
        this(new AABox(0, 0, 0, 0, 0, 0), new Vec3f());
    }


    public Vec3f getMin(){
        min.set(boundingBox.getMin()).add(position);
        return min;
    }

    public Vec3f getMax(){
        max.set(boundingBox.getMax()).add(position);
        return max;
    }


    public AABox getBoundingBox(){
        return boundingBox;
    }

    public Vec3f getPosition(){
        return position;
    }
    
    
    public boolean isIntersectBox(AABoxBody box){
        return Intersector.isAABoxIntersectAABox(this, box);
    }

    public float getIntersectionRay(Ray3f ray){
        return Intersector.getRayIntersectionAABox(ray, this);
    }

    public boolean isIntersectRay(Ray3f ray){
        return Intersector.isRayIntersectAABox(ray, this);
    }


    public AABoxBody copy(){
        return new AABoxBody(this);
    }

}
