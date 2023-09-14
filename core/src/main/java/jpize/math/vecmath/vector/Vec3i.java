package jpize.math.vecmath.vector;

import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.math.vecmath.matrix.Matrix4f;

import java.util.Objects;

import static jpize.math.vecmath.matrix.Matrix4.*;

public class Vec3i{
    
    
    public Vec3i(){ }

    public Vec3i(double x, double y, double z){
        set(x, y, z);
    }

    public Vec3i(float x, float y, float z){
        set(x, y, z);
    }

    public Vec3i(int x, int y, int z){
        set(x, y, z);
    }

    public Vec3i(double x, double y){
        set(x, y);
    }

    public Vec3i(float x, float y){
        set(x, y);
    }

    public Vec3i(int x, int y){
        set(x, y);
    }

    public Vec3i(double xyz){
        set(xyz);
    }

    public Vec3i(float xyz){
        set(xyz);
    }

    public Vec3i(int xyz){
        set(xyz);
    }

    public Vec3i(Vec3d vector){
        set(vector);
    }

    public Vec3i(Vec3f vector){
        set(vector);
    }

    public Vec3i(Vec3i vector){
        set(vector);
    }

    public Vec3i(Vec2d vector){
        set(vector);
    }

    public Vec3i(Vec2f vector){
        set(vector);
    }

    public Vec3i(Vec2i vector){
        set(vector);
    }
    
    
    /**             POINT             */
    
