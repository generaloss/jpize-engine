package pize.math.vecmath.vector;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.matrix.Matrix4f;

import java.util.Objects;

import static pize.math.vecmath.matrix.Matrix4.*;

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
    
    
    /**             VECTOR             */
    
    public float len2(){
        return x * x + y * y + z * z;
    }
    
    public float len(){
        return Mathc.sqrt(len2());
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
        set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );
        
        return this;
    }
    
    public Vec3f crs(double x, double y, double z){
        set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );
        
        return this;
    }

    public Vec3f crs(int x, int y, int z){
        set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );

        return this;
    }
    
    public Vec3f crs(Vec3f vector){
        set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );
        
        return this;
    }
    
    public Vec3f crs(Vec3d vector){
        set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );
        
        return this;
    }

    public Vec3f crs(Vec3i vector){
        set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );

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
    
    
    public Vec3f copy(){
        return new Vec3f(this);
    }
    
    
    public static Vec3f crs(Vec3f a, Vec3f b){
        return new Vec3f(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
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
    
    public Vec3f set(Vec2d vector){
        x = (float) vector.x;
        y = (float) vector.y;
        z = 0;
        return this;
    }
    
    public Vec3f set(Vec2f vector){
        x = vector.x;
        y = vector.y;
        z = 0;
        return this;
    }
    
    public Vec3f set(Vec2i vector){
        x = vector.x;
        y = vector.y;
        z = 0;
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
    
    
    public Vec3f add(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
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
        x += xyz;
        y += xyz;
        z += xyz;
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
        x += vector.x;
        y += vector.y;
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
        x += vector.x;
        y += vector.y;
        z += vector.z;
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
    
    
    public Vec3f sub(double x, double y, double z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
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
        x -= xyz;
        y -= xyz;
        z -= xyz;
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
        x -= vector.x;
        y -= vector.y;
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
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;
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
    
    
    public Vec3f mul(double x, double y, double z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
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
        x *= xyz;
        y *= xyz;
        z *= xyz;
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
        x *= vector.x;
        y *= vector.y;
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
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;
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
    
    
    public Vec3f div(double x, double y, double z){
        this.x /= x;
        this.y /= y;
        this.z /= z;
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
        x /= xyz;
        y /= xyz;
        z /= xyz;
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
        x /= vector.x;
        y /= vector.y;
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
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;
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
    
    
    public Vec3f mul(Matrix4f matrix){
        return mul(matrix.val);
    }
    
    public Vec3f mul(float[] matrix){
        set(
            (x * matrix[m00]) + (y * matrix[m10]) + (z * matrix[m20]) + matrix[m30],
            (x * matrix[m01]) + (y * matrix[m11]) + (z * matrix[m21]) + matrix[m31],
            (x * matrix[m02]) + (y * matrix[m12]) + (z * matrix[m22]) + matrix[m32]
        );
        return this;
    }
    
    
    public int x(){
        return Maths.round(x);
    }
    
    public int y(){
        return Maths.round(y);
    }
    
    public int z(){
        return Maths.round(z);
    }
    
    public int xf(){
        return Maths.floor(x);
    }
    
    public int yf(){
        return Maths.floor(y);
    }
    
    public int zf(){
        return Maths.floor(z);
    }
    
    public int xc(){
        return Maths.ceil(x);
    }
    
    public int yc(){
        return Maths.ceil(y);
    }
    
    public int zc(){
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
        return x == tuple.x && y == tuple.y;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(x, y, z);
    }
    
    
}
