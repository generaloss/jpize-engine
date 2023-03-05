package glit.math.vecmath.tuple;

import glit.math.Maths;
import glit.math.vecmath.matrix.Matrix4f;

import java.util.Objects;

import static glit.math.vecmath.matrix.Matrix4.*;

public class Tuple3f{

    public float x, y, z;


    public <T extends Tuple3f> T set(double x,double y,double z){
        this.x = (float) x;
        this.y = (float) y;
        this.z = (float) z;
        return (T) this;
    }

    public <T extends Tuple3f> T set(float x,float y,float z){
        this.x = x;
        this.y = y;
        this.z = z;
        return (T) this;
    }

    public <T extends Tuple3f> T set(double xyz){
        x = (float) xyz;
        y = (float) xyz;
        z = (float) xyz;
        return (T) this;
    }

    public <T extends Tuple3f> T set(float xyz){
        x = xyz;
        y = xyz;
        z = xyz;
        return (T) this;
    }

    public <T extends Tuple3f> T set(Tuple2d vector){
        x = (float) vector.x;
        y = (float) vector.y;
        z = 0;
        return (T) this;
    }

    public <T extends Tuple3f> T set(Tuple2f vector){
        x = vector.x;
        y = vector.y;
        z = 0;
        return (T) this;
    }

    public <T extends Tuple3f> T set(Tuple2i vector){
        x = vector.x;
        y = vector.y;
        z = 0;
        return (T) this;
    }

    public <T extends Tuple3f> T set(Tuple3d vector){
        x = (float) vector.x;
        y = (float) vector.y;
        z = (float) vector.z;
        return (T) this;
    }

    public <T extends Tuple3f> T set(Tuple3f vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return (T) this;
    }

    public <T extends Tuple3f> T set(Tuple3i vector){
        x = vector.x;
        y = vector.y;
        z = vector.z;
        return (T) this;
    }


    public <T extends Tuple3f> T add(double x,double y,double z){
        this.x += x;
        this.y += y;
        this.z += z;
        return (T) this;
    }

    public <T extends Tuple3f> T add(float x,float y,float z){
        this.x += x;
        this.y += y;
        this.z += z;
        return (T) this;
    }

    public <T extends Tuple3f> T add(double xyz){
        x += xyz;
        y += xyz;
        z += xyz;
        return (T) this;
    }

    public <T extends Tuple3f> T add(float xyz){
        x += xyz;
        y += xyz;
        z += xyz;
        return (T) this;
    }

    public <T extends Tuple3f> T add(Tuple2d vector){
        x += vector.x;
        y += vector.y;
        return (T) this;
    }

    public <T extends Tuple3f> T add(Tuple2f vector){
        x += vector.x;
        y += vector.y;
        return (T) this;
    }

    public <T extends Tuple3f> T add(Tuple3d vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return (T) this;
    }

    public <T extends Tuple3f> T add(Tuple3f vector){
        x += vector.x;
        y += vector.y;
        z += vector.z;
        return (T) this;
    }


    public <T extends Tuple3f> T sub(double x,double y,double z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return (T) this;
    }

    public <T extends Tuple3f> T sub(float x,float y,float z){
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return (T) this;
    }

    public <T extends Tuple3f> T sub(double xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;
        return (T) this;
    }

    public <T extends Tuple3f> T sub(float xyz){
        x -= xyz;
        y -= xyz;
        z -= xyz;
        return (T) this;
    }

    public <T extends Tuple3f> T sub(Tuple2d vector){
        x -= vector.x;
        y -= vector.y;
        return (T) this;
    }

    public <T extends Tuple3f> T sub(Tuple2f vector){
        x -= vector.x;
        y -= vector.y;
        return (T) this;
    }

    public <T extends Tuple3f> T sub(Tuple3d vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;
        return (T) this;
    }

    public <T extends Tuple3f> T sub(Tuple3f vector){
        x -= vector.x;
        y -= vector.y;
        z -= vector.z;
        return (T) this;
    }


    public <T extends Tuple3f> T mul(double x,double y,double z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return (T) this;
    }

    public <T extends Tuple3f> T mul(float x,float y,float z){
        this.x *= x;
        this.y *= y;
        this.z *= z;
        return (T) this;
    }

    public <T extends Tuple3f> T mul(double xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;
        return (T) this;
    }

    public <T extends Tuple3f> T mul(float xyz){
        x *= xyz;
        y *= xyz;
        z *= xyz;
        return (T) this;
    }

    public <T extends Tuple3f> T mul(Tuple2d vector){
        x *= vector.x;
        y *= vector.y;
        return (T) this;
    }

    public <T extends Tuple3f> T mul(Tuple2f vector){
        x *= vector.x;
        y *= vector.y;
        return (T) this;
    }

    public <T extends Tuple3f> T mul(Tuple3d vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;
        return (T) this;
    }

    public <T extends Tuple3f> T mul(Tuple3f vector){
        x *= vector.x;
        y *= vector.y;
        z *= vector.z;
        return (T) this;
    }


    public <T extends Tuple3f> T div(double x,double y,double z){
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return (T) this;
    }

    public <T extends Tuple3f> T div(float x,float y,float z){
        this.x /= x;
        this.y /= y;
        this.z /= z;
        return (T) this;
    }

    public <T extends Tuple3f> T div(double xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;
        return (T) this;
    }

    public <T extends Tuple3f> T div(float xyz){
        x /= xyz;
        y /= xyz;
        z /= xyz;
        return (T) this;
    }

    public <T extends Tuple3f> T div(Tuple2d vector){
        x /= vector.x;
        y /= vector.y;
        return (T) this;
    }

    public <T extends Tuple3f> T div(Tuple2f vector){
        x /= vector.x;
        y /= vector.y;
        return (T) this;
    }

    public <T extends Tuple3f> T div(Tuple3d vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;
        return (T) this;
    }

    public <T extends Tuple3f> T div(Tuple3f vector){
        x /= vector.x;
        y /= vector.y;
        z /= vector.z;
        return (T) this;
    }


    public <T extends Tuple3f> T mul(Matrix4f matrix){
        set(x * matrix.val[m00] + y * matrix.val[m01] + z * matrix.val[m02],
            x * matrix.val[m10] + y * matrix.val[m11] + z * matrix.val[m12],
            x * matrix.val[m10] + y * matrix.val[m11] + z * matrix.val[m12]);

        return (T) this;
    }

    public <T extends Tuple3f> T mul(float[] matrix){
        set(x * matrix[m00] + y * matrix[m01] + z * matrix[m02],
            x * matrix[m10] + y * matrix[m11] + z * matrix[m12],
            x * matrix[m10] + y * matrix[m11] + z * matrix[m12]);

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

        Tuple3f tuple = (Tuple3f) object;
        return x == tuple.x && y == tuple.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y,z);
    }


}
