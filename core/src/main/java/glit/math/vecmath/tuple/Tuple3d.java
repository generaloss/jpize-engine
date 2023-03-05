package glit.math.vecmath.tuple;

import glit.math.Maths;
import glit.math.vecmath.matrix.Matrix4f;

import java.util.Objects;

import static glit.math.vecmath.matrix.Matrix3.*;

public class Tuple3d{

    public double x, y, z;


    public <T extends Tuple3d> T set(double x,double y,double z){
        this.x = x;
        this.y = y;
        this.z = z;

        return (T) this;
    }

    public <T extends Tuple3d> T set(float x,float y,float z){
        this.x = x;
        this.y = y;
        this.z = z;

        return (T) this;
    }

    public <T extends Tuple3d> T set(double xyz){
        x = xyz;
        y = xyz;
        z = xyz;

        return (T) this;
    }

    public <T extends Tuple3d> T set(float xyz){
        x = xyz;
        y = xyz;
        z = xyz;

        return (T) this;
    }

    public <T extends Tuple3d> T set(Tuple2d vector){
        x = vector.x;
        y = vector.y;
        z = 0;

        return (T) this;
    }

    public <T extends Tuple3d> T set(Tuple2f vector){
        x = vector.x;
        y = vector.y;
        z = 0;

        return (T) this;
    }

    public <T extends Tuple3d> T set(Tuple2i vector){
        x = vector.x;
        y = vector.y;
        z = 0;

        return (T) this;
    }

    public <T extends Tuple3d> T set(Tuple3d vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;

        return (T) this;
    }

    public <T extends Tuple3d> T set(Tuple3f vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;

        return (T) this;
    }

    public <T extends Tuple3d> T set(Tuple3i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;

        return (T) this;
    }


    public <T extends Tuple3d> T add(double x,double y,double z){
        this.x += x;
        this.y += y;
        this.z += z;

        return (T) this;
    }

    public <T extends Tuple3d> T add(float x,float y,float z){
        this.x += x;
        this.y += y;
        this.z += z;

        return (T) this;
    }

    public <T extends Tuple3d> T add(double xyz){
        x += xyz;
        y += xyz;
        z += xyz;

        return (T) this;
    }

    public <T extends Tuple3d> T add(float xyz){
        x += xyz;
        y += xyz;
        z += xyz;

        return (T) this;
    }

    public <T extends Tuple3d> T add(Tuple2d vector){
        x += vector.x;
        y += vector.y;

        return (T) this;
    }

    public <T extends Tuple3d> T add(Tuple2f vector){
        x += vector.x;
        y += vector.y;

        return (T) this;
    }

    public <T extends Tuple3d> T add(Tuple3d vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;

        return (T) this;
    }

    public <T extends Tuple3d> T add(Tuple3f vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;

        return (T) this;
    }


    public <T extends Tuple3d> T sub(double x,double y,double z){
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return (T) this;
    }

    public <T extends Tuple3d> T sub(float x,float y,float z){
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return (T) this;
    }

    public <T extends Tuple3d> T sub(double xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;

        return (T) this;
    }

    public <T extends Tuple3d> T sub(float xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;

        return (T) this;
    }

    public <T extends Tuple3d> T sub(Tuple2d vector){
        x -= vector.x;
        y -= vector.y;

        return (T) this;
    }

    public <T extends Tuple3d> T sub(Tuple2f vector){
        x -= vector.x;
        y -= vector.y;

        return (T) this;
    }

    public <T extends Tuple3d> T sub(Tuple3d vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;

        return (T) this;
    }

    public <T extends Tuple3d> T sub(Tuple3f vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;

        return (T) this;
    }


    public <T extends Tuple3d> T mul(double x,double y,double z){
        this.x *= x;
        this.y *= y;
        this.z *= z;

        return (T) this;
    }

    public <T extends Tuple3d> T mul(float x,float y,float z){
        this.x *= x;
        this.y *= y;
        this.z *= z;

        return (T) this;
    }

    public <T extends Tuple3d> T mul(double xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;

        return (T) this;
    }

    public <T extends Tuple3d> T mul(float xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;

        return (T) this;
    }

    public <T extends Tuple3d> T mul(Tuple2d vector){
        x *= vector.x;
        y *= vector.y;

        return (T) this;
    }

    public <T extends Tuple3d> T mul(Tuple2f vector){
        x *= vector.x;
        y *= vector.y;

        return (T) this;
    }

    public <T extends Tuple3d> T mul(Tuple3d vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;

        return (T) this;
    }

    public <T extends Tuple3d> T mul(Tuple3f vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;

        return (T) this;
    }


    public <T extends Tuple3d> T div(double x,double y,double z){
        this.x /= x;
        this.y /= y;
        this.z /= z;

        return (T) this;
    }

    public <T extends Tuple3d> T div(float x,float y,float z){
        this.x /= x;
        this.y /= y;
        this.z /= z;

        return (T) this;
    }

    public <T extends Tuple3d> T div(double xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;

        return (T) this;
    }

    public <T extends Tuple3d> T div(float xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;

        return (T) this;
    }

    public <T extends Tuple3d> T div(Tuple2d vector){
        x /= vector.x;
        y /= vector.y;

        return (T) this;
    }

    public <T extends Tuple3d> T div(Tuple2f vector){
        x /= vector.x;
        y /= vector.y;

        return (T) this;
    }

    public <T extends Tuple3d> T div(Tuple3d vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;

        return (T) this;
    }

    public <T extends Tuple3d> T div(Tuple3f vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;

        return (T) this;
    }


    public <T extends Tuple3d> T mul(Matrix4f matrix){
        set(x * matrix.val[m00] + y * matrix.val[m01] + z * matrix.val[m02],
            x * matrix.val[m10] + y * matrix.val[m11] + z * matrix.val[m12],
            x * matrix.val[m10] + y * matrix.val[m11] + z * matrix.val[m12]);

        return (T) this;
    }


    public int x(){
        return Maths.round(x);
    }

    public int y(){
        return Maths.round(y);
    }

    public int z(){
        return Maths.round(z);
    }

    public int xf(){
        return Maths.floor(x);
    }

    public int yf(){
        return Maths.floor(y);
    }

    public int zf(){
        return Maths.floor(z);
    }

    public int xc(){
        return Maths.ceil(x);
    }

    public int yc(){
        return Maths.ceil(y);
    }

    public int zc(){
        return Maths.ceil(z);
    }


    @Override
    public String toString(){
        return "xyz: " + x + ", " + y + ", " + z;
    }

    @Override
    public boolean equals(Object object){
        if(object == null || getClass() != object.getClass())
            return false;

        Tuple3d tuple = (Tuple3d) object;
        return x == tuple.x && y == tuple.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y,z);
    }


}
