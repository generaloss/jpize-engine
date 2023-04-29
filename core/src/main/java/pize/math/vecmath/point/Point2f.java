package pize.math.vecmath.point;

import pize.math.vecmath.tuple.Tuple2d;
import pize.math.vecmath.tuple.Tuple2f;
import pize.math.vecmath.tuple.Tuple3d;
import pize.math.vecmath.tuple.Tuple3f;

public class Point2f extends Tuple2f implements Cloneable{


    public Point2f(){}

    public Point2f(double x, double y){
        set(x, y);
    }

    public Point2f(float x, float y){
        set(x, y);
    }

    public Point2f(double xy){
        set(xy);
    }

    public Point2f(float xy){
        set(xy);
    }

    public Point2f(Tuple2d vector){
        set(vector);
    }

    public Point2f(Tuple2f vector){
        set(vector);
    }

    public Point2f(Tuple3d vector){
        set(vector);
    }

    public Point2f(Tuple3f vector){
        set(vector);
    }


    public double dst(double x, double y){
        double dx = this.x - x;
        double dy = this.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double dst(float x, float y){
        double dx = this.x - x;
        double dy = this.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double dst(Tuple2d vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public double dst(Tuple2f vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        return Math.sqrt(dx * dx + dy * dy);
    }


    @Override
    public Point2f clone(){
        return new Point2f(this);
    }

}
