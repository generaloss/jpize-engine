package jpize.util.math.matrix;

import jpize.util.math.Mathc;
import jpize.util.math.vector.Vec4f;

import static jpize.util.math.matrix.Matrix4.*;

public class Frustum{

    private final Vec4f[] val;

    public Frustum(){
        this.val = new Vec4f[]{
            new Vec4f(), new Vec4f(), new Vec4f(), new Vec4f(), new Vec4f(), new Vec4f()
        };
    }

    public Frustum(Matrix4f view, Matrix4f proj){
        this();
        setFrustum(proj, view);
    }

    public Frustum(Matrix4f comb){
        this();
        setFrustum(comb);
    }


    public void setFrustum(Matrix4f view, Matrix4f proj){
        final float[] comb = Matrix4f.mul(proj.val, view.val);
        setFrustum(comb);
    }

    public void setFrustum(Matrix4f comb){
        setFrustum(comb.val);
    }

    public void setFrustum(float[] comb){
        val[0].x = comb[m03] - comb[m00];
        val[0].y = comb[m13] - comb[m10];
        val[0].z = comb[m23] - comb[m20];
        val[0].w = comb[m33] - comb[m30];

        val[1].x = comb[m03] + comb[m00];
        val[1].y = comb[m13] + comb[m10];
        val[1].z = comb[m23] + comb[m20];
        val[1].w = comb[m33] + comb[m30];

        val[2].x = comb[m03] + comb[m01];
        val[2].y = comb[m13] + comb[m11];
        val[2].z = comb[m23] + comb[m21];
        val[2].w = comb[m33] + comb[m31];

        val[3].x = comb[m03] - comb[m01];
        val[3].y = comb[m13] - comb[m11];
        val[3].z = comb[m23] - comb[m21];
        val[3].w = comb[m33] - comb[m31];

        val[4].x = comb[m03] - comb[m02];
        val[4].y = comb[m13] - comb[m12];
        val[4].z = comb[m23] - comb[m22];
        val[4].w = comb[m33] - comb[m32];

        val[5].x = comb[m03] + comb[m02];
        val[5].y = comb[m13] + comb[m12];
        val[5].z = comb[m23] + comb[m22];
        val[5].w = comb[m33] + comb[m32];

        for(int i = 0; i < 6; i++)
            divide(i);
    }

    private void divide(int index){
        final float sqrt = Mathc.sqrt(
            val[index].x * val[index].x +
            val[index].y * val[index].y +
            val[index].z * val[index].z
        );

        val[index].x /= sqrt;
        val[index].y /= sqrt;
        val[index].z /= sqrt;
        val[index].w /= sqrt;
    }

    private double multiply(int index, float x, float y, float z){
        return val[index].x * x  +  val[index].y * y  +  val[index].z * z  +  val[index].w;
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