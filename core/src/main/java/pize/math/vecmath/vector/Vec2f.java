package pize.math.vecmath.vector;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.tuple.Tuple2d;
import pize.math.vecmath.tuple.Tuple2f;
import pize.math.vecmath.tuple.Tuple3d;
import pize.math.vecmath.tuple.Tuple3f;

public class Vec2f extends Tuple2f implements Cloneable{


    public Vec2f(){}

    public Vec2f(double x, double y){
        set(x, y);
    }

    public Vec2f(float x, float y){
        set(x, y);
    }

    public Vec2f(double xy){
        set(xy);
    }

    public Vec2f(float xy){
        set(xy);
    }

    public Vec2f(Tuple2d vector){
        set(vector);
    }

    public Vec2f(Tuple2f vector){
        set(vector);
    }

    public Vec2f(Tuple3d vector){
        set(vector);
    }

    public Vec2f(Tuple3f vector){
        set(vector);
    }


    public float len2(){
        return x * x + y * y;
    }
    
    public float len(){
        return Mathc.sqrt(len2());
    }

    public Vec2f nor(){
        double length = len();
        if(length == 0 || length == 1)
            return this;

        div(length);
        return this;
    }


    public Vec2f module(){
        if(x < 0)
            x *= -1;
        if(y < 0)
            y *= -1;

        return this;
    }

    public Vec2f zero(){
        set(0, 0);

        return this;
    }

    public boolean isZero(){
        return x == 0 && y == 0;
    }


    public float dot(float x, float y){
        return this.x * x + this.y * y;
    }

    public double dot(double x, double y){
        return this.x * x + this.y * y;
    }

    public float dot(Vec2f vector){
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

    public float crs(Vec2f vector){
        return this.x * vector.y - this.y * vector.x;
    }

    public double crs(Vec2d vector){
        return this.x * vector.y - this.y * vector.x;
    }

    public Vec2f crs(){
        return new Vec2f(y, -x);
    }
    
    
    public Vec2f fract(){
        x = Maths.frac(x);
        y = Maths.frac(y);
        
        return this;
    }
    
    
    public Vec2f floor(){
        x = Maths.floor(x);
        y = Maths.floor(y);
        
        return this;
    }
    
    public Vec2f round(){
        x = Maths.round(x);
        y = Maths.round(y);
        
        return this;
    }
    
    public Vec2f ceil(){
        x = Maths.ceil(x);
        y = Maths.ceil(y);
        
        return this;
    }


    public float deg(Vec2d vector){
        return rad(vector) * Maths.toDeg;
    }

    public float rad(Vec2d vector){
        double cos = dot(vector) / (len() * vector.len());

        return Mathc.acos(Maths.clamp(cos, -1, 1));
    }

    public Vec2f rotDeg(double degrees){
        return rotRad(degrees * Maths.toRad);
    }

    public Vec2f rotRad(double radians){
        float cos = Mathc.cos(radians);
        float sin = Mathc.sin(radians);

        set(x * cos - y * sin, x * sin + y * cos);

        return this;
    }


    @Override
    public Vec2f clone(){
        return new Vec2f(this);
    }


    public static float crs(Tuple2f a, Tuple2f b){
        return a.x * b.y - a.y * b.x;
    }

    public static float crs(float x1, float y1, float x2, float y2){
        return x1 * y2 - y1 * x2;
    }

    public static float dot(Tuple2f a, Tuple2f b){
        return a.x * b.x + a.y * b.y;
    }

    public static float dot(float x1, float y1, float x2, float y2){
        return x1 * x2 + y1 * y2;
    }

    public static float len(float x, float y){
        return Mathc.sqrt(x * x + y * y);
    }

}
