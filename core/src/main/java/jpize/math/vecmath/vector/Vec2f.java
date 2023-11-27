package jpize.math.vecmath.vector;

import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.math.vecmath.matrix.Matrix3;
import jpize.math.vecmath.matrix.Matrix3f;

import java.util.Objects;

public class Vec2f{
    
    public Vec2f(){ }
    
    public Vec2f(double x, double y){
        set(x, y);
    }
    
    public Vec2f(float x, float y){
        set(x, y);
    }

    public Vec2f(int x, int y){
        set(x, y);
    }
    
    public Vec2f(double xy){
        set(xy);
    }
    
    public Vec2f(float xy){
        set(xy);
    }

    public Vec2f(int xy){
        set(xy);
    }

    public Vec2f(Vec3d vector){
        set(vector);
    }

    public Vec2f(Vec3f vector){
        set(vector);
    }

    public Vec2f(Vec3i vector){
        set(vector);
    }

    public Vec2f(Vec2d vector){
        set(vector);
    }
    
    public Vec2f(Vec2f vector){
        set(vector);
    }

    public Vec2f(Vec2i vector){
        set(vector);
    }

    
    /**             POINT             */
    
    public float dst(int x, int y){
        final double dx = this.x - x;
        final double dy = this.y - y;
        
        return Mathc.sqrt(dx * dx + dy * dy);
    }
    
    public float dst(double x, double y){
        final double dx = this.x - x;
        final double dy = this.y - y;
        
        return Mathc.sqrt(dx * dx + dy * dy);
    }
    
    public float dst(float x, float y){
        final double dx = this.x - x;
        final double dy = this.y - y;
        
        return Mathc.sqrt(dx * dx + dy * dy);
    }
    
    
    public float dst(Vec2i vector){
        final double dx = x - vector.x;
        final double dy = y - vector.y;
        
        return Mathc.sqrt(dx * dx + dy * dy);
    }
    
    public float dst(Vec2d vector){
        final double dx = x - vector.x;
        final double dy = y - vector.y;
        
        return Mathc.sqrt(dx * dx + dy * dy);
    }
    
    public float dst(Vec2f vector){
        final double dx = x - vector.x;
        final double dy = y - vector.y;
        
        return Mathc.sqrt(dx * dx + dy * dy);
    }


    public static float dst(float x1, float y1, float x2, float y2){
        final double dx = x1 - x2;
        final double dy = y1 - y2;
        return Mathc.sqrt(dx * dx + dy * dy);
    }


    public Vec2f min(Vec2f vector){
        return new Vec2f(Math.min(x, vector.x), Math.min(y, vector.y));
    }

    public Vec2f max(Vec2f vector){
        return new Vec2f(Math.max(x, vector.x), Math.max(y, vector.y));
    }

    public Vec2f min(Vec2f a, Vec2f b){
        return set(Math.min(a.x, b.x), Math.min(a.y, b.y));
    }

    public Vec2f max(Vec2f a, Vec2f b){
        return set(Math.max(a.x, b.x), Math.max(a.y, b.y));
    }
    
    
    /**             VECTOR             */
    
    public float len2(){
        return x * x + y * y;
    }
    
    public float len(){
        return Mathc.sqrt(len2());
    }
    