    public float dst(int x, int y, int z){
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        
        return Mathc.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
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
    
    
    public float dst(Vec3i vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        double dz = z - vector.z;
        
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
    
    
    /**             VECTOR             */
    
    public float len(){
        return Mathc.sqrt(x * x + y * y + z * z);
    }
    
    public Vec3i abs(){
        if(x < 0)
            x *= -1;
        if(y < 0)
            y *= -1;
        if(z < 0)
            z *= -1;
        
        return this;
    }
    
    public Vec3i zero(){
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


    public Vec3i crs(int x, int y, int z){
        set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );

        return this;
    }

    public Vec3i crs(Vec3i vector){
        set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );

        return this;
    }

    
    public Vec2i xy(){
        return new Vec2i(x, y);
    }
    
    public Vec2i xz(){
        return new Vec2i(x, z);
    }
    
    public Vec2i yz(){
        return new Vec2i(y, z);
    }
    
    
    public Vec3i copy(){
        return new Vec3i(this);
    }


    public static Vec3i crs(Vec3i a, Vec3i b){
        return new Vec3i(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
    }

    public static Vec3i crs(int x1, int y1, int z1, int x2, int y2, int z2){
        return new Vec3i(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2);
    }

    public static float dot(Vec3i a, Vec3i b){
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }

    public static float dot(int x1, int y1, int z1, int x2, int y2, int z2){
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public static float len(int x, int y, int z){
        return (float) Math.sqrt(x * x + y * y + z * z);
    }
    
    
    /**             TUPLE             */
    
    public int x, y, z;


    public Vec3i set(double x, double y, double z){
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
        return this;
    }

    public Vec3i set(float x, float y, float z){
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
        return this;
    }

    public Vec3i set(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3i set(double x, double y){
        this.x = (int) x;
        this.y = (int) y;
        this.z = 0;
        return this;
    }

    public Vec3i set(float x, float y){
        this.x = (int) x;
        this.y = (int) y;
        this.z = 0;
        return this;
    }

    public Vec3i set(int x, int y){
        this.x = x;
        this.y = y;
        this.z = 0;
        return this;
    }

    public Vec3i set(double xyz){
        x = (int) xyz;
        y = (int) xyz;
        z = (int) xyz;
        return this;
    }

    public Vec3i set(float xyz){
        x = (int) xyz;
        y = (int) xyz;
        z = (int) xyz;
        return this;
    }

    public Vec3i set(int xyz){
        x = xyz;
        y = xyz;
        z = xyz;
        return this;
    }

    public Vec3i set(Vec3d vector){
        x = (int) vector.x;
        y = (int) vector.y;
        z = (int) vector.z;
        return this;
    }

    public Vec3i set(Vec3f vector){
        x = (int) vector.x;
        y = (int) vector.y;
        z = (int) vector.z;
        return this;
    }

    public Vec3i set(Vec3i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }

    public Vec3i set(Vec2d vector){
        x = (int) vector.x;
        y = (int) vector.y;
        z = 0;
        return this;
    }

    public Vec3i set(Vec2f vector){
        x = (int) vector.x;
        y = (int) vector.y;
        z = 0;
        return this;
    }

    public Vec3i set(Vec2i vector){
        x = vector.x;
        y = vector.y;
        z = 0;
        return this;
    }
    
    
    public Vec3i add(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Vec3i add(float x, float y, float z){
        this.x += (int) x;
        this.y += (int) y;
        this.z += (int) z;
        return this;
    }
    
    public Vec3i add(int x, int y, int z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Vec3i add(double xyz){
        x += (int) xyz;
        y += (int) xyz;
        z += (int) xyz;
        return this;
    }
    
    public Vec3i add(float xyz){
        x += (int) xyz;
        y += (int) xyz;
        z += (int) xyz;
        return this;
    }
    
    public Vec3i add(int xyz){
        x += xyz;
        y += xyz;
        z += xyz;
        return this;
    }
    
    public Vec3i add(Vec2d vector){
        x += (int) vector.x;
        y += (int) vector.y;
        return this;
    }
    
    public Vec3i add(Vec2f vector){
        x += (int) vector.x;
        y += (int) vector.y;
        return this;
    }
    
    public Vec3i add(Vec2i vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec3i add(Vec3d vector){
        x += (int) vector.x;
        y += (int) vector.y;
        z += (int) vector.z;
        return this;
    }
    
    public Vec3i add(Vec3f vector){
        x += (int) vector.x;
        y += (int) vector.y;
        z += (int) vector.z;
        return this;
    }
    
    public Vec3i add(Vec3i vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this;
    }
    
    
    public Vec3i sub(double x, double y, double z){
        this.x -= (int) x;
        this.y -= (int) y;
        this.z -= (int) z;
        return this;
    }
    
    public Vec3i sub(float x, float y, float z){
        this.x -= (int) x;
        this.y -= (int) y;
        this.z -= (int) z;
        return this;
    }
    
    public Vec3i sub(int x, int y, int z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
    public Vec3i sub(double xyz){
        x -= (int) xyz;
        y -= (int) xyz;
        z -= (int) xyz;
        return this;
    }
    
    public Vec3i sub(float xyz){
        x -= (int) xyz;
        y -= (int) xyz;
        z -= (int) xyz;
        return this;
    }
    
    public Vec3i sub(int xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;
        return this;
    }
    
    public Vec3i sub(Vec2d vector){
        x -= (int) vector.x;
        y -= (int) vector.y;
        return this;
    }
    
    public Vec3i sub(Vec2f vector){
        x -= (int) vector.x;
        y -= (int) vector.y;
        return this;
    }
    
    public Vec3i sub(Vec2i vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    public Vec3i sub(Vec3d vector){
        x -= (int) vector.x;
        y -= (int) vector.y;
        z -= (int) vector.z;
        return this;
    }

    public Vec3i sub(Vec3f vector){
        x -= (int) vector.x;
        y -= (int) vector.y;
        z -= (int) vector.z;
        return this;
    }

    public Vec3i sub(Vec3i vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;
        return this;
    }
    
    
    public Vec3i mul(double x, double y, double z){
        this.x *= (int) x;
        this.y *= (int) y;
        this.z *= (int) z;
        return this;
    }
    
    public Vec3i mul(float x, float y, float z){
        this.x *= (int) x;
        this.y *= (int) y;
        this.z *= (int) z;
        return this;
    }
    
    public Vec3i mul(int x, int y, int z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }
    
    public Vec3i mul(double xyz){
        x *= (int) xyz;
        y *= (int) xyz;
        z *= (int) xyz;
        return this;
    }
    
    public Vec3i mul(float xyz){
        x *= (int) xyz;
        y *= (int) xyz;
        z *= (int) xyz;
        return this;
    }
    
    public Vec3i mul(int xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;
        return this;
    }
    
    public Vec3i mul(Vec2d vector){
        x *= (int) vector.x;
        y *= (int) vector.y;
        return this;
    }
    
    public Vec3i mul(Vec2f vector){
        x *= (int) vector.x;
        y *= (int) vector.y;
        return this;
    }

    public Vec3i mul(Vec2i vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec3i mul(Vec3d vector){
        x *= (int) vector.x;
        y *= (int) vector.y;
        z *= (int) vector.z;
        return this;
    }
    
    public Vec3i mul(Vec3f vector){
        x *= (int) vector.x;
        y *= (int) vector.y;
        z *= (int) vector.z;
        return this;
    }

    public Vec3i mul(Vec3i vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;
        return this;
    }

    
    public Vec3i div(double x, double y, double z){
        this.x /= (int) x;
        this.y /= (int) y;
        this.z /= (int) z;
        return this;
    }
    
    public Vec3i div(float x, float y, float z){
        this.x /= (int) x;
        this.y /= (int) y;
        this.z /= (int) z;
        return this;
    }
    
    public Vec3i div(int x, int y, int z){
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }
    
    public Vec3i div(double xyz){
        x /= (int) xyz;
        y /= (int) xyz;
        z /= (int) xyz;
        return this;
    }
    
    public Vec3i div(float xyz){
        x /= (int) xyz;
        y /= (int) xyz;
        z /= (int) xyz;
        return this;
    }
    
    public Vec3i div(int xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;
        return this;
    }
    
    public Vec3i div(Vec2d vector){
        x /= (int) vector.x;
        y /= (int) vector.y;
        return this;
    }
    
    public Vec3i div(Vec2f vector){
        x /= (int) vector.x;
        y /= (int) vector.y;
        return this;
    }

    public Vec3i div(Vec2i vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec3i div(Vec3d vector){
        x /= (int) vector.x;
        y /= (int) vector.y;
        z /= (int) vector.z;
        return this;
    }
    
    public Vec3i div(Vec3f vector){
        x /= (int) vector.x;
        y /= (int) vector.y;
        z /= (int) vector.z;
        return this;
    }

    public Vec3i div(Vec3i vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;
        return this;
    }


    public Vec3i mul(Matrix4f matrix){
        return mul(matrix.val);
    }

    public Vec3i mul(float[] matrix){
        set(
            Maths.round( (x * matrix[m00]) + (y * matrix[m01]) + (z * matrix[m02]) + matrix[m03] ),
            Maths.round( (x * matrix[m10]) + (y * matrix[m11]) + (z * matrix[m12]) + matrix[m13] ),
            Maths.round( (x * matrix[m20]) + (y * matrix[m21]) + (z * matrix[m22]) + matrix[m23] )
        );
        return this;
    }

    
    @Override
    public String toString(){
        return x + ", " + y + ", " + z;
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null || getClass() != object.getClass())
            return false;
        
        final Vec3i tuple = (Vec3i) object;
        return x == tuple.x && y == tuple.y;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(x, y, z);
    }
    
}
