package jpize.physic;

import jpize.math.vecmath.vector.Vec2f;

public class Velocity2f extends Vec2f{
    
    private float max;
    
    public Velocity2f(){
        super();
        max = 1;
    }
    
    public Velocity2f(Vec2f vector){
        super(vector);
        max = 1;
    }
    
    
    public Velocity2f collidedAxesToZero(Vec2f collidedVelocity){
        if(x != 0 && collidedVelocity.x == 0)
            x = 0;
        
        if(y != 0 && collidedVelocity.y == 0)
            y = 0;
        
        return this;
    }
    
    public Velocity2f clampToMax(){
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
    
    public Velocity2f reduce(double reduce){
        return reduce(reduce, reduce);
    }
    
    public Velocity2f reduce(double reduceX, double reduceY){
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
    
    public Velocity2f setMax(float max){
        this.max = max;
        
        return this;
    }
    
    
    public Velocity2f copy(){
        return new Velocity2f(this);
    }
    
}
