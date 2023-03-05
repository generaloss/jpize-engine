package glit.math.vecmath.vector;

import glit.math.Mathc;
import glit.math.Maths;
import glit.math.vecmath.tuple.Tuple2d;
import glit.math.vecmath.tuple.Tuple2f;
import glit.math.vecmath.tuple.Tuple3d;
import glit.math.vecmath.tuple.Tuple3f;

public class Vec2d extends Tuple2d implements Cloneable{


    public Vec2d(){}

    public Vec2d(double x, double y){
        set(x, y);
    }

    public Vec2d(float x, float y){
        set(x, y);
    }

    public Vec2d(double xy){
        set(xy);
    }

    public Vec2d(float xy){
        set(xy);
    }

    public Vec2d(Tuple2d vector){
        set(vector);
    }

    public Vec2d(Tuple2f vector){
        set(vector);
    }

    public Vec2d(Tuple3d vector){
        set(vector);
    }

    public Vec2d(Tuple3f vector){
        set(vector);
    }


    public double len(){
        return Math.sqrt(x * x + y * y);
    }

    public Vec2d nor(){
        double length = len();
        if(length == 0 || length == 1)
            return this;

        div(length);
        return this;
    }


    public Vec2d module(){
        if(x < 0)
            x *= -1;
        if(y < 0)
            y *= -1;

        return this;
    }

    public Vec2d zero(){
        set(0, 0);

        return this;
    }

    public boolean isZero(){
        return x == 0 && y == 0;
    }


    public double dot(float x, float y){
        return this.x * x + this.y * y;
    }

    public double dot(double x, double y){
        return this.x * x + this.y * y;
    }

    public double dot(Vec2f vector){
        return x * vector.x + y * vector.y;
    }

    public double dot(Vec2d vector){
        return x * vector.x + y * vector.y;
    }


    public double crs(float x, float y){
        return this.x * y - this.y * x;
    }

    public double crs(double x, double y){
        return this.x * y - this.y * x;
    }

    public double crs(Vec2f vector){
        return this.x * vector.y - this.y * vector.x;
    }

    public double crs(Vec2d vector){
        return this.x * vector.y - this.y * vector.x;
    }

    public Vec2d crs(){
        return new Vec2d(y, -x);
    }


    public double deg(Vec2d vector){
        return rad(vector) * Maths.toDeg;
    }

    public double rad(Vec2d vector){
        double cos = dot(vector) / (len() * vector.len());

        return Math.acos(Maths.clamp(cos, -1, 1));
    }

    public Vec2d rotDeg(float deg){
        return rotRad(deg * Maths.toRad);
    }

    public Vec2d rotRad(double rad){
        float cos = Mathc.cos(rad);
        float sin = Mathc.sin(rad);

        set(x * cos - y * sin, x * sin + y * cos);

        return this;
    }


    @Override
    public Vec2d clone(){
        return new Vec2d(this);
    }


    public static double crs(Tuple2d a, Tuple2d b){
        return a.x * b.y - a.y * b.x;
    }

    public static double crs(double x1, double y1, double x2, double y2){
        return x1 * y2 - y1 * x2;
    }

    public static double dot(Tuple2d a, Tuple2d b){
        return a.x * b.x + a.y * b.y;
    }

    public static double dot(double x1, double y1, double x2, double y2){
        return x1 * x2 + y1 * y2;
    }

    public static double len(double x, double y){
        return Math.sqrt(x * x + y * y);
    }

}
