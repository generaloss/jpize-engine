package glit.math.util;

import glit.math.Mathc;
import glit.math.vecmath.matrix.Matrix4f;

public class Frustum{

    private float[][] frustum;

    public Frustum(Matrix4f view, Matrix4f proj){
        setFrustum(view, proj);
    }


    public void setFrustum(Matrix4f view, Matrix4f proj){
        float[] clip = Matrix4f.mul(proj.val, view.val);

        frustum = new float[][]{
            { clip[3] - clip[0], clip[7] - clip[4], clip[11] - clip[8 ], clip[15] - clip[12] },
            { clip[3] + clip[0], clip[7] + clip[4], clip[11] + clip[8 ], clip[15] + clip[12] },
            { clip[3] + clip[1], clip[7] + clip[5], clip[11] + clip[9 ], clip[15] + clip[13] },
            { clip[3] - clip[1], clip[7] - clip[5], clip[11] - clip[9 ], clip[15] - clip[13] },
            { clip[3] - clip[2], clip[7] - clip[6], clip[11] - clip[10], clip[15] - clip[14] },
            { clip[3] + clip[2], clip[7] + clip[6], clip[11] + clip[10], clip[15] + clip[14] }
        };

        for(int i = 0; i < 6; i++)
            divide(i);
    }

    private void divide(int index){
        float f = Mathc.sqrt(
            frustum[index][0] * frustum[index][0] +
            frustum[index][1] * frustum[index][1] +
            frustum[index][2] * frustum[index][2]
        );

        frustum[index][0] /= f;
        frustum[index][1] /= f;
        frustum[index][2] /= f;
        frustum[index][3] /= f;
    }

    private double multiply(int index, double x, double y, double z){
        return
            frustum[index][0] * x +
            frustum[index][1] * y +
            frustum[index][2] * z +
            frustum[index][3];
    }

    public boolean isBoxInFrustum(double x1, double y1, double z1, double x2, double y2, double z2){
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

}