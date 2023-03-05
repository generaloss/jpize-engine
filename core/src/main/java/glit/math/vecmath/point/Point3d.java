package glit.math.vecmath.point;

import glit.math.vecmath.tuple.Tuple2d;
import glit.math.vecmath.tuple.Tuple2f;
import glit.math.vecmath.tuple.Tuple3d;
import glit.math.vecmath.tuple.Tuple3f;
import glit.math.vecmath.vector.Vec3d;
import glit.math.vecmath.vector.Vec3f;

public class Point3d extends Tuple3d implements Cloneable{


    public Point3d(){}

    public Point3d(double x, double y, double z){
        set(x, y, z);
    }

    public Point3d(float x, float y, float z){
        set(x, y, z);
    }

    public Point3d(double xyz){
        set(xyz);
    }

    public Point3d(float xyz){
        set(xyz);
    }

    public Point3d(Tuple3f vector){
        set(vector);
    }

    public Point3d(Tuple3d vector){
        set(vector);
    }

    public Point3d(Tuple2f vector){
        set(vector);
    }

    public Point3d(Tuple2d vector){
        set(vector);
    }


    public double dst(float x, float y, float z){
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double dst(double x, double y, double z){
        double dx = this.x - x;
        double dy = this.y - y;
        double dz = this.z - z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double dst(Vec3f vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        double dz = z - vector.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }

    public double dst(Vec3d vector){
        double dx = x - vector.x;
        double dy = y - vector.y;
        double dz = z - vector.z;

        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }


    @Override
    public Point3d clone(){
        return new Point3d(this);
    }


}
