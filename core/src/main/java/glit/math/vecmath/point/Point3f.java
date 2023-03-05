package glit.math.vecmath.point;

import glit.math.vecmath.tuple.Tuple2d;
import glit.math.vecmath.tuple.Tuple2f;
import glit.math.vecmath.tuple.Tuple3d;
import glit.math.vecmath.tuple.Tuple3f;
import glit.math.vecmath.vector.Vec3d;
import glit.math.vecmath.vector.Vec3f;

public class Point3f extends Tuple3f implements Cloneable{


    public Point3f(){}

    public Point3f(double x, double y, double z){
        set(x, y, z);
    }

    public Point3f(float x, float y, float z){
        set(x, y, z);
    }

    public Point3f(double xyz){
        set(xyz);
    }

    public Point3f(float xyz){
        set(xyz);
    }

    public Point3f(Tuple3f vector){
        set(vector);
    }

    public Point3f(Tuple3d vector){
        set(vector);
    }

    public Point3f(Tuple2f vector){
        set(vector);
    }

    public Point3f(Tuple2d vector){
        set(vector);
    }


    public float dst(float x, float y, float z){
        float dx = this.x - x;
        float dy = this.y - y;
        float dz = this.z - z;

        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double dst(double x, double y, double z){
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public float dst(Vec3f vector){
        float dx = x - vector.x;
        float dy = y - vector.y;
        float dz = z - vector.z;

        return (float) Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double dst(Vec3d vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        double dz = z - vector.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }


    @Override
    public Point3f clone(){
        return new Point3f(this);
    }


}
