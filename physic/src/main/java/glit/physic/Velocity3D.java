package glit.physic;

import glit.math.vecmath.tuple.Tuple3d;
import glit.math.vecmath.vector.Vec3d;

public class Velocity3D extends Vec3d implements Cloneable{

    private double max;


    public Velocity3D(){
        super();
        max = 1;
    }

    public Velocity3D(Tuple3d velocity){
        super(velocity);
        max = 1;
    }

    public Velocity3D(Velocity3D velocity){
        super(velocity);
        max = velocity.max;
    }


    public Velocity3D collidedAxesToZero(Tuple3d collidedMovement){
        if(x != 0 && collidedMovement.x == 0)
            x = 0;

        if(y != 0 && collidedMovement.y == 0)
            y = 0;

        if(z != 0 && collidedMovement.z == 0)
            z = 0;

        return this;
    }

    public Velocity3D clampToMax(){
        Tuple3d normalized = clone().nor().module();

        if(x > max*normalized.x)
            x = max*normalized.x;
        else if(x < -max*normalized.x)
            x = -max*normalized.x;

        if(y > max*normalized.y)
            y = max*normalized.y;
        else if(y < -max*normalized.y)
            y = -max*normalized.y;

        if(z > max*normalized.z)
            z = max*normalized.z;
        else if(z < -max*normalized.z)
            z = -max*normalized.z;

        return this;
    }

    public Velocity3D reduce(double reduce){
        Tuple3d normalized = clone().nor().module();

        double r = reduce*normalized.x;
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

        r = reduce*normalized.y;
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

        r = reduce*normalized.z;
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

        return this;
    }


    public double getMax(){
        return max;
    }

    public Velocity3D setMax(double max){
        this.max = max;

        return this;
    }


    @Override
    public Velocity3D clone(){
        return new Velocity3D(this);
    }

}
