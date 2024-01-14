package jpize.physic.utils;

import jpize.util.math.vecmath.vector.Vec3f;

public class Velocity3f extends Vec3f{
    
    private float max;
    
    public Velocity3f(){
        super();
        max = 1;
    }
    
    public Velocity3f(Vec3f vector){
        super(vector);
        max = 1;
    }

    public Velocity3f clampToMax(boolean clampX, boolean clampY, boolean clampZ){
        final Vec3f normalized = copy().nor().abs();
        if(clampX){
            if(x > max * normalized.x)
                x = max * normalized.x;
            else if(x < -max * normalized.x)
                x = -max * normalized.x;
        }
        if(clampY){
            if(y > max * normalized.y)
                y = max * normalized.y;
            else if(y < -max * normalized.y)
                y = -max * normalized.y;
        }
        if(clampZ){
            if(z > max * normalized.z)
                z = max * normalized.z;
            else if(z < -max * normalized.z)
                z = -max * normalized.z;
        }
        return this;
    }
    
    public Velocity3f clampToMax(){
        return clampToMax(true, true, true);
    }
    
    public Velocity3f reduce(double reduceX, double reduceY, double reduceZ){
        final Vec3f normalized = copy().nor().abs();
        float r;

        if(reduceX != 0){
            r = (float) reduceX * normalized.x;
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
            r = (float) reduceY * normalized.y;
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
            r = (float) reduceZ * normalized.z;
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

    public Velocity3f reduce(double reduceXYZ){
        return reduce(reduceXYZ, reduceXYZ, reduceXYZ);
    }

    public Velocity3f reduceXZ(double reduceXZ){
        return reduce(reduceXZ, 0, reduceXZ);
    }

    
    public float getMax(){
        return max;
    }
    
    public Velocity3f setMax(float max){
        this.max = max;
        
        return this;
    }
    
    
    @Override
    public Velocity3f copy(){
        return new Velocity3f(this);
    }
    
}
