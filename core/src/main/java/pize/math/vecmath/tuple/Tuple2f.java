package pize.math.vecmath.tuple;

import pize.math.Maths;
import pize.math.vecmath.matrix.Matrix3f;

import java.util.Objects;

import static pize.math.vecmath.matrix.Matrix3.*;

public class Tuple2f{

    public float x, y;


    public <T extends Tuple2f> T set(double x,double y){
        this.x = (float) x;
        this.y = (float) y;
        return (T) this;
    }

    public <T extends Tuple2f> T set(float x,float y){
        this.x = x;
        this.y = y;
        return (T) this;
    }

    public <T extends Tuple2f> T set(double xy){
        x = (float) xy;
        y = (float) xy;
        return (T) this;
    }

    public <T extends Tuple2f> T set(float xy){
        x = xy;
        y = xy;
        return (T) this;
    }

    public <T extends Tuple2f> T set(Tuple2d vector){
        x = (float) vector.x;
        y = (float) vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T set(Tuple2f vector){
        x = vector.x;
        y = vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T set(Tuple2i vector){
        x = vector.x;
        y = vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T set(Tuple3d vector){
        x = (float) vector.x;
        y = (float) vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T set(Tuple3f vector){
        x = vector.x;
        y = vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T set(Tuple3i vector){
        x = vector.x;
        y = vector.y;
        return (T) this;
    }


    public <T extends Tuple2f> T add(double x,double y){
        this.x += x;
        this.y += y;
        return (T) this;
    }

    public <T extends Tuple2f> T add(float x,float y){
        this.x += x;
        this.y += y;
        return (T) this;
    }

    public <T extends Tuple2f> T add(double xyz){
        x += xyz;
        y += xyz;
        return (T) this;
    }

    public <T extends Tuple2f> T add(float xy){
        x += xy;
        y += xy;
        return (T) this;
    }

    public <T extends Tuple2f> T add(Tuple2d vector){
        x += vector.x;
        y += vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T add(Tuple2f vector){
        x += vector.x;
        y += vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T add(Tuple3d vector){
        x += vector.x;
        y += vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T add(Tuple3f vector){
        x += vector.x;
        y += vector.y;
        return (T) this;
    }


    public <T extends Tuple2f> T sub(double x,double y){
        this.x -= x;
        this.y -= y;
        return (T) this;
    }

    public <T extends Tuple2f> T sub(float x,float y){
        this.x -= x;
        this.y -= y;
        return (T) this;
    }

    public <T extends Tuple2f> T sub(double xy){
        x -= xy;
        y -= xy;
        return (T) this;
    }

    public <T extends Tuple2f> T sub(float xy){
        x -= xy;
        y -= xy;
        return (T) this;
    }

    public <T extends Tuple2f> T sub(Tuple2d vector){
        x -= vector.x;
        y -= vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T sub(Tuple2f vector){
        x -= vector.x;
        y -= vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T sub(Tuple3d vector){
        x -= vector.x;
        y -= vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T sub(Tuple3f vector){
        x -= vector.x;
        y -= vector.y;
        return (T) this;
    }


    public <T extends Tuple2f> T mul(double x,double y){
        this.x *= x;
        this.y *= y;
        return (T) this;
    }

    public <T extends Tuple2f> T mul(float x,float y){
        this.x *= x;
        this.y *= y;
        return (T) this;
    }

    public <T extends Tuple2f> T mul(double xy){
        x *= xy;
        y *= xy;
        return (T) this;
    }

    public <T extends Tuple2f> T mul(float xy){
        x *= xy;
        y *= xy;
        return (T) this;
    }

    public <T extends Tuple2f> T mul(Tuple2d vector){
        x *= vector.x;
        y *= vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T mul(Tuple2f vector){
        x *= vector.x;
        y *= vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T mul(Tuple3d vector){
        x *= vector.x;
        y *= vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T mul(Tuple3f vector){
        x *= vector.x;
        y *= vector.y;
        return (T) this;
    }


    public <T extends Tuple2f> T div(double x,double y){
        this.x /= x;
        this.y /= y;
        return (T) this;
    }

    public <T extends Tuple2f> T div(float x,float y){
        this.x /= x;
        this.y /= y;
        return (T) this;
    }

    public <T extends Tuple2f> T div(double xy){
        x /= xy;
        y /= xy;
        return (T) this;
    }

    public <T extends Tuple2f> T div(float xy){
        x /= xy;
        y /= xy;
        return (T) this;
    }

    public <T extends Tuple2f> T div(Tuple2d vector){
        x /= vector.x;
        y /= vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T div(Tuple2f vector){
        x /= vector.x;
        y /= vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T div(Tuple3d vector){
        x /= vector.x;
        y /= vector.y;
        return (T) this;
    }

    public <T extends Tuple2f> T div(Tuple3f vector){
        x /= vector.x;
        y /= vector.y;
        return (T) this;
    }


    public <T extends Tuple2f> T mul(Matrix3f matrix){
        set(x * matrix.val[m00] + y * matrix.val[m01],
            x * matrix.val[m10] + y * matrix.val[m11]);

        return (T) this;
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
        return "xy: " + x + ", " + y;
    }

    @Override
    public boolean equals(Object object){
        if(object == null || getClass() != object.getClass())
            return false;

        Tuple2f tuple = (Tuple2f) object;
        return x == tuple.x && y == tuple.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }


}