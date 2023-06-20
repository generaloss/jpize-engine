package pize.math.vecmath.vector;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.tuple.*;

public class Vec3f extends Tuple3f implements Cloneable{


    public Vec3f(){}

    public Vec3f(double x, double y, double z){
        set(x, y, z);
    }

    public Vec3f(float x, float y, float z){
        set(x, y, z);
    }

    public Vec3f(int x, int y, int z){
        set(x, y, z);
    }

    public Vec3f(double xyz){
        set(xyz);
    }

    public Vec3f(float xyz){
        set(xyz);
    }

    public Vec3f(int xyz){
        set(xyz);
    }

    public Vec3f(Tuple3d vector){
        set(vector);
    }

    public Vec3f(Tuple3f vector){
        set(vector);
    }

    public Vec3f(Tuple3i vector){
        set(vector);
    }

    public Vec3f(Tuple2d vector){
        set(vector);
    }

    public Vec3f(Tuple2f vector){
        set(vector);
    }

    public Vec3f(Tuple2i vector){
        set(vector);
    }


    public float len(){
        return Mathc.sqrt(x * x + y * y + z * z);
    }

    public Vec3f nor(){
        double len = len();
        if(len == 0 || len == 1)
            return this;

        div(len);
        return this;
    }


    public Vec3f module(){
        if(x < 0)
            x *= -1;
        if(y < 0)
            y *= -1;
        if(z < 0)
            z *= -1;

        return this;
    }

    public Vec3f zero(){
        set(0, 0, 0);

        return this;
    }

    public boolean isZero(){
        return x == 0 && y == 0 && z == 0;
    }


    public float dot(float x, float y, float z){
        return this.x * x + this.y * y + this.z * z;
    }

    public double dot(double x, double y, double z){
        return this.x * x + this.y * y + this.z * z;
    }

    public float dot(Vec3f vector){
        return x * vector.x + y * vector.y + z * vector.z;
    }

    public double dot(Vec3d vector){
        return x * vector.x + y * vector.y + z * vector.z;
    }


    public Vec3f crs(float x, float y, float z){
        this.x = this.y * z - this.z * y;
        this.y = this.z * x - this.x * z;
        this.z = this.x * y - this.y * x;

        return this;
    }

    public Vec3f crs(double x, double y, double z){
        this.x = (float) (this.y * z - this.z * y);
        this.y = (float) (this.z * x - this.x * z);
        this.z = (float) (this.x * y - this.y * x);

        return this;
    }

    public Vec3f crs(Vec3f vector){
        this.x = y * vector.z - z * vector.y;
        this.y = z * vector.x - x * vector.z;
        this.z = x * vector.y - y * vector.x;

        return this;
    }

    public Vec3f crs(Vec3d vector){
        this.x = (float) (y * vector.z - z * vector.y);
        this.y = (float) (z * vector.x - x * vector.z);
        this.z = (float) (x * vector.y - y * vector.x);

        return this;
    }
    
    
    public Vec3f fract(){
        x = Maths.frac(x);
        y = Maths.frac(y);
        z = Maths.frac(z);
        
        return this;
    }
    
    
    public Vec3f lerp(Tuple3f start, Tuple3f end, float t){
        x = Maths.lerp(start.x, end.x, t);
        y = Maths.lerp(start.y, end.y, t);
        z = Maths.lerp(start.z, end.z, t);
        
        return this;
    }


    public Vec2f xy(){
        return new Vec2f(x, y);
    }

    public Vec2f xz(){
        return new Vec2f(x, z);
    }

    public Vec2f yz(){
        return new Vec2f(y, z);
    }
    
    
    public Vec3f floor(){
        x = Maths.floor(x);
        y = Maths.floor(y);
        z = Maths.floor(z);
        
        return this;
    }
    
    public Vec3f round(){
        x = Maths.round(x);
        y = Maths.round(y);
        z = Maths.round(z);
        
        return this;
    }
    
    public Vec3f ceil(){
        x = Maths.ceil(x);
        y = Maths.ceil(y);
        z = Maths.ceil(z);
        
        return this;
    }


    @Override
    public Vec3f clone(){
        return new Vec3f(this);
    }


    public static Vec3f crs(Tuple3f a, Tuple3f b){
        return new Vec3f(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
    }

    public static Vec3f crs(float x1, float y1, float z1, float x2, float y2, float z2){
        return new Vec3f(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2);
    }

    public static float dot(Tuple3f a, Tuple3f b){
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static float dot(float x1, float y1, float z1, float x2, float y2, float z2){
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public static float len(float x, float y, float z){
        return (float) Math.sqrt(x * x + y * y + z * z);
    }

}