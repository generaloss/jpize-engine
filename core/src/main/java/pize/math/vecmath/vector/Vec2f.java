package pize.math.vecmath.vector;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.matrix.Matrix3f;

import java.util.Objects;

import static pize.math.vecmath.matrix.Matrix3.*;

public class Vec2f{
    
    public Vec2f(){ }
    
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
    
    public Vec2f(Vec2d vector){
        set(vector);
    }
    
    public Vec2f(Vec2f vector){
        set(vector);
    }
    
    public Vec2f(Vec3d vector){
        set(vector);
    }
    
    public Vec2f(Vec3f vector){
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
        
        set(x * cos - y * sin, x * sin + y * cos);
        
        return this;
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
    
    
    public Vec2f add(double x, double y){
        this.x += x;
        this.y += y;
        return this;
    }
    
    public Vec2f add(float x, float y){
        this.x += x;
        this.y += y;
        return this;
    }
    
    public Vec2f add(double xyz){
        x += xyz;
        y += xyz;
        return this;
    }
    
    public Vec2f add(float xy){
        x += xy;
        y += xy;
        return this;
    }
    
    public Vec2f add(Vec2d vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec2f add(Vec2f vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec2f add(Vec3d vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec2f add(Vec3f vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    
    public Vec2f sub(double x, double y){
        this.x -= x;
        this.y -= y;
        return this;
    }
    
    public Vec2f sub(float x, float y){
        this.x -= x;
        this.y -= y;
        return this;
    }
    
    public Vec2f sub(double xy){
        x -= xy;
        y -= xy;
        return this;
    }
    
    public Vec2f sub(float xy){
        x -= xy;
        y -= xy;
        return this;
    }
    
    public Vec2f sub(Vec2d vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    public Vec2f sub(Vec2f vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    public Vec2f sub(Vec3d vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    public Vec2f sub(Vec3f vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    
    public Vec2f mul(double x, double y){
        this.x *= x;
        this.y *= y;
        return this;
    }
    
    public Vec2f mul(float x, float y){
        this.x *= x;
        this.y *= y;
        return this;
    }
    
    public Vec2f mul(double xy){
        x *= xy;
        y *= xy;
        return this;
    }
    
    public Vec2f mul(float xy){
        x *= xy;
        y *= xy;
        return this;
    }
    
    public Vec2f mul(Vec2d vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec2f mul(Vec2f vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec2f mul(Vec3d vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec2f mul(Vec3f vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    
    public Vec2f div(double x, double y){
        this.x /= x;
        this.y /= y;
        return this;
    }
    
    public Vec2f div(float x, float y){
        this.x /= x;
        this.y /= y;
        return this;
    }
    
    public Vec2f div(double xy){
        x /= xy;
        y /= xy;
        return this;
    }
    
    public Vec2f div(float xy){
        x /= xy;
        y /= xy;
        return this;
    }
    
    public Vec2f div(Vec2d vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec2f div(Vec2f vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec2f div(Vec3d vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec2f div(Vec3f vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    
    public Vec2f mul(Matrix3f matrix){
        set(x * matrix.val[m00] + y * matrix.val[m10], x * matrix.val[m01] + y * matrix.val[m11]);
        
        return this;
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
