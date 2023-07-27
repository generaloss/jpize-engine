package pize.tests.voxelgame.client.block;

import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec3i;

public enum BlockRotation{

    X90 (1, 0, 0,  90 ),
    X180(1, 0, 0,  180),
    X270(1, 0, 0,  270),
    Y90 (0, 1, 0,  90 ),
    Y180(0, 1, 0,  180),
    Y270(0, 1, 0,  270),
    Z90 (0, 0, 1,  90 ),
    Z180(0, 0, 1,  180),
    Z270(0, 0, 1,  270);


    private final Vec3i axis;
    private final int angle;
    private final Matrix4f matrix;

    BlockRotation(int x, int y, int z, int angle){
        this.axis = new Vec3i(x, y, z);
        this.angle = angle;
        this.matrix = new Matrix4f();

        if(x == 1)
            matrix.toRotatedX(angle);
        else if(y == 1)
            matrix.toRotatedY(angle);
        else if(z == 1)
            matrix.toRotatedZ(angle);
    }

    public Vec3i getAxis(){
        return axis;
    }

    public int getAngle(){
        return angle;
    }

    public Matrix4f getMatrix(){
        return matrix;
    }

}
