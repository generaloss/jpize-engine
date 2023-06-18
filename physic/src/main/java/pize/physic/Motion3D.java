package pize.physic;

import pize.math.vecmath.tuple.Tuple3d;
import pize.math.vecmath.vector.Vec3d;

public class Motion3D extends Vec3d implements Cloneable{
    
    private double max;
    
    
    public Motion3D(){
        super();
        max = 1;
    }
    
    public Motion3D(Tuple3d tuple){
        super(tuple);
        max = 1;
    }
    
    public Motion3D(Motion3D motion){
        super(motion);
        max = motion.max;
    }
    
    
    public Motion3D collidedAxesToZero(Tuple3d collidedMotion){
        if(collidedMotion == null)
            return this;
        
        if(x != 0 && collidedMotion.x == 0)
            x = 0;
        
        if(y != 0 && collidedMotion.y == 0)
            y = 0;
        
        if(z != 0 && collidedMotion.z == 0)
            z = 0;
        
        return this;
    }
    
    public Motion3D clampToMax(){
        final Vec3d normalized = clone().nor().module();
        
        if(x > max * normalized.x)
            x = max * normalized.x;
        else if(x < -max * normalized.x)
            x = -max * normalized.x;
        
        if(y > max * normalized.y)
            y = max * normalized.y;
        else if(y < -max * normalized.y)
            y = -max * normalized.y;
        
        if(z > max * normalized.z)
            z = max * normalized.z;
        else if(z < -max * normalized.z)
            z = -max * normalized.z;
        
        return this;
    }
    
    public Motion3D reduce(double reduce){
        return reduce(reduce, reduce, reduce);
    }
    
    public Motion3D reduceXZ(double reduce){
        return reduce(reduce, 0, reduce);
    }
    
    public Motion3D reduce(double reduceX, double reduceY, double reduceZ){
        final Vec3d normalized = clone().nor().module();
        
        double r;
        if(reduceX != 0){
            r = reduceX * normalized.x;
            if(x > 0){
                if(x >= r)
                    x -= r;
                else
                    x = 0;
            }else if(x < 0){
                if(x <= -r)
                    x += r;
                else
                    x = 0;
            }
        }
        
        if(reduceY != 0){
            r = reduceY * normalized.y;
            if(y > 0){
                if(y >= r)
                    y -= r;
                else
                    y = 0;
            }else if(y < 0){
                if(y <= -r)
                    y += r;
                else
                    y = 0;
            }
        }
        
        if(reduceZ != 0){
            r = reduceZ * normalized.z;
            if(z > 0){
                if(z >= r)
                    z -= r;
                else
                    z = 0;
            }else if(z < 0){
                if(z <= -r)
                    z += r;
                else
                    z = 0;
            }
        }
        
        return this;
    }
    
    
    public double getMax(){
        return max;
    }
    
    public Motion3D setMax(double max){
        this.max = max;
        
        return this;
    }
    
    
    @Override
    public Motion3D clone(){
        return new Motion3D(this);
    }
    
}
