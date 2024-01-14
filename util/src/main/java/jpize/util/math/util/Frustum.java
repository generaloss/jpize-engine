package jpize.util.math.util;

import jpize.util.math.Mathc;
import jpize.util.math.vecmath.matrix.Matrix4f;

public class Frustum{

    private final float[][] values;

    public Frustum(){
        values = new float[6][4];
    }

    public Frustum(Matrix4f view, Matrix4f proj){
        this();
        setFrustum(view, proj);
    }


    public void setFrustum(Matrix4f view, Matrix4f proj){
        final float[] clip = Matrix4f.mul(proj.val, view.val);

        values[0][0] = clip[3] - clip[0];
        values[1][0] = clip[3] + clip[0];
        values[2][0] = clip[3] + clip[1];
        values[3][0] = clip[3] - clip[1];
        values[4][0] = clip[3] - clip[2];
        values[5][0] = clip[3] + clip[2];

        values[0][1] = clip[7] - clip[4];
        values[1][1] = clip[7] + clip[4];
        values[2][1] = clip[7] + clip[5];
        values[3][1] = clip[7] - clip[5];
        values[4][1] = clip[7] - clip[6];
        values[5][1] = clip[7] + clip[6];

        values[0][2] = clip[11] - clip[8 ];
        values[1][2] = clip[11] + clip[8 ];
        values[2][2] = clip[11] + clip[9 ];
        values[3][2] = clip[11] - clip[9 ];
        values[4][2] = clip[11] - clip[10];
        values[5][2] = clip[11] + clip[10];

        values[0][3] = clip[15] - clip[12];
        values[1][3] = clip[15] + clip[12];
        values[2][3] = clip[15] + clip[13];
        values[3][3] = clip[15] - clip[13];
        values[4][3] = clip[15] - clip[14];
        values[5][3] = clip[15] + clip[14];

        for(int i = 0; i < 6; i++)
            divide(i);
    }

    private void divide(int index){
        final float sqrt = Mathc.sqrt(
            values[index][0] * values[index][0] +
            values[index][1] * values[index][1] +
            values[index][2] * values[index][2]
        );

        values[index][0] /= sqrt;
        values[index][1] /= sqrt;
        values[index][2] /= sqrt;
        values[index][3] /= sqrt;
    }

    private double multiply(int index, float x, float y, float z){
        return
            values[index][0] * x +
            values[index][1] * y +
            values[index][2] * z +
            values[index][3];
    }

    public boolean isBoxInFrustum(float x1, float y1, float z1, float x2, float y2, float z2){
        for(int i = 0; i < 6; i++)
            if(
                multiply(i, x1, y1, z1) <= 0 &&
                multiply(i, x2, y1, z1) <= 0 &&
                multiply(i, x1, y2, z1) <= 0 &&
                multiply(i, x2, y2, z1) <= 0 &&
                multiply(i, x1, y1, z2) <= 0 &&
                multiply(i, x2, y1, z2) <= 0 &&
                multiply(i, x1, y2, z2) <= 0 &&
                multiply(i, x2, y2, z2) <= 0
            )
                return false;
        return true;
    }

    public boolean isVertexInFrustum(float x, float y, float z){
        for(int i = 0; i < 6; i++)
            if(multiply(i, x, y, z) <= 0)
                return false;
        return true;
    }



}