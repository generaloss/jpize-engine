package jpize.math.vecmath.vector;

import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.math.vecmath.matrix.Matrix3;
import jpize.math.vecmath.matrix.Matrix3f;
import jpize.math.vecmath.matrix.Matrix4;
import jpize.math.vecmath.matrix.Matrix4f;

import java.util.Objects;

public class Vec3f{
    
    public Vec3f(){ }
    
    public Vec3f(double x, double y, double z){
        set(x, y, z);
    }
    
    public Vec3f(float x, float y, float z){
        set(x, y, z);
    }
    
    public Vec3f(int x, int y, int z){
        set(x, y, z);
    }

    public Vec3f(double x, double y){
        set(x, y);
    }

    public Vec3f(float x, float y){
        set(x, y);
    }

    public Vec3f(int x, int y){
        set(x, y);
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
    
    public Vec3f(Vec3d vector){
        set(vector);
    }
    
    public Vec3f(Vec3f vector){
        set(vector);
    }
    
    public Vec3f(Vec3i vector){
        set(vector);
    }
    
    public Vec3f(Vec2d vector){
        set(vector);
    }
    
    public Vec3f(Vec2f vector){
        set(vector);
    }
    
    public Vec3f(Vec2i vector){
        set(vector);
    }
    
    
    /**             POINT             */
    
    public float dst(float x, float y, float z){
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        
        return Mathc.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    public float dst(double x, double y, double z){
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        
        return Mathc.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public float dst(int x, int y, int z){
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;

        return Mathc.sqrt(dx * dx + dy * dy + dz * dz);
    }
    

    public float dst(Vec3f vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        double dz = z - vector.z;
        
        return Mathc.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    public float dst(Vec3d vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        double dz = z - vector.z;
        
        return Mathc.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public float dst(Vec3i vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        double dz = z - vector.z;

        return Mathc.sqrt(dx * dx + dy * dy + dz * dz);
    }


    public Vec3f min(Vec3f vector){
        return new Vec3f(Math.min(x, vector.x), Math.min(y, vector.y), Math.min(z, vector.z));
    }

    public Vec3f max(Vec3f vector){
        return new Vec3f(Math.max(x, vector.x), Math.max(y, vector.y), Math.max(z, vector.z));
    }

    public Vec3f min(Vec3f a, Vec3f b){
        return set(Math.min(a.x, b.x), Math.min(a.y, b.y), Math.min(a.z, b.z));
    }

    public Vec3f max(Vec3f a, Vec3f b){
        return set(Math.max(a.x, b.x), Math.max(a.y, b.y), Math.max(a.z, b.z));
    }
    
    
    /**             VECTOR             */
    
    public float len2(){
        return x * x + y * y + z * z;
    }
    
    public float len(){
        return Mathc.sqrt(len2());
    }

    public float lenXZ(){
        return Mathc.sqrt(x * x + z * z);
    }
    
    public Vec3f nor(){
        double len = len2();
        if(len == 0 || len == 1)
            return this;
        
        len = Maths.invSqrt(len);
        return mul(len);
    }
    
    
    public Vec3f abs(){
        if(x < 0)
            x *= -1;
        if(y < 0)
            y *= -1;
        if(z < 0)
            z *= -1;
        
        return this;
    }


    public Vec3f zero(){
        return set(0, 0, 0);
    }

    public boolean isZero(){
        return x == 0 && y == 0 && z == 0;
    }


    public Vec3f zeroThatLess(double x, double y, double z){
        if(Math.abs(this.x) < Math.abs(x)) this.x = 0;
        if(Math.abs(this.y) < Math.abs(y)) this.y = 0;
        if(Math.abs(this.z) < Math.abs(z)) this.z = 0;
        return this;
    }

    public Vec3f zeroThatLess(double xyz){
        return zeroThatLess(xyz, xyz, xyz);
    }

    public Vec3f zeroThatLess(Vec3d vector){
        return zeroThatLess(vector.x, vector.y, vector.z);
    }

    public Vec3f zeroThatLess(Vec3f vector){
        return zeroThatLess(vector.x, vector.y, vector.z);
    }

    public Vec3f zeroThatLess(Vec3i vector){
        return zeroThatLess(vector.x, vector.y, vector.z);
    }


    public Vec3f zeroThatZero(double x, double y, double z){
        if(x == 0) this.x = 0;
        if(y == 0) this.y = 0;
        if(z == 0) this.z = 0;
        return this;
    }

    public Vec3f zeroThatZero(double xyz){
        return zeroThatZero(xyz, xyz, xyz);
    }

    public Vec3f zeroThatZero(Vec3d vector){
        return zeroThatZero(vector.x, vector.y, vector.z);
    }

    public Vec3f zeroThatZero(Vec3f vector){
        return zeroThatZero(vector.x, vector.y, vector.z);
    }

    public Vec3f zeroThatZero(Vec3i vector){
        return zeroThatZero(vector.x, vector.y, vector.z);
    }


    public Vec3f zeroThatBigger(double x, double y, double z){
        if(Math.abs(this.x) > Math.abs(x)) this.x = 0;
        if(Math.abs(this.y) > Math.abs(y)) this.y = 0;
        if(Math.abs(this.z) > Math.abs(z)) this.z = 0;
        return this;
    }

    public Vec3f zeroThatBigger(double xyz){
        return zeroThatBigger(xyz, xyz, xyz);
    }

    public Vec3f zeroThatBigger(Vec3d vector){
        return zeroThatBigger(vector.x, vector.y, vector.z);
    }

    public Vec3f zeroThatBigger(Vec3f vector){
        return zeroThatBigger(vector.x, vector.y, vector.z);
    }

    public Vec3f zeroThatBigger(Vec3i vector){
        return zeroThatBigger(vector.x, vector.y, vector.z);
    }

    
    public float dot(float x, float y, float z){
        return this.x * x + this.y * y + this.z * z;
    }
    
    public double dot(double x, double y, double z){
        return this.x * x + this.y * y + this.z * z;
    }

    public double dot(int x, int y, int z){
        return this.x * x + this.y * y + this.z * z;
    }
    
    public float dot(Vec3f vector){
        return x * vector.x + y * vector.y + z * vector.z;
    }
    
    public double dot(Vec3d vector){
        return x * vector.x + y * vector.y + z * vector.z;
    }

    public double dot(Vec3i vector){
        return x * vector.x + y * vector.y + z * vector.z;
    }

    
    public Vec3f crs(float x, float y, float z){
        return set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );
    }
    
    public Vec3f crs(double x, double y, double z){
        return set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );
    }

    public Vec3f crs(int x, int y, int z){
        return set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );
    }
    
    public Vec3f crs(Vec3f vector){
        return set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );
    }
    
    public Vec3f crs(Vec3d vector){
        return set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );
    }

