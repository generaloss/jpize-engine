package pize.physic;

import pize.math.vecmath.vector.Vec2f;

public class Motion2f extends Vec2f{
    
    private float max;
    
    public Motion2f(){
        super();
        max = 1;
    }
    
    public Motion2f(Vec2f vector){
        super(vector);
        max = 1;
    }
    
    
    public Motion2f collidedAxesToZero(Vec2f collidedMotion){
        if(x != 0 && collidedMotion.x == 0)
            x = 0;
        
        if(y != 0 && collidedMotion.y == 0)
            y = 0;
        
        return this;
    }
    
    public Motion2f clampToMax(){
        final Vec2f normalized = copy().nor().abs();
        
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
    
    public Motion2f reduce(double reduce){
        return reduce(reduce, reduce);
    }
    
    public Motion2f reduce(double reduceX, double reduceY){
        final Vec2f normalized = copy().nor().abs();
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
    
    
    public float getMax(){
        return max;
    }
    
    public Motion2f setMax(float max){
        this.max = max;
        
        return this;
    }
    
    
    public Motion2f copy(){
        return new Motion2f(this);
    }
    
}
