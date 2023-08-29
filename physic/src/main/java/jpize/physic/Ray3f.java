package jpize.physic;

import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec3f;

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
    
    
    public void set(Vec3f position, Vec3f direction){
        position.set(position);
        direction.set(direction);
    }
    
    public void set(Vec3f position, Vec3f direction, float length){
        position.set(position);
        direction.set(direction).mul(length);
    }
    
    public void set(Ray3f ray){
        set(ray.origin, ray.direction);
    }
    
    public void set(Vec3f direction){
        direction.set(direction);
    }
    
    
    public Vec3f getOrigin(){
        return origin;
    }
    
    public Vec3f getDirection(){
        return direction;
    }
    
    
    public boolean intersects(BoxBody box){
        final Vec3f min = box.getMin();
        final Vec3f max = box.getMax();
        
        return intersects(new float[]{
            max.x, max.y, max.z,
            min.x, max.y, max.z,
            max.x, min.y, max.z,
            max.x, max.y, min.z,
            min.x, min.y, max.z,
            max.x, min.y, min.z,
            min.x, max.y, min.z,
            min.x, min.y, min.z
        });
    }
    
    public float intersect(Vec3f v0, Vec3f v1, Vec3f v2){
        final Vec3f edge10 = v1.sub(v0);
        final Vec3f edge20 = v2.sub(v0);
        final Vec3f normal = direction.copy().crs(edge20);
        final float det = edge10.copy().dot(normal);
        
        if(det < Maths.Epsilon)
            return -1;
        
        final float invDet = 1 / det;
        final Vec3f tvec = origin.copy().sub(v0);
        final float u = tvec.copy().dot(normal) * invDet;
        
        if(u < 0 || u > 1)
            return -1;
        
        final Vec3f qvec = tvec.copy().crs(edge10);
        final float v = direction.copy().dot(qvec) * invDet;
        
        if(v < 0 || u + v > 1)
            return -1;
        
        return edge20.copy().dot(qvec) * invDet;
    }
    
    public boolean intersects(float[] vertices){
        final int size = vertices.length / 3;
        
        for(int i = 0; i < size; i += 3){
            final int vertexOffset = i * 3;
            
            final Vec3f v0 = new Vec3f(
                vertices[vertexOffset    ],
                vertices[vertexOffset + 1],
                vertices[vertexOffset + 2]
            );
            final Vec3f v1 = new Vec3f(
                vertices[vertexOffset + 3],
                vertices[vertexOffset + 4],
                vertices[vertexOffset + 5]
            );
            final Vec3f v2 = new Vec3f(
                vertices[vertexOffset + 6],
                vertices[vertexOffset + 7],
                vertices[vertexOffset + 8]
            );
            
            if(intersect(v0, v1, v2) != -1)
                return true;
        }
        
        return false;
    }
    
    
    public Ray3f copy(){
        return new Ray3f(this);
    }
    
}
