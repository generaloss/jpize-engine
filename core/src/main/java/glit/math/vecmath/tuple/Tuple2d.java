package glit.math.vecmath.tuple;

import glit.math.Maths;
import glit.math.vecmath.matrix.Matrix3f;

import java.util.Objects;

import static glit.math.vecmath.matrix.Matrix3.*;
import static glit.math.vecmath.matrix.Matrix3.m11;

public class Tuple2d{

    public double x, y;


    public <T extends Tuple2d> T set(double x,double y){
        this.x = x;
        this.y = y;

        return (T) this;
    }

    public <T extends Tuple2d> T set(float x,float y){
        this.x = x;
        this.y = y;

        return (T) this;
    }

    public <T extends Tuple2d> T set(double xy){
        x = xy;
        y = xy;

        return (T) this;
    }

    public <T extends Tuple2d> T set(float xy){
        x = xy;
        y = xy;

        return (T) this;
    }

    public <T extends Tuple2d> T set(Tuple2d vector){
        x = vector.x;
        y = vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T set(Tuple2f vector){
        x = vector.x;
        y = vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T set(Tuple2i vector){
        x = vector.x;
        y = vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T set(Tuple3d vector){
        x = vector.x;
        y = vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T set(Tuple3f vector){
        x = vector.x;
        y = vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T set(Tuple3i vector){
        x = vector.x;
        y = vector.y;

        return (T) this;
    }


    public <T extends Tuple2d> T add(double x,double y){
        this.x += x;
        this.y += y;

        return (T) this;
    }

    public <T extends Tuple2d> T add(float x,float y){
        this.x += x;
        this.y += y;

        return (T) this;
    }

    public <T extends Tuple2d> T add(double xyz){
        x += xyz;
        y += xyz;

        return (T) this;
    }

    public <T extends Tuple2d> T add(float xy){
        x += xy;
        y += xy;

        return (T) this;
    }

    public <T extends Tuple2d> T add(Tuple2d vector){
        x += vector.x;
        y += vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T add(Tuple2f vector){
        x += vector.x;
        y += vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T add(Tuple3d vector){
        x += vector.x;
        y += vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T add(Tuple3f vector){
        x += vector.x;
        y += vector.y;

        return (T) this;
    }


    public <T extends Tuple2d> T sub(double x,double y){
        this.x -= x;
        this.y -= y;

        return (T) this;
    }

    public <T extends Tuple2d> T sub(float x,float y){
        this.x -= x;
        this.y -= y;

        return (T) this;
    }

    public <T extends Tuple2d> T sub(double xy){
        x -= xy;
        y -= xy;

        return (T) this;
    }

    public <T extends Tuple2d> T sub(float xy){
        x -= xy;
        y -= xy;

        return (T) this;
    }

    public <T extends Tuple2d> T sub(Tuple2d vector){
        x -= vector.x;
        y -= vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T sub(Tuple2f vector){
        x -= vector.x;
        y -= vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T sub(Tuple3d vector){
        x -= vector.x;
        y -= vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T sub(Tuple3f vector){
        x -= vector.x;
        y -= vector.y;

        return (T) this;
    }


    public <T extends Tuple2d> T mul(double x,double y){
        this.x *= x;
        this.y *= y;

        return (T) this;
    }

    public <T extends Tuple2d> T mul(float x,float y){
        this.x *= x;
        this.y *= y;

        return (T) this;
    }

    public <T extends Tuple2d> T mul(double xy){
        x *= xy;
        y *= xy;

        return (T) this;
    }

    public <T extends Tuple2d> T mul(float xy){
        x *= xy;
        y *= xy;

        return (T) this;
    }

    public <T extends Tuple2d> T mul(Tuple2d vector){
        x *= vector.x;
        y *= vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T mul(Tuple2f vector){
        x *= vector.x;
        y *= vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T mul(Tuple3d vector){
        x *= vector.x;
        y *= vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T mul(Tuple3f vector){
        x *= vector.x;
        y *= vector.y;

        return (T) this;
    }


    public <T extends Tuple2d> T div(double x,double y){
        this.x /= x;
        this.y /= y;

        return (T) this;
    }

    public <T extends Tuple2d> T div(float x,float y){
        this.x /= x;
        this.y /= y;

        return (T) this;
    }

    public <T extends Tuple2d> T div(double xy){
        x /= xy;
        y /= xy;

        return (T) this;
    }

    public <T extends Tuple2d> T div(float xy){
        x /= xy;
        y /= xy;

        return (T) this;
    }

    public <T extends Tuple2d> T div(Tuple2d vector){
        x /= vector.x;
        y /= vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T div(Tuple2f vector){
        x /= vector.x;
        y /= vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T div(Tuple3d vector){
        x /= vector.x;
        y /= vector.y;

        return (T) this;
    }

    public <T extends Tuple2d> T div(Tuple3f vector){
        x /= vector.x;
        y /= vector.y;

        return (T) this;
    }


    public <T extends Tuple2d> T mul(Matrix3f matrix){
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

        Tuple2d tuple = (Tuple2d) object;
        return x == tuple.x && y == tuple.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }


}
