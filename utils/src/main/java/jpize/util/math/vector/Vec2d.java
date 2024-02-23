package jpize.util.math.vector;

import jpize.util.math.Mathc;
import jpize.util.math.Maths;
import jpize.util.math.matrix.Matrix3;
import jpize.util.math.matrix.Matrix3f;

import java.util.Objects;

public class Vec2d{
    
    public Vec2d(){ }
    
    public Vec2d(double x, double y){
        set(x, y);
    }
    
    public Vec2d(float x, float y){
        set(x, y);
    }

    public Vec2d(int x, int y){
        set(x, y);
    }

    public Vec2d(double xy){
        set(xy);
    }
    
    public Vec2d(float xy){
        set(xy);
    }

    public Vec2d(int xy){
        set(xy);
    }

    public Vec2d(Vec3d vector){
        set(vector);
    }

    public Vec2d(Vec3f vector){
        set(vector);
    }

    public Vec2d(Vec3i vector){
        set(vector);
    }

    public Vec2d(Vec2d vector){
        set(vector);
    }
    
    public Vec2d(Vec2f vector){
        set(vector);
    }

    public Vec2d(Vec2i vector){
        set(vector);
    }

    
    /**             POINT             */
    
    public double dst(int x, int y){
        double dx = this.x - x;
        double dy = this.y - y;
        
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public double dst(double x, double y){
        double dx = this.x - x;
        double dy = this.y - y;
        
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public double dst(float x, float y){
        double dx = this.x - x;
        double dy = this.y - y;
        
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    
    public double dst(Vec2i vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public double dst(Vec2d vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public double dst(Vec2f vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        
        return Math.sqrt(dx * dx + dy * dy);
    }


    public Vec2d min(Vec2d vector){
        return new Vec2d(Math.min(x, vector.x), Math.min(y, vector.y));
    }

    public Vec2d max(Vec2d vector){
        return new Vec2d(Math.max(x, vector.x), Math.max(y, vector.y));
    }

    public Vec2d min(Vec2d a, Vec2d b){
        return set(Math.min(a.x, b.x), Math.min(a.y, b.y));
    }

    public Vec2d max(Vec2d a, Vec2d b){
        return set(Math.max(a.x, b.x), Math.max(a.y, b.y));
    }
    
    
    /**             VECTOR             */
    
    public double len2(){
        return x * x + y * y;
    }
    
    public double len(){
        return Math.sqrt(len2());
    }
    
    public Vec2d nor(){
        double len = len2();
        if(len == 0 || len == 1)
            return this;
        
        len = Maths.invSqrt(len);
        return mul(len);
    }
    
    
    public Vec2d abs(){
        if(x < 0)
            x *= -1;
        if(y < 0)
            y *= -1;
        
        return this;
    }


    public Vec2d zero(){
        return set(0, 0);
    }

    public boolean isZero(){
        return x == 0 && y == 0;
    }


    public Vec2d zeroThatLess(double x, double y){
        if(Math.abs(this.x) < Math.abs(x)) this.x = 0;
        if(Math.abs(this.y) < Math.abs(y)) this.y = 0;
        return this;
    }

    public Vec2d zeroThatLess(double xy){
        return zeroThatLess(xy, xy);
    }

    public Vec2d zeroThatLess(Vec2d vector){
        return zeroThatLess(vector.x, vector.y);
    }

    public Vec2d zeroThatLess(Vec2f vector){
        return zeroThatLess(vector.x, vector.y);
    }

    public Vec2d zeroThatLess(Vec2i vector){
        return zeroThatLess(vector.x, vector.y);
    }


    public Vec2d zeroThatZero(double x, double y){
        if(x == 0) this.x = 0;
        if(y == 0) this.y = 0;
        return this;
    }

    public Vec2d zeroThatZero(double xy){
        return zeroThatZero(xy, xy);
    }

    public Vec2d zeroThatZero(Vec2d vector){
        return zeroThatZero(vector.x, vector.y);
    }

    public Vec2d zeroThatZero(Vec2f vector){
        return zeroThatZero(vector.x, vector.y);
    }

    public Vec2d zeroThatZero(Vec2i vector){
        return zeroThatZero(vector.x, vector.y);
    }


    public Vec2d zeroThatBigger(double x, double y){
        if(Math.abs(this.x) > Math.abs(x)) this.x = 0;
        if(Math.abs(this.y) > Math.abs(y)) this.y = 0;
        return this;
    }

    public Vec2d zeroThatBigger(double xy){
        return zeroThatBigger(xy, xy);
    }

    public Vec2d zeroThatBigger(Vec2d vector){
        return zeroThatBigger(vector.x, vector.y);
    }

    public Vec2d zeroThatBigger(Vec2f vector){
        return zeroThatBigger(vector.x, vector.y);
    }

    public Vec2d zeroThatBigger(Vec2i vector){
        return zeroThatBigger(vector.x, vector.y);
    }
    
    
    public double dot(float x, float y){
        return this.x * x + this.y * y;
    }
    
    public double dot(double x, double y){
        return this.x * x + this.y * y;
    }
    
    public double dot(Vec2f vector){
        return this.x * vector.x + this.y * vector.y;
    }
    
    public double dot(Vec2d vector){
        return this.x * vector.x + this.y * vector.y;
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
    
    
    public Vec2d frac(){
        x = Maths.frac(x);
        y = Maths.frac(y);
        
        return this;
    }
    
    
    public Vec2d floor(){
        x = Maths.floor(x);
        y = Maths.floor(y);
        
        return this;
    }
    
    public Vec2d round(){
        x = Maths.round(x);
        y = Maths.round(y);
        
        return this;
    }
    
    public Vec2d ceil(){
        x = Maths.ceil(x);
        y = Maths.ceil(y);
        
        return this;
    }
    
    
    public double deg(Vec2d vector){
        return rad(vector) * Maths.toDeg;
    }
    
    public double rad(Vec2d vector){
        final double cos = dot(vector) / (len() * vector.len());
        return Math.acos(Maths.clamp(cos, -1, 1));
    }


    public float deg(Vec2f vector){
        return rad(vector) * Maths.toDeg;
    }

    public float rad(Vec2f vector){
        final double cos = dot(vector) / (len() * vector.len());
        return Mathc.acos(Maths.clamp(cos, -1, 1));
    }


    public Vec2d rotd(double degrees){
        return rotr(degrees * Maths.toRad);
    }

    public Vec2d rotr(double radians){
        float cos = Mathc.cos(radians);
        float sin = Mathc.sin(radians);

        set(x * cos - y * sin, x * sin + y * cos);
        return this;
    }

    public Vec2d rotd(double degrees, Vec2f origin){
        return rotr(degrees * Maths.toRad, origin);
    }

    public Vec2d rotd(double degrees, float originX, float originY){
        return rotr(degrees * Maths.toRad, originX, originY);
    }

    public Vec2d rotr(double radians, Vec2f origin){
        return rotr(radians, origin.x, origin.y);
    }

    public Vec2d rotr(double radians, float originX, float originY){
        float cos = Mathc.cos(radians);
        float sin = Mathc.sin(radians);

        sub(originX, originY);
        set(
            x * cos - y * sin + originX,
            x * sin + y * cos + originY
        );

        return this;
    }


    public double area(){
        return x * y;
    }
    
    
    public Vec2d copy(){
        return new Vec2d(this);
    }


    public static double crs(Vec2d a, Vec2d b){
        return a.x * b.y - a.y * b.x;
    }

    public static double crs(double x1, double y1, double x2, double y2){
        return x1 * y2 - y1 * x2;
    }

    public static double dot(Vec2d a, Vec2d b){
        return a.x * b.x + a.y * b.y;
    }

    public static double dot(double x1, double y1, double x2, double y2){
        return x1 * x2 + y1 * y2;
    }
    
    public static double len(double x, double y){
        return Math.sqrt(x * x + y * y);
    }
    
    
    /**             TUPLE             */
    
    public double x, y;
    
    
    public Vec2d set(double x, double y){
        this.x = x;
        this.y = y;
        return this;
    }
    
    public Vec2d set(float x, float y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec2d set(int x, int y){
        this.x = x;
        this.y = y;
        return this;
    }
    
    public Vec2d set(double xy){
        x = xy;
        y = xy;
        return this;
    }
    
    public Vec2d set(float xy){
        x = xy;
        y = xy;
        return this;
    }

    public Vec2d set(int xy){
        x = xy;
        y = xy;
        return this;
    }

    public Vec2d set(Vec3d vector){
        x = vector.x;
        y = vector.y;
        return this;
    }

    public Vec2d set(Vec3f vector){
        x = vector.x;
        y = vector.y;
        return this;
    }

    public Vec2d set(Vec3i vector){
        x = vector.x;
        y = vector.y;
        return this;
    }
    
    public Vec2d set(Vec2d vector){
        x = vector.x;
        y = vector.y;
        return this;
    }
    
    public Vec2d set(Vec2f vector){
        x = vector.x;
        y = vector.y;
        return this;
    }
    
    public Vec2d set(Vec2i vector){
        x = vector.x;
        y = vector.y;
        return this;
    }
    
    
    public Vec2d add(double x, double y){
        this.x += x;
        this.y += y;
        return this;
    }
    
    public Vec2d add(float x, float y){
        this.x += x;
        this.y += y;
        return this;
    }
    
    public Vec2d add(double xyz){
        x += xyz;
        y += xyz;
        return this;
    }
    
    public Vec2d add(float xy){
        x += xy;
        y += xy;
        return this;
    }
    
    public Vec2d add(Vec2d vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec2d add(Vec2f vector){
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vec2d add(Vec2i vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec2d add(Vec3d vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec2d add(Vec3f vector){
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vec2d add(Vec3i vector){
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vec2d add(Vec2d a, Vec2d b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2d add(Vec2f a, Vec2f b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2d add(Vec2i a, Vec2i b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2d add(Vec3d a, Vec3d b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2d add(Vec3f a, Vec3f b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2d add(Vec3i a, Vec3i b){
        return set(a.x + b.x, a.y + b.y);
    }
    
    
    public Vec2d sub(double x, double y){
        this.x -= x;
        this.y -= y;
        return this;
    }
    
    public Vec2d sub(float x, float y){
        this.x -= x;
        this.y -= y;
        return this;
    }
    
    public Vec2d sub(double xy){
        x -= xy;
        y -= xy;
        return this;
    }
    
    public Vec2d sub(float xy){
        x -= xy;
        y -= xy;
        return this;
    }
    
    public Vec2d sub(Vec2d vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    public Vec2d sub(Vec2f vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vec2d sub(Vec2i vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    public Vec2d sub(Vec3d vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    public Vec2d sub(Vec3f vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vec2d sub(Vec3i vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vec2d sub(Vec2d a, Vec2d b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2d sub(Vec2f a, Vec2f b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2d sub(Vec2i a, Vec2i b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2d sub(Vec3d a, Vec3d b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2d sub(Vec3i a, Vec3i b){
        return set(a.x - b.x, a.y - b.y);
    }
    
    
    public Vec2d mul(double x, double y){
        this.x *= x;
        this.y *= y;
        return this;
    }
    
    public Vec2d mul(float x, float y){
        this.x *= x;
        this.y *= y;
        return this;
    }
    
    public Vec2d mul(double xy){
        x *= xy;
        y *= xy;
        return this;
    }
    
    public Vec2d mul(float xy){
        x *= xy;
        y *= xy;
        return this;
    }
    
    public Vec2d mul(Vec2d vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec2d mul(Vec2f vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vec2d mul(Vec2i vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec2d mul(Vec3d vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec2d mul(Vec3f vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vec2d mul(Vec3i vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vec2d mul(Vec2d a, Vec2d b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2d mul(Vec2f a, Vec2f b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2d mul(Vec2i a, Vec2i b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2d mul(Vec3d a, Vec3d b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2d mul(Vec3f a, Vec3f b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2d mul(Vec3i a, Vec3i b){
        return set(a.x * b.x, a.y * b.y);
    }
    
    
    public Vec2d div(double x, double y){
        this.x /= x;
        this.y /= y;
        return this;
    }
    
    public Vec2d div(float x, float y){
        this.x /= x;
        this.y /= y;
        return this;
    }
    
    public Vec2d div(double xy){
        x /= xy;
        y /= xy;
        return this;
    }
    
    public Vec2d div(float xy){
        x /= xy;
        y /= xy;
        return this;
    }
    
    public Vec2d div(Vec2d vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec2d div(Vec2f vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }

    public Vec2d div(Vec2i vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec2d div(Vec3d vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec2d div(Vec3f vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }

    public Vec2d div(Vec3i vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }

    public Vec2d div(Vec2d a, Vec2d b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec2d div(Vec2f a, Vec2f b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec2d div(Vec2i a, Vec2i b){
        return set((double) a.x / b.x, (double) a.y / b.y);
    }

    public Vec2d div(Vec3d a, Vec3d b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec2d div(Vec3f a, Vec3f b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec2d div(Vec3i a, Vec3i b){
        return set((double) a.x / b.x, (double) a.y / b.y);
    }


    public Vec2d mulMat3(float[] matrix){
        return set(
            x * matrix[Matrix3.m00] + y * matrix[Matrix3.m10] + matrix[Matrix3.m20],
            x * matrix[Matrix3.m01] + y * matrix[Matrix3.m11] + matrix[Matrix3.m21]
        );
    }

    public Vec2d mulMat3(Matrix3f matrix){
        return mulMat3(matrix.val);
    }


    public int xRound(){
        return Maths.round(x);
    }

    public int yRound(){
        return Maths.round(y);
    }

    public int xFloor(){
        return Maths.floor(x);
    }

    public int yFloor(){
        return Maths.floor(y);
    }

    public int xCeil(){
        return Maths.ceil(x);
    }

    public int yCeil(){
        return Maths.ceil(y);
    }
    
    
    @Override
    public String toString(){
        return x + ", " + y;
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null || getClass() != object.getClass())
            return false;
        
        final Vec2d vec = (Vec2d) object;
        return x == vec.x && y == vec.y;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }
    
    
}
