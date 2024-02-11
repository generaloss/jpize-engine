package jpize.util.math.vector;

import jpize.util.math.Maths;
import jpize.util.math.matrix.Matrix3;
import jpize.util.math.matrix.Matrix3f;
import jpize.util.math.matrix.Matrix4f;
import jpize.util.math.matrix.Matrix4;

import java.util.Objects;

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


    public Vec3d min(Vec3d vector){
        return new Vec3d(Math.min(x, vector.x), Math.min(y, vector.y), Math.min(z, vector.z));
    }

    public Vec3d max(Vec3d vector){
        return new Vec3d(Math.max(x, vector.x), Math.max(y, vector.y), Math.max(z, vector.z));
    }

    public Vec3d min(Vec3d a, Vec3d b){
        return set(Math.min(a.x, b.x), Math.min(a.y, b.y), Math.min(a.z, b.z));
    }

    public Vec3d max(Vec3d a, Vec3d b){
        return set(Math.max(a.x, b.x), Math.max(a.y, b.y), Math.max(a.z, b.z));
    }
    
    
    /**             VECTOR             */
    
    public double len2(){
        return x * x + y * y + z * z;
    }
    
    public double len(){
        return Math.sqrt(len2());
    }

    public double lenh(){
        return Math.sqrt(x * x + z * z);
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
        return set(0, 0, 0);
    }

    public boolean isZero(){
        return x == 0 && y == 0 && z == 0;
    }


    public Vec3d zeroThatLess(double x, double y, double z){
        if(Math.abs(this.x) < Math.abs(x)) this.x = 0;
        if(Math.abs(this.y) < Math.abs(y)) this.y = 0;
        if(Math.abs(this.z) < Math.abs(z)) this.z = 0;
        return this;
    }

    public Vec3d zeroThatLess(double xyz){
        return zeroThatLess(xyz, xyz, xyz);
    }

    public Vec3d zeroThatLess(Vec3d vector){
        return zeroThatLess(vector.x, vector.y, vector.z);
    }

    public Vec3d zeroThatLess(Vec3f vector){
        return zeroThatLess(vector.x, vector.y, vector.z);
    }

    public Vec3d zeroThatLess(Vec3i vector){
        return zeroThatLess(vector.x, vector.y, vector.z);
    }


    public Vec3d zeroThatZero(double x, double y, double z){
        if(x == 0) this.x = 0;
        if(y == 0) this.y = 0;
        if(z == 0) this.z = 0;
        return this;
    }

    public Vec3d zeroThatZero(double xyz){
        return zeroThatZero(xyz, xyz, xyz);
    }

    public Vec3d zeroThatZero(Vec3d vector){
        return zeroThatZero(vector.x, vector.y, vector.z);
    }

    public Vec3d zeroThatZero(Vec3f vector){
        return zeroThatZero(vector.x, vector.y, vector.z);
    }

    public Vec3d zeroThatZero(Vec3i vector){
        return zeroThatZero(vector.x, vector.y, vector.z);
    }


    public Vec3d zeroThatBigger(double x, double y, double z){
        if(Math.abs(this.x) > Math.abs(x)) this.x = 0;
        if(Math.abs(this.y) > Math.abs(y)) this.y = 0;
        if(Math.abs(this.z) > Math.abs(z)) this.z = 0;
        return this;
    }

    public Vec3d zeroThatBigger(double xyz){
        return zeroThatBigger(xyz, xyz, xyz);
    }

    public Vec3d zeroThatBigger(Vec3d vector){
        return zeroThatBigger(vector.x, vector.y, vector.z);
    }

    public Vec3d zeroThatBigger(Vec3f vector){
        return zeroThatBigger(vector.x, vector.y, vector.z);
    }

    public Vec3d zeroThatBigger(Vec3i vector){
        return zeroThatBigger(vector.x, vector.y, vector.z);
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
        return set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );
    }
    
    public Vec3d crs(double x, double y, double z){
        return set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );
    }

    public Vec3d crs(int x, int y, int z){
        return set(
            this.y * z - this.z * y,
            this.z * x - this.x * z,
            this.x * y - this.y * x
        );
    }
    
    public Vec3d crs(Vec3f vector){
        return set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );
    }
    
    public Vec3d crs(Vec3d vector){
        return set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );
    }

    public Vec3d crs(Vec3i vector){
        return set(
            y * vector.z - z * vector.y,
            z * vector.x - x * vector.z,
            x * vector.y - y * vector.x
        );
    }

    public Vec3d crs(Vec3f a, Vec3f b){
        return set(
            a.y * b.z - a.z * b.y,
            a.z * b.x - a.x * b.z,
            a.x * b.y - a.y * b.x
        );
    }

    public Vec3d crs(Vec3d a, Vec3d b){
        return set(
            a.y * b.z - a.z * b.y,
            a.z * b.x - a.x * b.z,
            a.x * b.y - a.y * b.x
        );
    }

    public Vec3d crs(Vec3i a, Vec3i b){
        return set(
            a.y * b.z - a.z * b.y,
            a.z * b.x - a.x * b.z,
            a.x * b.y - a.y * b.x
        );
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


    public double volume(){
        return x * y * z;
    }
    
    
    public Vec3d copy(){
        return new Vec3d(this);
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

    public Vec3d add(Vec2d a, Vec2d b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec3d add(Vec2f a, Vec2f b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec3d add(Vec2i a, Vec2i b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec3d add(Vec3d a, Vec3d b){
        return set(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public Vec3d add(Vec3f a, Vec3f b){
        return set(a.x + b.x, a.y + b.y, a.z + b.z);
    }

    public Vec3d add(Vec3i a, Vec3i b){
        return set(a.x + b.x, a.y + b.y, a.z + b.z);
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

    public Vec3d sub(Vec2d a, Vec2d b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec3d sub(Vec2f a, Vec2f b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec3d sub(Vec2i a, Vec2i b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec3d sub(Vec3d a, Vec3d b){
        return set(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public Vec3d sub(Vec3f a, Vec3f b){
        return set(a.x - b.x, a.y - b.y, a.z - b.z);
    }

    public Vec3d sub(Vec3i a, Vec3i b){
        return set(a.x - b.x, a.y - b.y, a.z - b.z);
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

    public Vec3d mul(Vec2d a, Vec2d b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec3d mul(Vec2f a, Vec2f b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec3d mul(Vec2i a, Vec2i b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec3d mul(Vec3d a, Vec3d b){
        return set(a.x * b.x, a.y * b.y, a.z * b.z);
    }

    public Vec3d mul(Vec3f a, Vec3f b){
        return set(a.x * b.x, a.y * b.y, a.z * b.z);
    }

    public Vec3d mul(Vec3i a, Vec3i b){
        return set(a.x * b.x, a.y * b.y, a.z * b.z);
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

    public Vec3d div(Vec2d a, Vec2d b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec3d div(Vec2f a, Vec2f b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec3d div(Vec2i a, Vec2i b){
        return set((double) a.x / b.x, (double) a.y / b.y);
    }

    public Vec3d div(Vec3d a, Vec3d b){
        return set(a.x / b.x, a.y / b.y, a.z / b.z);
    }

    public Vec3d div(Vec3f a, Vec3f b){
        return set(a.x / b.x, a.y / b.y, a.z / b.z);
    }

    public Vec3d div(Vec3i a, Vec3i b){
        return set((double) a.x / b.x, (double) a.y / b.y, (double) a.z / b.z);
    }


    public Vec3d mulMat4(float[] matrix){
        return set(
            x * matrix[Matrix4.m00]  +  y * matrix[Matrix4.m10]  +  z * matrix[Matrix4.m20]  +  matrix[Matrix4.m30],
            x * matrix[Matrix4.m01]  +  y * matrix[Matrix4.m11]  +  z * matrix[Matrix4.m21]  +  matrix[Matrix4.m31],
            x * matrix[Matrix4.m02]  +  y * matrix[Matrix4.m12]  +  z * matrix[Matrix4.m22]  +  matrix[Matrix4.m32]
        );
    }

    public Vec3d mulMat4(Matrix4f matrix){
        return mulMat4(matrix.val);
    }

    public Vec3d mulMat3(float[] matrix){
        return set(
            x * matrix[Matrix3.m00]  +  y * matrix[Matrix3.m10]  +  z * matrix[Matrix3.m20],
            x * matrix[Matrix3.m01]  +  y * matrix[Matrix3.m11]  +  z * matrix[Matrix3.m21],
            x * matrix[Matrix3.m02]  +  y * matrix[Matrix3.m12]  +  z * matrix[Matrix3.m22]
        );
    }

    public Vec3d mulMat3(Matrix3f matrix){
        return mulMat3(matrix.val);
    }


    public Vec3f castFloat(){
        return new Vec3f(this);
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
        
        final Vec3d vec = (Vec3d) object;
        return x == vec.x && y == vec.y && z == vec.z;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(x, y, z);
    }
    
    
}
