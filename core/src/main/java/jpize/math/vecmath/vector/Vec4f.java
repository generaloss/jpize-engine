package jpize.math.vecmath.vector;

public class Vec4f{

    public Vec4f(){ }

    public Vec4f(double x, double y, double z, double w){
        set(x, y, z, w);
    }

    public Vec4f(float x, float y, float z, float w){
        set(x, y, z, w);
    }

    public Vec4f(int x, int y, int z, int w){
        set(x, y, z, w);
    }

    public Vec4f(double x, double y, double z){
        set(x, y, z);
    }

    public Vec4f(float x, float y, float z){
        set(x, y, z);
    }

    public Vec4f(int x, int y, int z){
        set(x, y, z);
    }

    public Vec4f(double x, double y){
        set(x, y);
    }

    public Vec4f(float x, float y){
        set(x, y);
    }

    public Vec4f(int x, int y){
        set(x, y);
    }

    public Vec4f(double xyzw){
        set(xyzw);
    }

    public Vec4f(float xyzw){
        set(xyzw);
    }

    public Vec4f(int xyzw){
        set(xyzw);
    }

    public Vec4f(Vec4d vector){
        set(vector);
    }

    public Vec4f(Vec4f vector){
        set(vector);
    }

    public Vec4f(Vec4i vector){
        set(vector);
    }

    public Vec4f(Vec3d vector){
        set(vector);
    }

    public Vec4f(Vec3f vector){
        set(vector);
    }

    public Vec4f(Vec3i vector){
        set(vector);
    }

    public Vec4f(Vec2d vector){
        set(vector);
    }

    public Vec4f(Vec2f vector){
        set(vector);
    }

    public Vec4f(Vec2i vector){
        set(vector);
    }
    
    /**             TUPLE             */

    public float x, y, z, w;

    public Vec4f set(double x, double y, double z, double w){
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
        this.w = (float) w;
        return this;
    }

    public Vec4f set(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vec4f set(int x, int y, int z, int w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vec4f set(double x, double y, double z){
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
        return this;
    }

    public Vec4f set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec4f set(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec4f set(double x, double y){
        this.x = (float) x;
        this.y = (float) y;
        return this;
    }

    public Vec4f set(float x, float y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec4f set(int x, int y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Vec4f set(double xyzw){
        x = (float) xyzw;
        y = (float) xyzw;
        z = (float) xyzw;
        w = (float) xyzw;
        return this;
    }

    public Vec4f set(float xyzw){
        x = xyzw;
        y = xyzw;
        z = xyzw;
        w = xyzw;
        return this;
    }

    public Vec4f set(int xyzw){
        x = xyzw;
        y = xyzw;
        z = xyzw;
        w = xyzw;
        return this;
    }

    public Vec4f set(Vec4d vector){
        x = (float) vector.x;
        y = (float) vector.y;
        z = (float) vector.z;
        w = (float) vector.w;
        return this;
    }

    public Vec4f set(Vec4f vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        w = vector.w;
        return this;
    }

    public Vec4f set(Vec4i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        w = vector.w;
        return this;
    }

    public Vec4f set(Vec3d vector){
        x = (float) vector.x;
        y = (float) vector.y;
        z = (float) vector.z;
        return this;
    }

    public Vec4f set(Vec3f vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }

    public Vec4f set(Vec3i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }

    public Vec4f set(Vec2d vector){
        x = (float) vector.x;
        y = (float) vector.y;
        return this;
    }

    public Vec4f set(Vec2f vector){
        x = vector.x;
        y = vector.y;
        return this;
    }

    public Vec4f set(Vec2i vector){
        x = vector.x;
        y = vector.y;
        return this;
    }
    
}
