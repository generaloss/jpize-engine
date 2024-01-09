package jpize.math.vecmath.matrix;

import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec2d;
import jpize.math.vecmath.vector.Vec2f;
import jpize.math.vecmath.vector.Vec2i;

import java.util.Arrays;

public class Matrix3f implements Matrix3{

    public final float[] val;

    /** Constructor */
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


    /** Set */
    public Matrix3f set(float[] values){
        System.arraycopy(values, 0, val, 0, values.length);
        return this;
    }

    public Matrix3f set(Matrix3f matrix){
        set(matrix.val);
        return this;
    }

    public Matrix3f zero(){
        Arrays.fill(val, 0);
        return this;
    }

    public Matrix3f identity(){
        val[m00] = 1; val[m10] = 0; val[m20] = 0;
        val[m01] = 0; val[m11] = 1; val[m21] = 0;
        val[m02] = 0; val[m12] = 0; val[m22] = 1;
        return this;
    }


    /** Translate */
    public Matrix3f translate(float x, float y){
        val[m20] += val[m00] * x + val[m10] * y;
        val[m21] += val[m01] * x + val[m11] * y;
        val[m22] += val[m02] * x + val[m12] * y;
        return this;
    }

    public Matrix3f translate(Vec2f vec2){
        return translate(vec2.x, vec2.y);
    }

    public Matrix3f translate(Vec2d vec2){
        return translate((float) vec2.x, (float) vec2.y);
    }

    public Matrix3f translate(Vec2i vec2){
        return translate(vec2.x, vec2.y);
    }


    /** Set Translate */
    public Matrix3f setTranslate(float x, float y){
        identity();
        val[m20] = x;
        val[m21] = y;
        return this;
    }

    public Matrix3f setTranslate(Vec2f vec2){
        return setTranslate(vec2.x, vec2.y);
    }

    public Matrix3f setTranslate(Vec2d vec2){
        return setTranslate((float) vec2.x, (float) vec2.y);
    }

    public Matrix3f setTranslate(Vec2i vec2){
        return setTranslate(vec2.x, vec2.y);
    }


    /** Set Scale */
    public Matrix3f setScale(float x, float y){
        identity();
        val[m00] = x;
        val[m11] = y;
        return this;
    }

    public Matrix3f setScale(float scale){
        return setScale(scale, scale);
    }

    public Matrix3f setScale(double scale){
        return setScale((float) scale);
    }

    public Matrix3f setScale(Vec2f vec2){
        return setScale(vec2.x, vec2.y);
    }

    public Matrix3f setScale(Vec2d vec2){
        return setScale((float) vec2.x, (float) vec2.y);
    }

    public Matrix3f setScale(Vec2i vec2){
        return setScale(vec2.x, vec2.y);
    }


    /** Set Rotation */
    public Matrix3f setRotation(double degrees){
        identity();

        final float cos = Maths.cosDeg(degrees);
        final float sin = Maths.sinDeg(degrees);

        val[m00] = cos;
        val[m10] = -sin;
        val[m01] = sin;
        val[m11] = cos;

        return this;
    }


    /** Shear */

    public Matrix3f shear(double degreesX, double degreesY){
        final float M10 = Maths.tanDeg(degreesX);
        final float M01 = Maths.tanDeg(degreesY);

        final float oldM10 = val[m10];
        final float oldM11 = val[m11];
        final float oldM12 = val[m12];

        val[m10] += val[m00] * M10;
        val[m11] += val[m01] * M10;
        val[m12] += val[m02] * M10;
        val[m00] += oldM10 * M01;
        val[m01] += oldM11 * M01;
        val[m02] += oldM12 * M01;
        return this;
    }

    public Matrix3f shear(Vec2f vec2){
        return shear(vec2.x, vec2.y);
    }


    /** Set Shear */
    public Matrix3f setShear(double degreesX, double degreesY){
        identity();
        val[m10] = Maths.tanDeg(degreesX);
        val[m01] = Maths.tanDeg(degreesY);
        return this;
    }

    public Matrix3f setShear(Vec2f vec2){
        return setShear(vec2.x, vec2.y);
    }


    /** Culling */
    public Matrix3f cullPosition(){
        val[m20] = 0; // X
        val[m21] = 0; // Y
        return this;
    }

    public Matrix3f cullRotation(){
        val[m00] = 1; val[m10] = 0;
        val[m01] = 0; val[m11] = 1;
        return this;
    }


    /** Copy */
    public Matrix3f copy(){
        return new Matrix3f(this);
    }


    /** Multiply */
    public float[] getMul(float[] values){
        return mul(this.val, values);
    }

    public float[] getMul(Matrix3f matrix){
        return mul(this, matrix);
    }

    public Matrix3f mul(Matrix3f matrix){
        return set(mul(this, matrix));
    }

    public Matrix3f mul(float[] values){
        return set(mul(this.val, values));
    }


    public static float[] mul(float[] a, float[] b){
        return new float[]{
            a[m00] * b[m00]  +  a[m10] * b[m01]  +  a[m20] * b[m02],
            a[m01] * b[m00]  +  a[m11] * b[m01]  +  a[m21] * b[m02],
            a[m02] * b[m00]  +  a[m12] * b[m01]  +  a[m22] * b[m02],

            a[m00] * b[m10]  +  a[m10] * b[m11]  +  a[m20] * b[m12],
            a[m01] * b[m10]  +  a[m11] * b[m11]  +  a[m21] * b[m12],
            a[m02] * b[m10]  +  a[m12] * b[m11]  +  a[m22] * b[m12],

            a[m00] * b[m20]  +  a[m10] * b[m21]  +  a[m20] * b[m22],
            a[m01] * b[m20]  +  a[m11] * b[m21]  +  a[m21] * b[m22],
            a[m02] * b[m20]  +  a[m12] * b[m21]  +  a[m22] * b[m22],
        };
    }

    public static float[] mul(Matrix3f a, Matrix3f b){
        return mul(a.val, b.val);
    }

}
