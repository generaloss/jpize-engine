package glit.math.vecmath.vector;

import glit.math.vecmath.tuple.*;

public class Vec3i extends Tuple3f implements Cloneable{


    public Vec3i(){}

    public Vec3i(double x, double y, double z){
        set(x, y, z);
    }

    public Vec3i(float x, float y, float z){
        set(x, y, z);
    }

    public Vec3i(int x, int y, int z){
        set(x, y, z);
    }
    
    public Vec3i(double xyz){
        set(xyz);
    }

    public Vec3i(float xyz){
        set(xyz);
    }
    
    public Vec3i(int xyz){
        set(xyz);
    }

    public Vec3i(Tuple3d vector){
        set(vector);
    }

    public Vec3i(Tuple3f vector){
        set(vector);
    }

    public Vec3i(Tuple3i vector){
        set(vector);
    }

    public Vec3i(Tuple2d vector){
        set(vector);
    }

    public Vec3i(Tuple2f vector){
        set(vector);
    }

    public Vec3i(Tuple2i vector){
        set(vector);
    }
    
}