    public Vec3f crs(Vec3i vector){
        return set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );
    }

    public Vec3f crs(Vec3f a, Vec3f b){
        return set(
            a.y * b.z - a.z * b.y,
            a.z * b.x - a.x * b.z,
            a.x * b.y - a.y * b.x
        );
    }

    public Vec3f crs(Vec3d a, Vec3d b){
        return set(
            a.y * b.z - a.z * b.y,
            a.z * b.x - a.x * b.z,
            a.x * b.y - a.y * b.x
        );
    }

    public Vec3f crs(Vec3i a, Vec3i b){
        return set(
            a.y * b.z - a.z * b.y,
            a.z * b.x - a.x * b.z,
            a.x * b.y - a.y * b.x
        );
    }


    public Vec3f rotX(double degrees){
        final float cos = Maths.cosDeg(degrees);
        final float sin = Maths.sinDeg(degrees);

        final float newY = y * cos + z * sin;
        z = y * -sin + z * cos;
        y = newY;

        return this;
    }

    public Vec3f rotY(double degrees){
        final float cos = Maths.cosDeg(degrees);
        final float sin = Maths.sinDeg(degrees);

        final float newX = x * cos + z * -sin;
        z = x * sin + z * cos;
        x = newX;

        return this;
    }

    public Vec3f rotZ(double degrees){
        final float cos = Maths.cosDeg(degrees);
        final float sin = Maths.sinDeg(degrees);

        final float newX = x * cos + y * sin;
        y = x * -sin + y * cos;
        x = newX;

        return this;
    }
    
    
    public Vec3f frac(){
        x = Maths.frac(x);
        y = Maths.frac(y);
        z = Maths.frac(z);
        
        return this;
    }
    
    
    public Vec3f lerp(Vec3f start, Vec3f end, float t){
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


    public float volume(){
        return x * y * z;
    }
    
    
    public Vec3f copy(){
        return new Vec3f(this);
    }

    
    public static Vec3f crs(float x1, float y1, float z1, float x2, float y2, float z2){
        return new Vec3f(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2);
    }
    
    public static float dot(Vec3f a, Vec3f b){
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }
    
    public static float dot(float x1, float y1, float z1, float x2, float y2, float z2){
        return x1 * x2 + y1 * y2 + z1 * z2;
    }
    
    public static float len(float x, float y, float z){
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
    
    
    /**             TUPLE             */
    
    public float x, y, z;
    
    
    public Vec3f set(double x, double y, double z){
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
        return this;
    }
    
    public Vec3f set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3f set(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3f set(double x, double y){
        this.x = (float) x;
        this.y = (float) y;
        return this;
    }

    public Vec3f set(float x, float y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec3f set(int x, int y){
        this.x = x;
        this.y = y;
        this.z = 0;
        return this;
    }
    
    public Vec3f set(double xyz){
        x = (float) xyz;
        y = (float) xyz;
        z = (float) xyz;
        return this;
    }
    
    public Vec3f set(float xyz){
        x = xyz;
        y = xyz;
        z = xyz;
        return this;
    }

    public Vec3f set(int xyz){
        x = xyz;
        y = xyz;
        z = xyz;
        return this;
    }
    
    public Vec3f set(Vec3d vector){
        x = (float) vector.x;
        y = (float) vector.y;
        z = (float) vector.z;
        return this;
    }
    
    public Vec3f set(Vec3f vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }
    
    public Vec3f set(Vec3i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }

    public Vec3f set(Vec2d vector){
        x = (float) vector.x;
        y = (float) vector.y;
        return this;
    }

    public Vec3f set(Vec2f vector){
        x = vector.x;
        y = vector.y;
        return this;
    }

    public Vec3f set(Vec2i vector){
        x = vector.x;
        y = vector.y;
        return this;
    }
    
    
    public Vec3f add(double x, double y, double z){
        this.x += (float) x;
        this.y += (float) y;
        this.z += (float) z;
        return this;
    }
    
    public Vec3f add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vec3f add(int x, int y, int z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Vec3f add(double xyz){
        x += (float) xyz;
        y += (float) xyz;
        z += (float) xyz;
        return this;
    }
    
    public Vec3f add(float xyz){
        x += xyz;
        y += xyz;
        z += xyz;
        return this;
    }

    public Vec3f add(int xyz){
        x += xyz;
        y += xyz;
        z += xyz;
        return this;
    }
    
    public Vec3f add(Vec2d vector){
        x += (float) vector.x;
        y += (float) vector.y;
        return this;
    }
    
    public Vec3f add(Vec2f vector){
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vec3f add(Vec2i vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec3f add(Vec3d vector){
        x += (float) vector.x;
        y += (float) vector.y;
        z += (float) vector.z;
        return this;
    }
    
    public Vec3f add(Vec3f vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this;
    }

    public Vec3f add(Vec3i vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this;
    }

    public Vec3f add(Vec2d a, Vec2d b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec3f add(Vec2f a, Vec2f b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec3f add(Vec2i a, Vec2i b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec3f add(Vec3d a, Vec3d b){
        return set(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public Vec3f add(Vec3f a, Vec3f b){
        return set(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public Vec3f add(Vec3i a, Vec3i b){
        return set(a.x + b.x, a.y + b.y, a.z + b.z);
    }
    
    
    public Vec3f sub(double x, double y, double z){
        this.x -= (float) x;
        this.y -= (float) y;
        this.z -= (float) z;
        return this;
    }
    
    public Vec3f sub(float x, float y, float z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vec3f sub(int x, int y, int z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
    public Vec3f sub(double xyz){
        x -= (float) xyz;
        y -= (float) xyz;
        z -= (float) xyz;
        return this;
    }
    
    public Vec3f sub(float xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;
        return this;
    }

    public Vec3f sub(int xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;
        return this;
    }

    public Vec3f sub(Vec2d vector){
        x -= (float) vector.x;
        y -= (float) vector.y;
        return this;
    }
    
    public Vec3f sub(Vec2f vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vec3f sub(Vec2i vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vec3f sub(Vec3d vector){
        x -= (float) vector.x;
        y -= (float) vector.y;
        z -= (float) vector.z;
        return this;
    }
    
    public Vec3f sub(Vec3f vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;
        return this;
    }

    public Vec3f sub(Vec3i vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;
        return this;
    }

    public Vec3f sub(Vec2d a, Vec2d b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec3f sub(Vec2f a, Vec2f b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec3f sub(Vec2i a, Vec2i b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec3f sub(Vec3d a, Vec3d b){
        return set(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public Vec3f sub(Vec3f a, Vec3f b){
        return set(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public Vec3f sub(Vec3i a, Vec3i b){
        return set(a.x - b.x, a.y - b.y, a.z - b.z);
    }
    
    
    public Vec3f mul(double x, double y, double z){
        this.x *= (float) x;
        this.y *= (float) y;
        this.z *= (float) z;
        return this;
    }

    public Vec3f mul(int x, int y, int z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }
    
    public Vec3f mul(float x, float y, float z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }
    
    public Vec3f mul(double xyz){
        x *= (float) xyz;
        y *= (float) xyz;
        z *= (float) xyz;
        return this;
    }
    
    public Vec3f mul(float xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;
        return this;
    }

    public Vec3f mul(int xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;
        return this;
    }
    
    public Vec3f mul(Vec2d vector){
        x *= (float) vector.x;
        y *= (float) vector.y;
        return this;
    }
    
    public Vec3f mul(Vec2f vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vec3f mul(Vec2i vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec3f mul(Vec3d vector){
        x *= (float) vector.x;
        y *= (float) vector.y;
        z *= (float) vector.z;
        return this;
    }
    
    public Vec3f mul(Vec3f vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;
        return this;
    }

    public Vec3f mul(Vec3i vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;
        return this;
    }

    public Vec3f mul(Vec2d a, Vec2d b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec3f mul(Vec2f a, Vec2f b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec3f mul(Vec2i a, Vec2i b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec3f mul(Vec3d a, Vec3d b){
        return set(a.x * b.x, a.y * b.y, a.z * b.z);
    }

    public Vec3f mul(Vec3f a, Vec3f b){
        return set(a.x * b.x, a.y * b.y, a.z * b.z);
    }

    public Vec3f mul(Vec3i a, Vec3i b){
        return set(a.x * b.x, a.y * b.y, a.z * b.z);
    }
    
    
    public Vec3f div(double x, double y, double z){
        this.x /= (float) x;
        this.y /= (float) y;
        this.z /= (float) z;
        return this;
    }
    
    public Vec3f div(float x, float y, float z){
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Vec3f div(int x, int y, int z){
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }
    
    public Vec3f div(double xyz){
        x /= (float) xyz;
        y /= (float) xyz;
        z /= (float) xyz;
        return this;
    }
    
    public Vec3f div(float xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;
        return this;
    }

    public Vec3f div(int xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;
        return this;
    }
    
    public Vec3f div(Vec2d vector){
        x /= (float) vector.x;
        y /= (float) vector.y;
        return this;
    }
    
    public Vec3f div(Vec2f vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }

    public Vec3f div(Vec2i vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec3f div(Vec3d vector){
        x /= (float) vector.x;
        y /= (float) vector.y;
        z /= (float) vector.z;
        return this;
    }
    
    public Vec3f div(Vec3f vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;
        return this;
    }

    public Vec3f div(Vec3i vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;
        return this;
    }

    public Vec3f div(Vec2d a, Vec2d b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec3f div(Vec2f a, Vec2f b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec3f div(Vec2i a, Vec2i b){
        return set((float) a.x / b.x, (float) a.y / b.y);
    }

    public Vec3f div(Vec3d a, Vec3d b){
        return set(a.x / b.x, a.y / b.y, a.z / b.z);
    }

    public Vec3f div(Vec3f a, Vec3f b){
        return set(a.x / b.x, a.y / b.y, a.z / b.z);
    }

    public Vec3f div(Vec3i a, Vec3i b){
        return set((float) a.x / b.x, (float) a.y / b.y, (float) a.z / b.z);
    }
    
    
    public Vec3f mulMat4(float[] matrix){
        return set(
            x * matrix[Matrix4.m00]  +  y * matrix[Matrix4.m10]  +  z * matrix[Matrix4.m20]  +  matrix[Matrix4.m30],
            x * matrix[Matrix4.m01]  +  y * matrix[Matrix4.m11]  +  z * matrix[Matrix4.m21]  +  matrix[Matrix4.m31],
            x * matrix[Matrix4.m02]  +  y * matrix[Matrix4.m12]  +  z * matrix[Matrix4.m22]  +  matrix[Matrix4.m32]
        );
    }

    public Vec3f mulMat4(Matrix4f matrix){
        return mulMat4(matrix.val);
    }

    public Vec3f mulMat3(float[] matrix){
        return set(
            x * matrix[Matrix3.m00]  +  y * matrix[Matrix3.m10]  +  z * matrix[Matrix3.m20],
            x * matrix[Matrix3.m01]  +  y * matrix[Matrix3.m11]  +  z * matrix[Matrix3.m21],
            x * matrix[Matrix3.m02]  +  y * matrix[Matrix3.m12]  +  z * matrix[Matrix3.m22]
        );
    }

    public Vec3f mulMat3(Matrix3f matrix){
        return mulMat3(matrix.val);
    }


    public Vec3d castDouble(){
        return new Vec3d(this);
    }

    public Vec3i castInt(){
        return new Vec3i(this);
    }

    
    public int xFloor(){
        return Maths.floor(x);
    }
    
    public int yFloor(){
        return Maths.floor(y);
    }
    
    public int zFloor(){
        return Maths.floor(z);
    }

    public int xRound(){
        return Maths.round(x);
    }

    public int yRound(){
        return Maths.round(y);
    }

    public int zRound(){
        return Maths.round(z);
    }

    public int xCeil(){
        return Maths.ceil(x);
    }
    
    public int yCeil(){
        return Maths.ceil(y);
    }
    
    public int zCeil(){
        return Maths.ceil(z);
    }
    
    
    @Override
    public String toString(){
        return x + ", " + y + ", " + z;
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null || getClass() != object.getClass())
            return false;
        
        final Vec3f tuple = (Vec3f) object;
        return x == tuple.x && y == tuple.y && z == tuple.z;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(x, y, z);
    }
    
    
}
