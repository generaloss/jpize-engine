package pize.math.vecmath.matrix;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec2f;

import java.util.Arrays;

public class Matrix3f implements Matrix3{

    public final float[] val;

    public Matrix3f(){
        val = new float[16];

        val[m00] = 1;
        val[m11] = 1;
        val[m22] = 1;
    }
    
    
    public Matrix3f(float[] values){
        val = new float[16];
        set(values);
    }
    
    public Matrix3f(Matrix3f matrix){
        this(matrix.val);
    }


    public Matrix3f zero(){
        Arrays.fill(val, 0);

        return this;
    }

    public Matrix3f identity(){
        val[m00] = 1;
        val[m01] = 0;
        val[m02] = 0;

        val[m10] = 0;
        val[m11] = 1;
        val[m12] = 0;

        val[m20] = 0;
        val[m21] = 0;
        val[m22] = 1;

        return this;
    }

    public Matrix3f set(float[] values){
        val[m00] = values[m00];
        val[m01] = values[m01];
        val[m02] = values[m02];

        val[m10] = values[m10];
        val[m11] = values[m11];
        val[m12] = values[m12];

        val[m20] = values[m20];
        val[m21] = values[m21];
        val[m22] = values[m22];

        return this;
    }

    public Matrix3f set(Matrix3f matrix){
        set(matrix.val);

        return this;
    }


    public Matrix3f translate(Vec2f translation){
        return translate(translation.x, translation.y);
    }

    public Matrix3f translate(double x, double y){
        val[m20] += val[m00] * x + val[m10] * y;
        val[m21] += val[m01] * x + val[m11] * y;
        val[m22] += val[m02] * x + val[m12] * y;

        return this;
    }


    public Matrix3f toScaled(float scale){
        return toScaled(scale, scale);
    }

    public Matrix3f toScaled(float x, float y){
        identity();

        val[m00] = x;
        val[m11] = y;

        return this;
    }

    public Matrix3f toTranslated(Vec2f position){
        return toTranslated(position.x, position.y);
    }

    public Matrix3f toTranslated(float x, float y){
        identity();

        val[m20] = x;
        val[m21] = y;

        return this;
    }

    /**
     * Set matrix to rotated
     *
     * @param angle Angle in degrees
     * @return      That instance
     */
    public Matrix3f toRotated(double angle){
        identity();

        float cos = Mathc.cos(angle * Maths.ToRad);
        float sin = Mathc.sin(angle * Maths.ToRad);

        val[m00] = cos;
        val[m10] = -sin;
        val[m01] = sin;
        val[m11] = cos;

        return this;
    }

    public Matrix3f toSheared(float angleX, float angleY){
        identity();

        val[m10] = Mathc.tan(angleX * Maths.ToRad);
        val[m01] = Mathc.tan(angleY * Maths.ToRad);

        return this;
    }


    public float[] getMul(Matrix3f matrix){
        return mul(this, matrix);
    }

    public Matrix3f mul(Matrix3f matrix){
        return set(mul(this, matrix));
    }

    public float[] getMul(float[] matrix){
        return mul(this.val, matrix);
    }

    public Matrix3f mul(float[] matrix){
        return set(mul(this.val, matrix));
    }
    
    
    public Matrix3f copy(){
        return new Matrix3f(this);
    }
    

    public static float[] mul(Matrix3f a, Matrix3f b){
        return mul(a.val, b.val);
    }

    public static float[] mul(float[] a, float[] b){
        return new float[]{
            a[m00] * b[m00] + a[m10] * b[m01] + a[m20] * b[m02],
            a[m01] * b[m00] + a[m11] * b[m01] + a[m21] * b[m02],
            a[m02] * b[m00] + a[m12] * b[m01] + a[m22] * b[m02],

            a[m00] * b[m10] + a[m10] * b[m11] + a[m20] * b[m12],
            a[m01] * b[m10] + a[m11] * b[m11] + a[m21] * b[m12],
            a[m02] * b[m10] + a[m12] * b[m11] + a[m22] * b[m12],

            a[m00] * b[m20] + a[m10] * b[m21] + a[m20] * b[m22],
            a[m01] * b[m20] + a[m11] * b[m21] + a[m21] * b[m22],
            a[m02] * b[m20] + a[m12] * b[m21] + a[m22] * b[m22],
        };
    }

}
