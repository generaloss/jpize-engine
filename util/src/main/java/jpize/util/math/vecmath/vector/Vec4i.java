package jpize.util.math.vecmath.vector;

import java.util.Objects;

public class Vec4i{

    public Vec4i(){ }

    public Vec4i(double x, double y, double z, double w){
        set(x, y, z, w);
    }

    public Vec4i(float x, float y, float z, float w){
        set(x, y, z, w);
    }

    public Vec4i(int x, int y, int z, int w){
        set(x, y, z, w);
    }

    public Vec4i(double x, double y, double z){
        set(x, y, z);
    }

    public Vec4i(float x, float y, float z){
        set(x, y, z);
    }

    public Vec4i(int x, int y, int z){
        set(x, y, z);
    }

    public Vec4i(double x, double y){
        set(x, y);
    }

    public Vec4i(float x, float y){
        set(x, y);
    }

    public Vec4i(int x, int y){
        set(x, y);
    }

    public Vec4i(double xyzw){
        set(xyzw);
    }

    public Vec4i(float xyzw){
        set(xyzw);
    }

    public Vec4i(int xyzw){
        set(xyzw);
    }

    public Vec4i(Vec4d vector){
        set(vector);
    }

    public Vec4i(Vec4f vector){
        set(vector);
    }

    public Vec4i(Vec4i vector){
        set(vector);
    }

    public Vec4i(Vec3d vector){
        set(vector);
    }

    public Vec4i(Vec3f vector){
        set(vector);
    }

    public Vec4i(Vec3i vector){
        set(vector);
    }

    public Vec4i(Vec2d vector){
        set(vector);
    }

    public Vec4i(Vec2f vector){
        set(vector);
    }

    public Vec4i(Vec2i vector){
        set(vector);
    }

    /**             TUPLE             */

    public int x, y, z, w;

    public Vec4i set(double x, double y, double z, double w){
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
        this.w = (int) w;
        return this;
    }

    public Vec4i set(float x, float y, float z, float w){
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
        this.w = (int) w;
        return this;
    }

    public Vec4i set(int x, int y, int z, int w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vec4i set(double x, double y, double z){
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
        return this;
    }

    public Vec4i set(float x, float y, float z){
        this.x = (int) x;
        this.y = (int) y;
        this.z = (int) z;
        return this;
    }

    public Vec4i set(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec4i set(double x, double y){
        this.x = (int) x;
        this.y = (int) y;
        return this;
    }

    public Vec4i set(float x, float y){
        this.x = (int) x;
        this.y = (int) y;
        return this;
    }

    public Vec4i set(int x, int y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec4i set(double xyzw){
        x = (int) xyzw;
        y = (int) xyzw;
        z = (int) xyzw;
        w = (int) xyzw;
        return this;
    }

    public Vec4i set(float xyzw){
        x = (int) xyzw;
        y = (int) xyzw;
        z = (int) xyzw;
        w = (int) xyzw;
        return this;
    }

    public Vec4i set(int xyzw){
        x = xyzw;
        y = xyzw;
        z = xyzw;
        w = xyzw;
        return this;
    }

    public Vec4i set(Vec4d vector){
        x = (int) vector.x;
        y = (int) vector.y;
        z = (int) vector.z;
        w = (int) vector.w;
        return this;
    }

    public Vec4i set(Vec4f vector){
        x = (int) vector.x;
        y = (int) vector.y;
        z = (int) vector.z;
        w = (int) vector.w;
        return this;
    }

    public Vec4i set(Vec4i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        w = vector.w;
        return this;
    }

    public Vec4i set(Vec3d vector){
        x = (int) vector.x;
        y = (int) vector.y;
        z = (int) vector.z;
        return this;
    }

    public Vec4i set(Vec3f vector){
        x = (int) vector.x;
        y = (int) vector.y;
        z = (int) vector.z;
        return this;
    }

    public Vec4i set(Vec3i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }

    public Vec4i set(Vec2d vector){
        x = (int) vector.x;
        y = (int) vector.y;
        return this;
    }

    public Vec4i set(Vec2f vector){
        x = (int) vector.x;
        y = (int) vector.y;
        return this;
    }

    public Vec4i set(Vec2i vector){
        x = vector.x;
        y = vector.y;
        return this;
    }


    @Override
    public String toString(){
        return x + ", " + y + ", " + z + ", " + w;
    }

    @Override
    public boolean equals(Object object){
        if(object == null || getClass() != object.getClass())
            return false;

        final Vec4i tuple = (Vec4i) object;
        return x == tuple.x && y == tuple.y && z == tuple.z && w == tuple.w;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y, z, w);
    }
    
}
