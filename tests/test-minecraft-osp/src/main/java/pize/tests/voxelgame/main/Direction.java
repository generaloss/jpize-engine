package pize.tests.voxelgame.main;

import pize.math.vecmath.vector.Vec2i;
import pize.math.vecmath.vector.Vec3i;

public enum Direction{
    
    NEGATIVE_X(-1,  0,  0),
    POSITIVE_X( 1,  0,  0),
    NEGATIVE_Y( 0, -1,  0),
    POSITIVE_Y( 0,  1,  0),
    NEGATIVE_Z( 0,  0, -1),
    POSITIVE_Z( 0,  0,  1),
    NONE      ( 0,  0,  0);
    
    
    private final Vec3i normal;
    
    Direction(int x, int y, int z){
        normal = new Vec3i(x, y, z);
    }

    public final Vec3i getNormal(){
        return normal;
    }


    public static Direction fromNormal(Vec3i normal){
        return fromNormal(normal.x, normal.y, normal.z);
    }

    public static Direction fromNormal(int x, int y, int z){
        if(x == 1)
            return POSITIVE_X;
        else if(y == 1)
            return POSITIVE_Y;
        else if(z == 1)
            return POSITIVE_Z;
        else if(x == -1)
            return NEGATIVE_X;
        else if(y == -1)
            return NEGATIVE_Y;
        else if(z == -1)
            return NEGATIVE_Z;
        else
            return NONE;
    }


    private static final Vec2i[] normal2D = new Vec2i[]{
        new Vec2i(-1,  0), new Vec2i( 1,  0), new Vec2i( 0, -1), new Vec2i( 0,  1)
    };

    private static final Vec3i[] normal3D = new Vec3i[]{
        NEGATIVE_X.getNormal(), POSITIVE_X.getNormal(), NEGATIVE_Y.getNormal(), POSITIVE_Y.getNormal(), NEGATIVE_Z.getNormal(), POSITIVE_Z.getNormal()
    };

    public static Vec2i normal2DFromIndex(int index){
        return normal2D[index];
    }

    public static Vec3i normal3DFromIndex(int index){
        return normal3D[index];
    }
    
}
