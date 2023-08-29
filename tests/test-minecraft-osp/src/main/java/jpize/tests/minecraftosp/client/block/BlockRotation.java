package jpize.tests.minecraftosp.client.block;

import jpize.math.vecmath.matrix.Matrix4f;
import jpize.math.vecmath.vector.Vec3i;

public enum BlockRotation{

    X90 (1, 0, 0),
    Y90 (0, 1, 0),
    Z90 (0, 0, 1);

    private final Vec3i axis;
    private final Matrix4f matrix;

    BlockRotation(int x, int y, int z){
        this.axis = new Vec3i(x, y, z);
        this.matrix = new Matrix4f();

        if(x == 1)
            matrix.toRotatedX(90);
        else if(y == 1)
            matrix.toRotatedY(90);
        else if(z == 1)
            matrix.toRotatedZ(90);
    }

    public Vec3i getAxis(){
        return axis;
    }

    public Matrix4f getMatrix(){
        return matrix;
    }

}
