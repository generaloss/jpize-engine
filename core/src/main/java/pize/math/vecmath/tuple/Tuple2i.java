package pize.math.vecmath.tuple;

import java.util.Objects;

public class Tuple2i{

    public int x,y;

    public <T extends Tuple2i> T set(int x,int y){
        this.x = x;
        this.y = y;
        return (T) this;
    }

    public <T extends Tuple2i> T set(int xy){
        x = xy;
        y = xy;
        return (T) this;
    }

    public <T extends Tuple2i> T set(Tuple2i vector){
        x = vector.x;
        y = vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T set(Tuple3i vector){
        x = vector.x;
        y = vector.y;
        return (T) this;
    }


    public <T extends Tuple2i> T add(int x,int y){
        this.x += x;
        this.y += y;
        return (T) this;
    }

    public <T extends Tuple2i> T add(int xy){
        x += xy;
        y += xy;
        return (T) this;
    }

    public <T extends Tuple2i> T add(Tuple2d vector){
        x += vector.x;
        y += vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T add(Tuple2f vector){
        x += vector.x;
        y += vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T add(Tuple3d vector){
        x += vector.x;
        y += vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T add(Tuple3f vector){
        x += vector.x;
        y += vector.y;
        return (T) this;
    }


    public <T extends Tuple2i> T sub(int x,int y){
        this.x -= x;
        this.y -= y;
        return (T) this;
    }

    public <T extends Tuple2i> T sub(int xy){
        x -= xy;
        y -= xy;
        return (T) this;
    }

    public <T extends Tuple2i> T sub(Tuple2d vector){
        x -= vector.x;
        y -= vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T sub(Tuple2f vector){
        x -= vector.x;
        y -= vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T sub(Tuple3d vector){
        x -= vector.x;
        y -= vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T sub(Tuple3f vector){
        x -= vector.x;
        y -= vector.y;
        return (T) this;
    }


    public <T extends Tuple2i> T mul(int x,int y){
        this.x *= x;
        this.y *= y;
        return (T) this;
    }

    public <T extends Tuple2i> T mul(int xy){
        x *= xy;
        y *= xy;
        return (T) this;
    }

    public <T extends Tuple2i> T mul(Tuple2d vector){
        x *= vector.x;
        y *= vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T mul(Tuple2f vector){
        x *= vector.x;
        y *= vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T mul(Tuple3d vector){
        x *= vector.x;
        y *= vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T mul(Tuple3f vector){
        x *= vector.x;
        y *= vector.y;
        return (T) this;
    }


    public <T extends Tuple2i> T div(int x,int y){
        this.x /= x;
        this.y /= y;
        return (T) this;
    }

    public <T extends Tuple2i> T div(int xy){
        x /= xy;
        y /= xy;
        return (T) this;
    }

    public <T extends Tuple2i> T div(Tuple2d vector){
        x /= vector.x;
        y /= vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T div(Tuple2f vector){
        x /= vector.x;
        y /= vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T div(Tuple3d vector){
        x /= vector.x;
        y /= vector.y;
        return (T) this;
    }

    public <T extends Tuple2i> T div(Tuple3f vector){
        x /= vector.x;
        y /= vector.y;
        return (T) this;
    }


    @Override
    public String toString(){
        return "xy: " + x + ", " + y;
    }

    @Override
    public boolean equals(Object object){
        if(object == null || getClass() != object.getClass())
            return false;

        Tuple2i tuple = (Tuple2i) object;
        return x == tuple.x && y == tuple.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x,y);
    }

}