    public Vec2f nor(){
        double len = len2();
        if(len == 0 || len == 1)
            return this;
        
        len = Maths.invSqrt(len);
        return mul(len);
    }
    
    
    public Vec2f abs(){
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
    
    
    public Vec2f frac(){
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
    
    
    public float deg(Vec2f vector){
        return rad(vector) * Maths.ToDeg;
    }
    
    public float rad(Vec2f vector){
        double cos = dot(vector) / (len() * vector.len());
        
        return Mathc.acos(Maths.clamp(cos, -1, 1));
    }
    
    public Vec2f rotDeg(double degrees){
        return rotRad(degrees * Maths.ToRad);
    }

    public Vec2f rotRad(double radians){
        float cos = Mathc.cos(radians);
        float sin = Mathc.sin(radians);

        set(
            x * cos - y * sin,
            x * sin + y * cos
        );

        return this;
    }

    public Vec2f rotDeg(double degrees, Vec2f origin){
        return rotRad(degrees * Maths.ToRad, origin);
    }

    public Vec2f rotDeg(double degrees, float originX, float originY){
        return rotRad(degrees * Maths.ToRad, originX, originY);
    }

    public Vec2f rotRad(double radians, Vec2f origin){
        return rotRad(radians, origin.x, origin.y);
    }

    public Vec2f rotRad(double radians, float originX, float originY){
        float cos = Mathc.cos(radians);
        float sin = Mathc.sin(radians);

        sub(originX, originY);
        set(
            x * cos - y * sin + originX,
            x * sin + y * cos + originY
        );

        return this;
    }


    public float area(){
        return x * y;
    }
    
    
    public Vec2f copy(){
        return new Vec2f(this);
    }
    
    
    public static float crs(Vec2f a, Vec2f b){
        return a.x * b.y - a.y * b.x;
    }
    
    public static float crs(float x1, float y1, float x2, float y2){
        return x1 * y2 - y1 * x2;
    }
    
    public static float dot(Vec2f a, Vec2f b){
        return a.x * b.x + a.y * b.y;
    }
    
    public static float dot(float x1, float y1, float x2, float y2){
        return x1 * x2 + y1 * y2;
    }
    
    public static float len(double x, double y){
        return Mathc.sqrt(x * x + y * y);
    }
    
    
    /**             TUPLE             */
    
    public float x, y;
    
    
    public Vec2f set(double x, double y){
        this.x = (float) x;
        this.y = (float) y;
        return this;
    }
    
    public Vec2f set(float x, float y){
        this.x = x;
        this.y = y;
        return this;
    }
    
    public Vec2f set(double xy){
        x = (float) xy;
        y = (float) xy;
        return this;
    }
    
    public Vec2f set(float xy){
        x = xy;
        y = xy;
        return this;
    }

    public Vec2f set(Vec3d vector){
        x = (float) vector.x;
        y = (float) vector.y;
        return this;
    }

    public Vec2f set(Vec3f vector){
        x = vector.x;
        y = vector.y;
        return this;
    }

    public Vec2f set(Vec3i vector){
        x = vector.x;
        y = vector.y;
        return this;
    }
    
    public Vec2f set(Vec2d vector){
        x = (float) vector.x;
        y = (float) vector.y;
        return this;
    }
    
    public Vec2f set(Vec2f vector){
        x = vector.x;
        y = vector.y;
        return this;
    }
    
    public Vec2f set(Vec2i vector){
        x = vector.x;
        y = vector.y;
        return this;
    }
    
    
    public Vec2f add(double x, double y){
        this.x += (float) x;
        this.y += (float) y;
        return this;
    }
    
    public Vec2f add(float x, float y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Vec2f add(int x, int y){
        this.x += x;
        this.y += y;
        return this;
    }
    
    public Vec2f add(double xyz){
        x += (float) xyz;
        y += (float) xyz;
        return this;
    }
    
    public Vec2f add(float xy){
        x += xy;
        y += xy;
        return this;
    }

    public Vec2f add(int xy){
        x += xy;
        y += xy;
        return this;
    }

    public Vec2f add(Vec3d vector){
        x += (float) vector.x;
        y += (float) vector.y;
        return this;
    }

    public Vec2f add(Vec3f vector){
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vec2f add(Vec3i vector){
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vec2f add(Vec2d vector){
        x += (float) vector.x;
        y += (float) vector.y;
        return this;
    }
    
    public Vec2f add(Vec2f vector){
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vec2f add(Vec2i vector){
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vec2f add(Vec2d a, Vec2d b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2f add(Vec2f a, Vec2f b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2f add(Vec2i a, Vec2i b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2f add(Vec3d a, Vec3d b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2f add(Vec3f a, Vec3f b){
        return set(a.x + b.x, a.y + b.y);
    }
    
    public Vec2f add(Vec3i a, Vec3i b){
        return set(a.x + b.x, a.y + b.y);
    }


    public Vec2f sub(double x, double y){
        this.x -= (float) x;
        this.y -= (float) y;
        return this;
    }
    
    public Vec2f sub(float x, float y){
        this.x -= x;
        this.y -= y;
        return this;
    }
    
    public Vec2f sub(double xy){
        x -= (float) xy;
        y -= (float) xy;
        return this;
    }
    
    public Vec2f sub(float xy){
        x -= xy;
        y -= xy;
        return this;
    }
    
    public Vec2f sub(Vec2d vector){
        x -= (float) vector.x;
        y -= (float) vector.y;
        return this;
    }
    
    public Vec2f sub(Vec2f vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    public Vec2f sub(Vec2i vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vec2f sub(Vec3d vector){
        x -= (float) vector.x;
        y -= (float) vector.y;
        return this;
    }
    
    public Vec2f sub(Vec3f vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vec2f sub(Vec3i vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vec2f sub(Vec2d a, Vec2d b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2f sub(Vec2f a, Vec2f b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2f sub(Vec2i a, Vec2i b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2f sub(Vec3d a, Vec3d b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2f sub(Vec3f a, Vec3f b){
        return set(a.x - b.x, a.y - b.y);
    }
    
    public Vec2f sub(Vec3i a, Vec3i b){
        return set(a.x - b.x, a.y - b.y);
    }


    public Vec2f mul(double x, double y){
        this.x *= (float) x;
        this.y *= (float) y;
        return this;
    }
    
    public Vec2f mul(float x, float y){
        this.x *= x;
        this.y *= y;
        return this;
    }
    
    public Vec2f mul(double xy){
        x *= (float) xy;
        y *= (float) xy;
        return this;
    }
    
    public Vec2f mul(float xy){
        x *= xy;
        y *= xy;
        return this;
    }
    
    public Vec2f mul(Vec2d vector){
        x *= (float) vector.x;
        y *= (float) vector.y;
        return this;
    }
    
    public Vec2f mul(Vec2f vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec2f mul(Vec2i vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vec2f mul(Vec3d vector){
        x *= (float) vector.x;
        y *= (float) vector.y;
        return this;
    }
    
    public Vec2f mul(Vec3f vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vec2f mul(Vec3i vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vec2f mul(Vec2d a, Vec2d b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2f mul(Vec2f a, Vec2f b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2f mul(Vec2i a, Vec2i b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2f mul(Vec3d a, Vec3d b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2f mul(Vec3f a, Vec3f b){
        return set(a.x * b.x, a.y * b.y);
    }
    
    public Vec2f mul(Vec3i a, Vec3i b){
        return set(a.x * b.x, a.y * b.y);
    }


    public Vec2f div(double x, double y){
        this.x /= (float) x;
        this.y /= (float) y;
        return this;
    }
    
    public Vec2f div(float x, float y){
        this.x /= x;
        this.y /= y;
        return this;
    }
    
    public Vec2f div(double xy){
        x /= (float) xy;
        y /= (float) xy;
        return this;
    }
    
    public Vec2f div(float xy){
        x /= xy;
        y /= xy;
        return this;
    }
    
    public Vec2f div(Vec2d vector){
        x /= (float) vector.x;
        y /= (float) vector.y;
        return this;
    }
    
    public Vec2f div(Vec2f vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec2f div(Vec2i vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }

    public Vec2f div(Vec3d vector){
        x /= (float) vector.x;
        y /= (float) vector.y;
        return this;
    }
    
    public Vec2f div(Vec3f vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }

    public Vec2f div(Vec3i vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }

    public Vec2f div(Vec2d a, Vec2d b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec2f div(Vec2f a, Vec2f b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec2f div(Vec2i a, Vec2i b){
        return set((float) a.x / b.x, (float) a.y / b.y);
    }

    public Vec2f div(Vec3d a, Vec3d b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec2f div(Vec3f a, Vec3f b){
        return set(a.x / b.x, a.y / b.y);
    }
    
    public Vec2f div(Vec3i a, Vec3i b){
        return set((float) a.x / b.x, (float) a.y / b.y);
    }


    public Vec2f mulMat3(float[] matrix){
        return set(
            x * matrix[Matrix3.m00] + y * matrix[Matrix3.m10] + matrix[Matrix3.m20],
            x * matrix[Matrix3.m01] + y * matrix[Matrix3.m11] + matrix[Matrix3.m21]
        );
    }

    public Vec2f mulMat3(Matrix3f matrix){
        return mulMat3(matrix.val);
    }

    
    public int x(){
        return Maths.round(x);
    }
    
    public int y(){
        return Maths.round(y);
    }
    
    public int xf(){
        return Maths.floor(x);
    }
    
    public int yf(){
        return Maths.floor(y);
    }
    
    public int xc(){
        return Maths.ceil(x);
    }
    
    public int yc(){
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
        
        final Vec2f tuple = (Vec2f) object;
        return x == tuple.x && y == tuple.y;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }
    
    
}
