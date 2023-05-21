package pize.math.vecmath.vector;

import pize.math.Mathc;
import pize.math.vecmath.tuple.*;

public class Vec3i extends Tuple3i implements Cloneable{
    
    public Vec3i(){}

    public Vec3i(int x, int y, int z){
        set(x, y, z);
    }
    
    public Vec3i(int xyz){
        set(xyz);
    }

    public Vec3i(Tuple3i vector){
        set(vector);
    }
    
    public Vec3i(Tuple2i vector){
        set(vector);
    }
    
    
    public float len(){
        return Mathc.sqrt(x * x + y * y + z * z);
    }
    
    public Vec3i module(){
        if(x < 0)
            x *= -1;
        if(y < 0)
            y *= -1;
        if(z < 0)
            z *= -1;
        
        return this;
    }
    
    public Vec3i zero(){
        set(0, 0, 0);
        
        return this;
    }
    
    public boolean isZero(){
        return x == 0 && y == 0 && z == 0;
    }
    
    public Vec2i xy(){
        return new Vec2i(x, y);
    }
    
    public Vec2i xz(){
        return new Vec2i(x, z);
    }
    
    public Vec2i yz(){
        return new Vec2i(y, z);
    }
    
    
    @Override
    public Vec3i clone(){
        return new Vec3i(this);
    }
    
}
