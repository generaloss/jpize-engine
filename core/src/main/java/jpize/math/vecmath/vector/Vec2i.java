package jpize.math.vecmath.vector;

import jpize.math.Mathc;

import java.util.Objects;

public class Vec2i{
    
    public Vec2i(){ }

    public Vec2i(double x, double y){
        set(x, y);
    }

    public Vec2i(float x, float y){
        set(x, y);
    }

    public Vec2i(int x, int y){
        set(x, y);
    }

    public Vec2i(double xy){
        set(xy);
    }

    public Vec2i(float xy){
        set(xy);
    }

    public Vec2i(int xy){
        set(xy);
    }

    public Vec2i(Vec3d vector){
        set(vector);
    }

    public Vec2i(Vec3f vector){
        set(vector);
    }

    public Vec2i(Vec3i vector){
        set(vector);
    }

    public Vec2i(Vec2d vector){
        set(vector);
    }

    public Vec2i(Vec2f vector){
        set(vector);
    }

    public Vec2i(Vec2i vector){
        set(vector);
    }
    
    
    /**             POINT             */
    
    public float dst(int x, int y){
        double dx = this.x - x;
        double dy = this.y - y;
        
        return Mathc.sqrt(dx * dx + dy * dy);
    }
    
    public float dst(double x, double y){
        double dx = this.x - x;
        double dy = this.y - y;
        
        return Mathc.sqrt(dx * dx + dy * dy);
    }
    
    public float dst(float x, float y){
        double dx = this.x - x;
        double dy = this.y - y;
        
        return Mathc.sqrt(dx * dx + dy * dy);
    }
    
    
    public float dst(Vec2i vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        
        return Mathc.sqrt(dx * dx + dy * dy);
    }
    
    public float dst(Vec2d vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        
        return Mathc.sqrt(dx * dx + dy * dy);
    }
    
    public float dst(Vec2f vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        
        return Mathc.sqrt(dx * dx + dy * dy);
    }


    public Vec2i min(Vec2i vector){
        return new Vec2i(Math.min(x, vector.x), Math.min(y, vector.y));
    }

    public Vec2i max(Vec2i vector){
        return new Vec2i(Math.max(x, vector.x), Math.max(y, vector.y));
    }

    public Vec2i min(Vec2i a, Vec2i b){
        return set(Math.min(a.x, b.x), Math.min(a.y, b.y));
    }

    public Vec2i max(Vec2i a, Vec2i b){
        return set(Math.max(a.x, b.x), Math.max(a.y, b.y));
    }
    
    
    /**             VECTOR             */
    
    public double len(){
        return Math.sqrt(x * x + y * y);
    }
    
    
    public Vec2i abs(){
        if(x < 0)
            x *= -1;
        if(y < 0)
            y *= -1;
        
        return this;
    }


    public Vec2i zero(){
        return set(0, 0);
    }

    public boolean isZero(){
        return x == 0 && y == 0;
    }


    public Vec2i zeroThatLess(double x, double y){
        if(Math.abs(this.x) < Math.abs(x)) this.x = 0;
        if(Math.abs(this.y) < Math.abs(y)) this.y = 0;
        return this;
    }

    public Vec2i zeroThatLess(double xy){
        return zeroThatLess(xy, xy);
    }

    public Vec2i zeroThatLess(Vec2d vector){
        return zeroThatLess(vector.x, vector.y);
    }

    public Vec2i zeroThatLess(Vec2f vector){
        return zeroThatLess(vector.x, vector.y);
    }

    public Vec2i zeroThatLess(Vec2i vector){
        return zeroThatLess(vector.x, vector.y);
    }


    public Vec2i zeroThatZero(double x, double y){
        if(x == 0) this.x = 0;
        if(y == 0) this.y = 0;
        return this;
    }

    public Vec2i zeroThatZero(double xy){
        return zeroThatZero(xy, xy);
    }

    public Vec2i zeroThatZero(Vec2d vector){
        return zeroThatZero(vector.x, vector.y);
    }

    public Vec2i zeroThatZero(Vec2f vector){
        return zeroThatZero(vector.x, vector.y);
    }

    public Vec2i zeroThatZero(Vec2i vector){
        return zeroThatZero(vector.x, vector.y);
    }


    public Vec2i zeroThatBigger(double x, double y){
        if(Math.abs(this.x) > Math.abs(x)) this.x = 0;
        if(Math.abs(this.y) > Math.abs(y)) this.y = 0;
        return this;
    }

