package jpize.physic;

import jpize.math.vecmath.vector.Vec3f;
import jpize.physic.axisaligned.box.AABoxBody;
import jpize.physic.utils.Intersector;

public class Ray3f{

    private final Vec3f origin;
    private final Vec3f direction;

    public Ray3f(){
        this.origin = new Vec3f();
        this.direction = new Vec3f();
    }

    public Ray3f(Ray3f ray){
        this.origin = ray.origin.copy();
        this.direction = ray.direction.copy();
    }


    public void set(Vec3f origin, Vec3f direction){
        this.origin.set(origin);
        this.direction.set(direction);
    }

    public void set(Vec3f origin, Vec3f direction, float length){
        this.origin.set(origin);
        this.direction.set(direction).mul(length);
    }

    public void set(Ray3f ray){
        set(ray.origin, ray.direction);
    }

    public void set(Vec3f direction){
        direction.set(direction);
    }


    public Vec3f origin(){
        return origin;
    }

    public Vec3f dir(){
        return direction;
    }

    public float len(){
        return direction.len();
    }


    public float getIntersectionAabb(AABoxBody box){
        return Intersector.getRayIntersectionAabb(this, box);
    }

    public boolean isIntersectAabb(AABoxBody box){
        return Intersector.isRayIntersectAabb(this, box);
    }

    public float getIntersectionTriangle(Vec3f vertex1, Vec3f vertex2, Vec3f vertex3){
        return Intersector.getRayIntersectionTriangle(this, vertex1, vertex2, vertex3);
    }

    public boolean isIntersectionTriangle(Vec3f vertex1, Vec3f vertex2, Vec3f vertex3){
        return Intersector.isRayIntersectTriangle(this, vertex1, vertex2, vertex3);
    }

    public float getIntersectionTriangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3){
        return Intersector.getRayIntersectionTriangle(this, x1, y1, z1, x2, y2, z2, x3, y3, z3);
    }

    public boolean isIntersectionTriangle(float x1, float y1, float z1, float x2, float y2, float z2, float x3, float y3, float z3){
        return Intersector.isRayIntersectTriangle(this, x1, y1, z1, x2, y2, z2, x3, y3, z3);
    }


    public Ray3f copy(){
        return new Ray3f(this);
    }

}
