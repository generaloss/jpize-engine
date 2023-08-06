package pize.math.vecmath.vector;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.matrix.Matrix3f;

import java.util.Objects;

import static pize.math.vecmath.matrix.Matrix3.*;

public class Vec2d{
    
    public Vec2d(){ }
    
    public Vec2d(double x, double y){
        set(x, y);
    }
    
    public Vec2d(float x, float y){
        set(x, y);
    }
    
    public Vec2d(double xy){
        set(xy);
    }
    
    public Vec2d(float xy){
        set(xy);
    }
    
    public Vec2d(Vec2d vector){
        set(vector);
    }
    
    public Vec2d(Vec2f vector){
        set(vector);
    }
    
    public Vec2d(Vec3d vector){
        set(vector);
    }
    
    public Vec2d(Vec3f vector){
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
        set(0, 0);
        
        return this;
    }
    
    public boolean isZero(){
        return x == 0 && y == 0;
    }
    
    
    public double dot(float x, float y){
        return this.x * x + this.y * y;
    }
    
    public double dot(double x, double y){
        return this.x * x + this.y * y;
    }
    
    public double dot(Vec2f vector){
        return x * vector.x + y * vector.y;
    }
    
    public double dot(Vec2d vector){
        return x * vector.x + y * vector.y;
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
        return rad(vector) * Maths.ToDeg;
    }
    
    public double rad(Vec2d vector){
        double cos = dot(vector) / (len() * vector.len());
        
        return Math.acos(Maths.clamp(cos, -1, 1));
    }
    
    public Vec2d rotDeg(double deg){
        return rotRad(deg * Maths.ToRad);
    }
    
    public Vec2d rotRad(double rad){
        float cos = Mathc.cos(rad);
        float sin = Mathc.sin(rad);
        
        set(x * cos - y * sin, x * sin + y * cos);
        
        return this;
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
    
    
    public Vec2d mul(Matrix3f matrix){
        set(
            x * matrix.val[m00] + y * matrix.val[m10],
            x * matrix.val[m01] + y * matrix.val[m11]
        );
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
        
        final Vec2d tuple = (Vec2d) object;
        return x == tuple.x && y == tuple.y;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }
    
    
}
