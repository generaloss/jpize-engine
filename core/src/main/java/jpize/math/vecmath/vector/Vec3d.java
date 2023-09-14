package jpize.math.vecmath.vector;

import jpize.math.Maths;
import jpize.math.vecmath.matrix.Matrix4f;

import java.util.Objects;

import static jpize.math.vecmath.matrix.Matrix3.*;

public class Vec3d{
    
    public Vec3d(){ }
    
    public Vec3d(double x, double y, double z){
        set(x, y, z);
    }
    
    public Vec3d(float x, float y, float z){
        set(x, y, z);
    }
    
    public Vec3d(int x, int y, int z){
        set(x, y, z);
    }

    public Vec3d(double x, double y){
        set(x, y);
    }

    public Vec3d(float x, float y){
        set(x, y);
    }

    public Vec3d(int x, int y){
        set(x, y);
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
    
    public Vec3d(Vec3d vector){
        set(vector);
    }
    
    public Vec3d(Vec3f vector){
        set(vector);
    }
    
    public Vec3d(Vec3i vector){
        set(vector);
    }
    
    public Vec3d(Vec2d vector){
        set(vector);
    }
    
    public Vec3d(Vec2f vector){
        set(vector);
    }
    
    public Vec3d(Vec2i vector){
        set(vector);
    }
    
    
    /**             POINT             */
    
    public double dst(float x, float y, float z){
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    public double dst(double x, double y, double z){
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;
        
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double dst(int x, int y, int z){
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    
    public double dst(Vec3f vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        double dz = z - vector.z;
        
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    public double dst(Vec3d vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        double dz = z - vector.z;
        
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double dst(Vec3i vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        double dz = z - vector.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    
    /**             VECTOR             */
    
    public double len2(){
        return x * x + y * y + z * z;
    }
    
    public double len(){
        return Math.sqrt(len2());
    }
    
    public Vec3d nor(){
        double len = len2();
        if(len == 0 || len == 1)
            return this;
        
        len = Maths.invSqrt(len);
        return mul(len);
    }
    
    
    public Vec3d abs(){
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

    public double dot(int x, int y, int z){
        return this.x * x + this.y * y + this.z * z;
    }

    public double dot(Vec3f vector){
        return x * vector.x + y * vector.y + z * vector.z;
    }
    
    public double dot(Vec3d vector){
        return x * vector.x + y * vector.y + z * vector.z;
    }

    public double dot(Vec3i vector){
        return x * vector.x + y * vector.y + z * vector.z;
    }

    
    public Vec3d crs(float x, float y, float z){
        set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );
        
        return this;
    }
    
    public Vec3d crs(double x, double y, double z){
        set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );
        
        return this;
    }

    public Vec3d crs(int x, int y, int z){
        set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );

        return this;
    }
    
    public Vec3d crs(Vec3f vector){
        set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );

        return this;
    }
    
    public Vec3d crs(Vec3d vector){
        set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );
        
        return this;
    }

    public Vec3d crs(Vec3i vector){
        set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );

        return this;
    }
    
    
    public Vec3d frac(){
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
    
    
    public Vec3d copy(){
        return new Vec3d(this);
    }
    
    
    public static Vec3d crs(Vec3d a, Vec3d b){
        return new Vec3d(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
    }
    
    public static Vec3d crs(double x1, double y1, double z1, double x2, double y2, double z2){
        return new Vec3d(y1 * z2 - z1 * y2, z1 * x2 - x1 * z2, x1 * y2 - y1 * x2);
    }
    
    public static double dot(Vec3d a, Vec3d b){
        return a.x * b.x + a.y * b.y + a.z * b.z;
    }
    
    public static double dot(double x1, double y1, double z1, double x2, double y2, double z2){
        return x1 * x2 + y1 * y2 + z1 * z2;
    }
    
    public static double len(double x, double y, double z){
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    
    /**             TUPLE             */
    
    public double x, y, z;
    
    
    public Vec3d set(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
    public Vec3d set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3d set(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3d set(double x, double y){
        this.x = x;
        this.y = y;
        this.z = 0;
        return this;
    }

    public Vec3d set(float x, float y){
        this.x = x;
        this.y = y;
        this.z = 0;
        return this;
    }

    public Vec3d set(int x, int y){
        this.x = x;
        this.y = y;
        this.z = 0;
        return this;
    }
    
    public Vec3d set(double xyz){
        x = xyz;
        y = xyz;
        z = xyz;
        return this;
    }
    
    public Vec3d set(float xyz){
        x = xyz;
        y = xyz;
        z = xyz;
        return this;
    }

    public Vec3d set(int xyz){
        x = xyz;
        y = xyz;
        z = xyz;
        return this;
    }

    public Vec3d set(Vec3d vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }

    public Vec3d set(Vec3f vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }

    public Vec3d set(Vec3i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }
    
    public Vec3d set(Vec2d vector){
        x = vector.x;
        y = vector.y;
        z = 0;
        return this;
    }
    
    public Vec3d set(Vec2f vector){
        x = vector.x;
        y = vector.y;
        z = 0;
        return this;
    }
    
    public Vec3d set(Vec2i vector){
        x = vector.x;
        y = vector.y;
        z = 0;
        return this;
    }
    
    
    public Vec3d add(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Vec3d add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vec3d add(int x, int y, int z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Vec3d add(double xyz){
        x += xyz;
        y += xyz;
        z += xyz;
        return this;
    }
    
    public Vec3d add(float xyz){
        x += xyz;
        y += xyz;
        z += xyz;
        return this;
    }

    public Vec3d add(int xyz){
        x += xyz;
        y += xyz;
        z += xyz;
        return this;
    }
    
    public Vec3d add(Vec2d vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec3d add(Vec2f vector){
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vec3d add(Vec2i vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec3d add(Vec3d vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this;
    }
    
    public Vec3d add(Vec3f vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this;
    }

    public Vec3d add(Vec3i vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this;
    }
    
    
    public Vec3d sub(double x, double y, double z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
    public Vec3d sub(float x, float y, float z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vec3d sub(int x, int y, int z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
    public Vec3d sub(double xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;
        return this;
    }
    
    public Vec3d sub(float xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;
        return this;
    }

    public Vec3d sub(int xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;
        return this;
    }
    
    public Vec3d sub(Vec2d vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    public Vec3d sub(Vec2f vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vec3d sub(Vec2i vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    public Vec3d sub(Vec3d vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;
        return this;
    }
    
    public Vec3d sub(Vec3f vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;
        return this;
    }

    public Vec3d sub(Vec3i vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;
        return this;
    }
    
    
    public Vec3d mul(double x, double y, double z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }
    
    public Vec3d mul(float x, float y, float z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }

    public Vec3d mul(int x, int y, int z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }
    
    public Vec3d mul(double xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;
        return this;
    }
    
    public Vec3d mul(float xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;
        return this;
    }

    public Vec3d mul(int xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;
        return this;
    }
    
    public Vec3d mul(Vec2d vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec3d mul(Vec2f vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vec3d mul(Vec2i vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec3d mul(Vec3d vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;
        return this;
    }
    
    public Vec3d mul(Vec3f vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;
        return this;
    }

    public Vec3d mul(Vec3i vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;
        return this;
    }
    
    
    public Vec3d div(double x, double y, double z){
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }
    
    public Vec3d div(float x, float y, float z){
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }

    public Vec3d div(int x, int y, int z){
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }
    
    public Vec3d div(double xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;
        return this;
    }
    
    public Vec3d div(float xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;
        return this;
    }

    public Vec3d div(int xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;
        return this;
    }
    
    public Vec3d div(Vec2d vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec3d div(Vec2f vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }

    public Vec3d div(Vec2i vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec3d div(Vec3d vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;
        return this;
    }
    
    public Vec3d div(Vec3f vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;
        return this;
    }

    public Vec3d div(Vec3i vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;
        return this;
    }
    
    
    public Vec3d mul(Matrix4f matrix){
        set(
            x * matrix.val[m00] + y * matrix.val[m10] + z * matrix.val[m20],
            x * matrix.val[m01] + y * matrix.val[m11] + z * matrix.val[m21],
            x * matrix.val[m01] + y * matrix.val[m11] + z * matrix.val[m21]
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
        
        Vec3d tuple = (Vec3d) object;
        return x == tuple.x && y == tuple.y;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(x, y, z);
    }
    
    
}
