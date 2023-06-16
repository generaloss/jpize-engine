package pize.physic;

import pize.math.vecmath.tuple.Tuple2d;
import pize.math.vecmath.vector.Vec2d;

public class Motion2D extends Vec2d implements Cloneable{
    
    private double max;
    
    
    public Motion2D(){
        super();
        max = 1;
    }
    
    public Motion2D(Tuple2d tuple){
        super(tuple);
        max = 1;
    }
    
    public Motion2D(Motion2D motion){
        super(motion);
        max = motion.max;
    }
    
    
    public Motion2D collidedAxesToZero(Tuple2d collidedMotion){
        if(x != 0 && collidedMotion.x == 0)
            x = 0;
        
        if(y != 0 && collidedMotion.y == 0)
            y = 0;
        
        return this;
    }
    
    public Motion2D clampToMax(){
        final Vec2d normalized = clone().nor().module();
        
        if(x > max * normalized.x)
            x = max * normalized.x;
        else if(x < -max * normalized.x)
            x = -max * normalized.x;
        
        if(y > max * normalized.y)
            y = max * normalized.y;
        else if(y < -max * normalized.y)
            y = -max * normalized.y;
        
        return this;
    }
    
    public Motion2D reduce(double reduce){
        return reduce(reduce, reduce);
    }
    
    public Motion2D reduce(double reduceX, double reduceY){
        final Vec2d normalized = clone().nor().module();
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
        
        return this;
    }
    
    
    public double getMax(){
        return max;
    }
    
    public Motion2D setMax(double max){
        this.max = max;
        
        return this;
    }
    
    
    @Override
    public Motion2D clone(){
        return new Motion2D(this);
    }
    
}
