package pize.math.vecmath.tuple;

import java.util.Objects;

public class Tuple3i{
    
    public int x,y,z;
    

    public <T extends Tuple3i> T set(int x,int y,int z){
        this.x = x;
        this.y = y;
        this.z = z;

        return (T) this;
    }

    public <T extends Tuple3i> T set(int xyz){
        x = xyz;
        y = xyz;
        z = xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T set(Tuple2i vector){
        x = vector.x;
        y = vector.y;
        z = 0;

        return (T) this;
    }

    public <T extends Tuple3i> T set(Tuple3i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;

        return (T) this;
    }


    public <T extends Tuple3i> T add(double x,double y,double z){
        this.x += x;
        this.y += y;
        this.z += z;

        return (T) this;
    }

    public <T extends Tuple3i> T add(float x,float y,float z){
        this.x += x;
        this.y += y;
        this.z += z;

        return (T) this;
    }

    public <T extends Tuple3i> T add(int x,int y,int z){
        this.x += x;
        this.y += y;
        this.z += z;

        return (T) this;
    }

    public <T extends Tuple3i> T add(double xyz){
        x += xyz;
        y += xyz;
        z += xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T add(float xyz){
        x += xyz;
        y += xyz;
        z += xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T add(int xyz){
        x += xyz;
        y += xyz;
        z += xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T add(Tuple2d vector){
        x += vector.x;
        y += vector.y;

        return (T) this;
    }

    public <T extends Tuple3i> T add(Tuple2f vector){
        x += vector.x;
        y += vector.y;

        return (T) this;
    }

    public <T extends Tuple3i> T add(Tuple2i vector){
        x += vector.x;
        y += vector.y;

        return (T) this;
    }

    public <T extends Tuple3i> T add(Tuple3d vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;

        return (T) this;
    }

    public <T extends Tuple3i> T add(Tuple3f vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;

        return (T) this;
    }

    public <T extends Tuple3i> T add(Tuple3i vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;

        return (T) this;
    }


    public <T extends Tuple3i> T sub(double x,double y,double z){
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return (T) this;
    }

    public <T extends Tuple3i> T sub(float x,float y,float z){
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return (T) this;
    }

    public <T extends Tuple3i> T sub(int x,int y,int z){
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return (T) this;
    }

    public <T extends Tuple3i> T sub(double xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T sub(float xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T sub(int xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T sub(Tuple2d vector){
        x -= vector.x;
        y -= vector.y;

        return (T) this;
    }

    public <T extends Tuple3i> T sub(Tuple2f vector){
        x -= vector.x;
        y -= vector.y;

        return (T) this;
    }

    public <T extends Tuple3i> T sub(Tuple3i vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;

        return (T) this;
    }

    public <T extends Tuple3i> T sub(Tuple3f vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;

        return (T) this;
    }


    public <T extends Tuple3i> T mul(double x,double y,double z){
        this.x *= x;
        this.y *= y;
        this.z *= z;

        return (T) this;
    }

    public <T extends Tuple3i> T mul(float x,float y,float z){
        this.x *= x;
        this.y *= y;
        this.z *= z;

        return (T) this;
    }

    public <T extends Tuple3i> T mul(int x,int y,int z){
        this.x *= x;
        this.y *= y;
        this.z *= z;

        return (T) this;
    }

    public <T extends Tuple3i> T mul(double xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T mul(float xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T mul(int xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T mul(Tuple2i vector){
        x *= vector.x;
        y *= vector.y;

        return (T) this;
    }

    public <T extends Tuple3i> T mul(Tuple2f vector){
        x *= vector.x;
        y *= vector.y;

        return (T) this;
    }

    public <T extends Tuple3i> T mul(Tuple3i vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;

        return (T) this;
    }

    public <T extends Tuple3i> T mul(Tuple3f vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;

        return (T) this;
    }


    public <T extends Tuple3i> T div(double x,double y,double z){
        this.x /= x;
        this.y /= y;
        this.z /= z;

        return (T) this;
    }

    public <T extends Tuple3i> T div(float x,float y,float z){
        this.x /= x;
        this.y /= y;
        this.z /= z;

        return (T) this;
    }

    public <T extends Tuple3i> T div(int x,int y,int z){
        this.x /= x;
        this.y /= y;
        this.z /= z;

        return (T) this;
    }

    public <T extends Tuple3i> T div(double xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T div(float xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T div(int xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;

        return (T) this;
    }

    public <T extends Tuple3i> T div(Tuple2i vector){
        x /= vector.x;
        y /= vector.y;

        return (T) this;
    }

    public <T extends Tuple3i> T div(Tuple2f vector){
        x /= vector.x;
        y /= vector.y;

        return (T) this;
    }

    public <T extends Tuple3i> T div(Tuple3i vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;

        return (T) this;
    }

    public <T extends Tuple3i> T div(Tuple3f vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;

        return (T) this;
    }


    @Override
    public String toString(){
        return "xyz: " + x + ", " + y + ", " + z;
    }

    @Override
    public boolean equals(Object object){
        if(object == null || getClass() != object.getClass())
            return false;

        Tuple3i tuple = (Tuple3i) object;
        return x == tuple.x && y == tuple.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y,z);
    }
    
}