    public Vec2i zeroThatBigger(double xy){
        return zeroThatBigger(xy, xy);
    }

    public Vec2i zeroThatBigger(Vec2d vector){
        return zeroThatBigger(vector.x, vector.y);
    }

    public Vec2i zeroThatBigger(Vec2f vector){
        return zeroThatBigger(vector.x, vector.y);
    }

    public Vec2i zeroThatBigger(Vec2i vector){
        return zeroThatBigger(vector.x, vector.y);
    }

    
    public double dot(float x, float y){
        return this.x * x + this.y * y;
    }
    
    public double dot(double x, double y){
        return this.x * x + this.y * y;
    }
    
    public double dot(Vec2i vector){
        return x * vector.x + y * vector.y;
    }
    
    public double dot(Vec2f vector){
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
    
    public float crs(Vec2i vector){
        return this.x * vector.y - this.y * vector.x;
    }
    
    public float crs(Vec2f vector){
        return this.x * vector.y - this.y * vector.x;
    }
    
    public double crs(Vec2d vector){
        return this.x * vector.y - this.y * vector.x;
    }
    
    public Vec2i crs(){
        return new Vec2i(y, -x);
    }


    public int area(){
        return x * y;
    }
    
    
    public Vec2i copy(){
        return new Vec2i(this);
    }
    
    
    public static float crs(Vec2i a, Vec2i b){
        return a.x * b.y - a.y * b.x;
    }
    
    public static float crs(int x1, int y1, int x2, int y2){
        return x1 * y2 - y1 * x2;
    }
    
    public static float dot(Vec2i a, Vec2i b){
        return a.x * b.x + a.y * b.y;
    }
    
    public static float dot(int x1, int y1, int x2, int y2){
        return x1 * x2 + y1 * y2;
    }
    
    public static double len(int x, int y){
        return Math.sqrt(x * x + y * y);
    }
    
    
    /**             TUPLE             */
    
    public int x, y;


    public Vec2i set(double x, double y){
        this.x = (int) x;
        this.y = (int) y;
        return this;
    }

    public Vec2i set(float x, float y){
        this.x = (int) x;
        this.y = (int) y;
        return this;
    }

    public Vec2i set(int x, int y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec2i set(double xy){
        x = (int) xy;
        y = (int) xy;
        return this;
    }

    public Vec2i set(float xy){
        x = (int) xy;
        y = (int) xy;
        return this;
    }

    public Vec2i set(int xy){
        x = xy;
        y = xy;
        return this;
    }

    public Vec2i set(Vec3d vector){
        x = (int) vector.x;
        y = (int) vector.y;
        return this;
    }

    public Vec2i set(Vec3f vector){
        x = (int) vector.x;
        y = (int) vector.y;
        return this;
    }

    public Vec2i set(Vec3i vector){
        x = vector.x;
        y = vector.y;
        return this;
    }

    public Vec2i set(Vec2d vector){
        x = (int) vector.x;
        y = (int) vector.y;
        return this;
    }

    public Vec2i set(Vec2f vector){
        x = (int) vector.x;
        y = (int) vector.y;
        return this;
    }

    public Vec2i set(Vec2i vector){
        x = vector.x;
        y = vector.y;
        return this;
    }
    
    
    public Vec2i add(int x, int y){
        this.x += x;
        this.y += y;
        return this;
    }
    
    public Vec2i add(int xy){
        x += xy;
        y += xy;
        return this;
    }
    
    public Vec2i add(Vec2d vector){
        x += (int) vector.x;
        y += (int) vector.y;
        return this;
    }
    
    public Vec2i add(Vec2f vector){
        x += (int) vector.x;
        y += (int) vector.y;
        return this;
    }
    public Vec2i add(Vec2i vector){
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vec2i add(Vec3d vector){
        x += (int) vector.x;
        y += (int) vector.y;
        return this;
    }
    
    public Vec2i add(Vec3f vector){
        x += (int) vector.x;
        y += (int) vector.y;
        return this;
    }

    public Vec2i add(Vec3i vector){
        x += vector.x;
        y += vector.y;
        return this;
    }

    public Vec2i add(Vec2d a, Vec2d b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2i add(Vec2f a, Vec2f b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2i add(Vec2i a, Vec2i b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2i add(Vec3d a, Vec3d b){
        return set(a.x + b.x, a.y + b.y);
    }

    public Vec2i add(Vec3f a, Vec3f b){
        return set(a.x + b.x, a.y + b.y);
    }
    
    public Vec2i add(Vec3i a, Vec3i b){
        return set(a.x + b.x, a.y + b.y);
    }


    public Vec2i sub(int x, int y){
        this.x -= x;
        this.y -= y;
        return this;
    }
    
    public Vec2i sub(int xy){
        x -= xy;
        y -= xy;
        return this;
    }
    
    public Vec2i sub(Vec2d vector){
        x -= (int) vector.x;
        y -= (int) vector.y;
        return this;
    }
    
    public Vec2i sub(Vec2f vector){
        x -= (int) vector.x;
        y -= (int) vector.y;
        return this;
    }
    
    public Vec2i sub(Vec2i vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vec2i sub(Vec3d vector){
        x -= (int) vector.x;
        y -= (int) vector.y;
        return this;
    }
    
    public Vec2i sub(Vec3f vector){
        x -= (int) vector.x;
        y -= (int) vector.y;
        return this;
    }

    public Vec2i sub(Vec3i vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }

    public Vec2i sub(Vec2d a, Vec2d b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2i sub(Vec2f a, Vec2f b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2i sub(Vec2i a, Vec2i b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2i sub(Vec3d a, Vec3d b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2i sub(Vec3f a, Vec3f b){
        return set(a.x - b.x, a.y - b.y);
    }

    public Vec2i sub(Vec3i a, Vec3i b){
        return set(a.x - b.x, a.y - b.y);
    }


    public Vec2i mul(int x, int y){
        this.x *= x;
        this.y *= y;
        return this;
    }
    
    public Vec2i mul(int xy){
        x *= xy;
        y *= xy;
        return this;
    }
    
    public Vec2i mul(Vec2d vector){
        x *= (int) vector.x;
        y *= (int) vector.y;
        return this;
    }
    
    public Vec2i mul(Vec2f vector){
        x *= (int) vector.x;
        y *= (int) vector.y;
        return this;
    }
    
    public Vec2i mul(Vec2i vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vec2i mul(Vec3d vector){
        x *= (int) vector.x;
        y *= (int) vector.y;
        return this;
    }
    
    public Vec2i mul(Vec3f vector){
        x *= (int) vector.x;
        y *= (int) vector.y;
        return this;
    }

    public Vec2i mul(Vec3i vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }

    public Vec2i mul(Vec2d a, Vec2d b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2i mul(Vec2f a, Vec2f b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2i mul(Vec2i a, Vec2i b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2i mul(Vec3d a, Vec3d b){
        return set(a.x * b.x, a.y * b.y);
    }

    public Vec2i mul(Vec3f a, Vec3f b){
        return set(a.x * b.x, a.y * b.y);
    }
    
    public Vec2i mul(Vec3i a, Vec3i b){
        return set(a.x * b.x, a.y * b.y);
    }


    public Vec2i div(int x, int y){
        this.x /= x;
        this.y /= y;
        return this;
    }
    
    public Vec2i div(int xy){
        x /= xy;
        y /= xy;
        return this;
    }
    
    public Vec2i div(Vec2d vector){
        x /= (int) vector.x;
        y /= (int) vector.y;
        return this;
    }
    
    public Vec2i div(Vec2f vector){
        x /= (int) vector.x;
        y /= (int) vector.y;
        return this;
    }
    
    public Vec2i div(Vec2i vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }

    public Vec2i div(Vec3d vector){
        x /= (int) vector.x;
        y /= (int) vector.y;
        return this;
    }
    
    public Vec2i div(Vec3f vector){
        x /= (int) vector.x;
        y /= (int) vector.y;
        return this;
    }

    public Vec2i div(Vec3i vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }

    public Vec2i div(Vec2d a, Vec2d b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec2i div(Vec2f a, Vec2f b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec2i div(Vec2i a, Vec2i b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec2i div(Vec3d a, Vec3d b){
        return set(a.x / b.x, a.y / b.y);
    }

    public Vec2i div(Vec3f a, Vec3f b){
        return set(a.x / b.x, a.y / b.y);
    }
    
    public Vec2i div(Vec3i a, Vec3i b){
        return set(a.x / b.x, a.y / b.y);
    }


    @Override
    public String toString(){
        return x + ", " + y;
    }
    
    @Override
    public boolean equals(Object object){
        if(object == null || getClass() != object.getClass())
            return false;
        
        final Vec2i tuple = (Vec2i) object;
        return x == tuple.x && y == tuple.y;
    }
    
    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }
    
}
