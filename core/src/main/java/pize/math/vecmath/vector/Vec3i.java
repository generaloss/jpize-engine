package pize.math.vecmath.vector;

import pize.math.Mathc;

import java.util.Objects;

public class Vec3i{
    
    
    public Vec3i(){ }
    
    public Vec3i(int x, int y, int z){
        set(x, y, z);
    }
    
    public Vec3i(int xyz){
        set(xyz);
    }
    
    public Vec3i(Vec3i vector){
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
    
    
    
    /**             TUPLE             */
    
    public int x, y, z;
    
    
    public Vec3i set(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
    
    public Vec3i set(int xyz){
        x = xyz;
        y = xyz;
        z = xyz;
        return this;
    }
    
    public Vec3i set(Vec2i vector){
        x = vector.x;
        y = vector.y;
        z = 0;
        return this;
    }
    
    public Vec3i set(Vec3i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }
    
    
    public Vec3i add(double x, double y, double z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Vec3i add(float x, float y, float z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Vec3i add(int x, int y, int z){
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }
    
    public Vec3i add(double xyz){
        x += xyz;
        y += xyz;
        z += xyz;
        return this;
    }
    
    public Vec3i add(float xyz){
        x += xyz;
        y += xyz;
        z += xyz;
        return this;
    }
    
    public Vec3i add(int xyz){
        x += xyz;
        y += xyz;
        z += xyz;
        return this;
    }
    
    public Vec3i add(Vec2d vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec3i add(Vec2f vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec3i add(Vec2i vector){
        x += vector.x;
        y += vector.y;
        return this;
    }
    
    public Vec3i add(Vec3d vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this;
    }
    
    public Vec3i add(Vec3f vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this;
    }
    
    public Vec3i add(Vec3i vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return this;
    }
    
    
    public Vec3i sub(double x, double y, double z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
    public Vec3i sub(float x, float y, float z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
    public Vec3i sub(int x, int y, int z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }
    
    public Vec3i sub(double xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;
        return this;
    }
    
    public Vec3i sub(float xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;
        return this;
    }
    
    public Vec3i sub(int xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;
        return this;
    }
    
    public Vec3i sub(Vec2d vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    public Vec3i sub(Vec2f vector){
        x -= vector.x;
        y -= vector.y;
        return this;
    }
    
    public Vec3i sub(Vec3i vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;
        return this;
    }
    
    public Vec3i sub(Vec3f vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;
        return this;
    }
    
    
    public Vec3i mul(double x, double y, double z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }
    
    public Vec3i mul(float x, float y, float z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }
    
    public Vec3i mul(int x, int y, int z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return this;
    }
    
    public Vec3i mul(double xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;
        return this;
    }
    
    public Vec3i mul(float xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;
        return this;
    }
    
    public Vec3i mul(int xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;
        return this;
    }
    
    public Vec3i mul(Vec2i vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec3i mul(Vec2f vector){
        x *= vector.x;
        y *= vector.y;
        return this;
    }
    
    public Vec3i mul(Vec3i vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;
        return this;
    }
    
    public Vec3i mul(Vec3f vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;
        return this;
    }
    
    
    public Vec3i div(double x, double y, double z){
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }
    
    public Vec3i div(float x, float y, float z){
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }
    
    public Vec3i div(int x, int y, int z){
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return this;
    }
    
    public Vec3i div(double xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;
        return this;
    }
    
    public Vec3i div(float xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;
        return this;
    }
    
    public Vec3i div(int xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;
        return this;
    }
    
    public Vec3i div(Vec2i vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec3i div(Vec2f vector){
        x /= vector.x;
        y /= vector.y;
        return this;
    }
    
    public Vec3i div(Vec3i vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;
        return this;
    }
    
    public Vec3i div(Vec3f vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;
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
