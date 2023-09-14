package jpize.math.vecmath.vector;

public class Vec4d{

    public Vec4d(){ }

    public Vec4d(double x, double y, double z, double w){
        set(x, y, z, w);
    }

    public Vec4d(float x, float y, float z, float w){
        set(x, y, z, w);
    }

    public Vec4d(int x, int y, int z, int w){
        set(x, y, z, w);
    }

    public Vec4d(double x, double y, double z){
        set(x, y, z);
    }

    public Vec4d(float x, float y, float z){
        set(x, y, z);
    }

    public Vec4d(int x, int y, int z){
        set(x, y, z);
    }

    public Vec4d(double x, double y){
        set(x, y);
    }

    public Vec4d(float x, float y){
        set(x, y);
    }

    public Vec4d(int x, int y){
        set(x, y);
    }

    public Vec4d(double xyzw){
        set(xyzw);
    }

    public Vec4d(float xyzw){
        set(xyzw);
    }

    public Vec4d(int xyzw){
        set(xyzw);
    }

    public Vec4d(Vec4d vector){
        set(vector);
    }

    public Vec4d(Vec4f vector){
        set(vector);
    }

    public Vec4d(Vec4i vector){
        set(vector);
    }

    public Vec4d(Vec3d vector){
        set(vector);
    }

    public Vec4d(Vec3f vector){
        set(vector);
    }

    public Vec4d(Vec3i vector){
        set(vector);
    }

    public Vec4d(Vec2d vector){
        set(vector);
    }

    public Vec4d(Vec2f vector){
        set(vector);
    }

    public Vec4d(Vec2i vector){
        set(vector);
    }

    /**             TUPLE             */

    public double x, y, z, w;

    public Vec4d set(double x, double y, double z, double w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vec4d set(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vec4d set(int x, int y, int z, int w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        return this;
    }

    public Vec4d set(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 0;
        return this;
    }

    public Vec4d set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 0;
        return this;
    }

    public Vec4d set(int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 0;
        return this;
    }

    public Vec4d set(double x, double y){
        this.x = x;
        this.y = y;
        this.z = 0;
        this.w = 0;
        return this;
    }

    public Vec4d set(float x, float y){
        this.x = x;
        this.y = y;
        this.z = 0;
        this.w = 0;
        return this;
    }

    public Vec4d set(int x, int y){
        this.x = x;
        this.y = y;
        this.z = 0;
        this.w = 0;
        return this;
    }

    public Vec4d set(double xyzw){
        x = xyzw;
        y = xyzw;
        z = xyzw;
        w = xyzw;
        return this;
    }

    public Vec4d set(float xyzw){
        x = xyzw;
        y = xyzw;
        z = xyzw;
        w = xyzw;
        return this;
    }

    public Vec4d set(int xyzw){
        x = xyzw;
        y = xyzw;
        z = xyzw;
        w = xyzw;
        return this;
    }

    public Vec4d set(Vec4d vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        w = vector.w;
        return this;
    }

    public Vec4d set(Vec4f vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        w = vector.w;
        return this;
    }

    public Vec4d set(Vec4i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        w = vector.w;
        return this;
    }

    public Vec4d set(Vec3d vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }

    public Vec4d set(Vec3f vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }

    public Vec4d set(Vec3i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return this;
    }

    public Vec4d set(Vec2d vector){
        x = vector.x;
        y = vector.y;
        z = 0;
        w = 0;
        return this;
    }

    public Vec4d set(Vec2f vector){
        x = vector.x;
        y = vector.y;
        z = 0;
        w = 0;
        return this;
    }

    public Vec4d set(Vec2i vector){
        x = vector.x;
        y = vector.y;
        z = 0;
        w = 0;
        return this;
    }
    
}
