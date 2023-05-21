package pize.math.vecmath.vector;

import pize.math.Maths;
import pize.math.vecmath.tuple.*;

public class Vec3d extends Tuple3d implements Cloneable{


    public Vec3d(){}

    public Vec3d(double x, double y, double z){
        set(x, y, z);
    }

    public Vec3d(float x, float y, float z){
        set(x, y, z);
    }

    public Vec3d(int x, int y, int z){
        set(x, y, z);
    }

    public Vec3d(double xyz){
        set(xyz);
    }

    public Vec3d(float xyz){
        set(xyz);
    }

    public Vec3d(int xyz){
        set(xyz);
    }

    public Vec3d(Tuple3d vector){
        set(vector);
    }

    public Vec3d(Tuple3f vector){
        set(vector);
    }

    public Vec3d(Tuple3i vector){
        set(vector);
    }

    public Vec3d(Tuple2d vector){
        set(vector);
    }

    public Vec3d(Tuple2f vector){
        set(vector);
    }

    public Vec3d(Tuple2i vector){
        set(vector);
    }


    public double len(){
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vec3d nor(){
        double len = len();
        if(len == 0 || len == 1)
            return this;

        div(len);
        return this;
    }


    public Vec3d module(){
        if(x < 0)
            x *= -1;
        if(y < 0)
            y *= -1;
        if(z < 0)
            z *= -1;

        return this;
    }

    public Vec3d zero(){
        set(0, 0, 0);

        return this;
    }

    public boolean isZero(){
        return x == 0 && y == 0 && z == 0;
    }


    public double dot(float x, float y, float z){
        return this.x * x + this.y * y + this.z * z;
    }

    public double dot(double x, double y, double z){
        return this.x * x + this.y * y + this.z * z;
    }

    public double dot(Vec3f vector){
        return x * vector.x + y * vector.y + z * vector.z;
    }

    public double dot(Vec3d vector){
        return x * vector.x + y * vector.y + z * vector.z;
    }


    public Vec3d crs(float x, float y, float z){
        this.x = this.y * z - this.z * y;
        this.y = this.z * x - this.x * z;
        this.z = this.x * y - this.y * x;

        return this;
    }

    public Vec3d crs(double x, double y, double z){
        this.x = this.y * z - this.z * y;
        this.y = this.z * x - this.x * z;
        this.z = this.x * y - this.y * x;

        return this;
    }

    public Vec3d crs(Vec3f vector){
        this.x = y * vector.z - z * vector.y;
        this.y = z * vector.x - x * vector.z;
        this.z = x * vector.y - y * vector.x;

        return this;
    }

    public Vec3d crs(Vec3d vector){
        this.x = y * vector.z - z * vector.y;
        this.y = z * vector.x - x * vector.z;
        this.z = x * vector.y - y * vector.x;

        return this;
    }
    
    
    public Vec3d fract(){
        x = Maths.frac(x);
        y = Maths.frac(y);
        z = Maths.frac(z);
        
        return this;
    }


    public Vec2d xy(){
        return new Vec2d(x, y);
    }

    public Vec2d xz(){
        return new Vec2d(x, z);
    }

    public Vec2d yz(){
        return new Vec2d(y, z);
    }
    
    
    public Vec3d floor(){
        x = Maths.floor(x);
        y = Maths.floor(y);
        z = Maths.floor(z);
        
        return this;
    }
    
    public Vec3d round(){
        x = Maths.round(x);
        y = Maths.round(y);
        z = Maths.round(z);
        
        return this;
    }
    
    public Vec3d ceil(){
        x = Maths.ceil(x);
        y = Maths.ceil(y);
        z = Maths.ceil(z);
        
        return this;
    }


    @Override
    public Vec3d clone(){
        return new Vec3d(this);
    }


    public static Vec3d crs(Tuple3d a, Tuple3d b){
        return new Vec3d(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
    }

    public static Vec3d crs(double x1, double y1, double z1, double x2, double y2, double z2){
        return new Vec3d(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2);
    }

    public static double dot(Tuple3d a, Tuple3d b){
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static double dot(double x1, double y1, double z1, double x2, double y2, double z2){
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public static double len(double x, double y, double z){
        return Math.sqrt(x * x + y * y + z * z);
    }

}
