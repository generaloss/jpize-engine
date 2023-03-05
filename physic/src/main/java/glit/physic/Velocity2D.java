package glit.physic;

import glit.math.vecmath.tuple.Tuple2d;
import glit.math.vecmath.vector.Vec2d;

public class Velocity2D extends Vec2d implements Cloneable{

    private double max;


    public Velocity2D(){
        super();
        max = 1;
    }

    public Velocity2D(Tuple2d tuple){
        super(tuple);
        max = 1;
    }

    public Velocity2D(Velocity2D velocity){
        super(velocity);
        max = velocity.max;
    }


    public Velocity2D collidedAxesToZero(Tuple2d collidedMovement){
        if(x != 0 && collidedMovement.x == 0)
            x = 0;

        if(y != 0 && collidedMovement.y == 0)
            y = 0;

        return this;
    }

    public Velocity2D clampToMax(){
        Tuple2d normalized = clone().nor().module();

        if(x > max*normalized.x)
            x = max*normalized.x;
        else if(x < -max*normalized.x)
            x = -max*normalized.x;

        if(y > max*normalized.y)
            y = max*normalized.y;
        else if(y < -max*normalized.y)
            y = -max*normalized.y;

        return this;
    }

    public Velocity2D reduce(double reduce){
        Tuple2d normalized = clone().nor().module();

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

        return this;
    }


    public double getMax(){
        return max;
    }

    public Velocity2D setMax(double max){
        this.max = max;

        return this;
    }


    @Override
    public Velocity2D clone(){
        return new Velocity2D(this);
    }

}
