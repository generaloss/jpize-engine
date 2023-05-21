package pize.math.vecmath.vector;

import pize.math.vecmath.tuple.Tuple2i;
import pize.math.vecmath.tuple.Tuple3i;

public class Vec2i extends Tuple2i implements Cloneable{

    public Vec2i(){}

    public Vec2i(int x, int y){
        set(x, y);
    }

    public Vec2i(int xy){
        set(xy);
    }

    public Vec2i(Tuple2i vector){
        set(vector);
    }

    public Vec2i(Tuple3i vector){
        set(vector);
    }


    public double len(){
        return Math.sqrt(x * x + y * y);
    }


    public Vec2i module(){
        if(x < 0)
            x *= -1;
        if(y < 0)
            y *= -1;

        return this;
    }

    public Vec2i zero(){
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

    public double dot(Vec2i vector){
        return x * vector.x + y * vector.y;
    }

    public double dot(Vec2f vector){
        return x * vector.x + y * vector.y;
    }

    public double dot(Vec2d vector){
        return x * vector.x + y * vector.y;
    }


    public float crs(float x, float y){
        return this.x * y - this.y * x;
    }

    public double crs(double x, double y){
        return this.x * y - this.y * x;
    }

    public float crs(Vec2i vector){
        return this.x * vector.y - this.y * vector.x;
    }

    public float crs(Vec2f vector){
        return this.x * vector.y - this.y * vector.x;
    }

    public double crs(Vec2d vector){
        return this.x * vector.y - this.y * vector.x;
    }

    public Vec2i crs(){
        return new Vec2i(y, -x);
    }


    @Override
    public Vec2i clone(){
        return new Vec2i(this);
    }


    public static float crs(Tuple2i a, Tuple2i b){
        return a.x * b.y - a.y * b.x;
    }

    public static float crs(int x1, int y1, int x2, int y2){
        return x1 * y2 - y1 * x2;
    }

    public static float dot(Tuple2i a, Tuple2i b){
        return a.x * b.x + a.y * b.y;
    }

    public static float dot(int x1, int y1, int x2, int y2){
        return x1 * x2 + y1 * y2;
    }

    public static double len(int x, int y){
        return Math.sqrt(x * x + y * y);
    }

}
