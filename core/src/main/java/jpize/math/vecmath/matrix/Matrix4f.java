package jpize.math.vecmath.matrix;

import jpize.math.Maths;
import jpize.math.vecmath.vector.*;

import java.util.Arrays;

public class Matrix4f implements Matrix4{

    private static final Vec3f UP = new Vec3f(0, 1, 0);
    private final Vec3f tmp_camLeft = new Vec3f();
    private final Vec3f tmp_camUp = new Vec3f();


    public final float[] val;

    /** Constructor */
    public Matrix4f(){
        val = new float[16];
        val[m00] = 1;
        val[m11] = 1;
        val[m22] = 1;
        val[m33] = 1;
    }

    public Matrix4f(float[] values){
        val = new float[16];
        set(values);
    }

    public Matrix4f(Matrix4f matrix){
        this(matrix.val);
    }


    /** Set */
    public Matrix4f set(float[] values){
        System.arraycopy(values, 0, val, 0, values.length);
        return this;
    }

    public Matrix4f set(Matrix4f matrix){
        set(matrix.val);
        return this;
    }

    public Matrix4f zero(){
        Arrays.fill(val, 0);
        return this;
    }

    public Matrix4f identity(){
        val[m00] = 1; val[m10] = 0; val[m20] = 0; val[m30] = 0;
        val[m01] = 0; val[m11] = 1; val[m21] = 0; val[m31] = 0;
        val[m02] = 0; val[m12] = 0; val[m22] = 1; val[m32] = 0;
        val[m03] = 0; val[m13] = 0; val[m23] = 0; val[m33] = 1;
        return this;
    }


    /** Translate */
    public Matrix4f translate(float x, float y, float z){
        val[m30] += val[m00] * x + val[m10] * y + val[m20] * z;
        val[m31] += val[m01] * x + val[m11] * y + val[m21] * z;
        val[m32] += val[m02] * x + val[m12] * y + val[m22] * z;
        val[m33] += val[m03] * x + val[m13] * y + val[m23] * z;
        return this;
    }

    public Matrix4f translate(float x, float y){
        val[m30] += val[m00] * x + val[m10] * y;
        val[m31] += val[m01] * x + val[m11] * y;
        val[m32] += val[m02] * x + val[m12] * y;
        val[m33] += val[m03] * x + val[m13] * y;
        return this;
    }

    public Matrix4f translate(Vec2f vec2){
        return translate(vec2.x, vec2.y);
    }

    public Matrix4f translate(Vec2d vec2){
        return translate((float) vec2.x, (float) vec2.y);
    }

    public Matrix4f translate(Vec2i vec2){
        return translate(vec2.x, vec2.y);
    }

    public Matrix4f translate(Vec3f vec3){
        return translate(vec3.x, vec3.y, vec3.z);
    }

    public Matrix4f translate(Vec3d vec3){
        return translate((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public Matrix4f translate(Vec3i vec3){
        return translate(vec3.x, vec3.y, vec3.z);
    }


    /** To Translated */
    public Matrix4f toTranslated(float x, float y, float z){
        identity();
        val[m30] = x;
        val[m31] = y;
        val[m32] = z;
        return this;
    }

    public Matrix4f toTranslated(float x, float y){
        identity();
        val[m30] = x;
        val[m31] = y;
        return this;
    }

    public Matrix4f toTranslated(Vec2f vec2){
        return toTranslated(vec2.x, vec2.y);
    }

    public Matrix4f toTranslated(Vec2d vec2){
        return toTranslated((float) vec2.x, (float) vec2.y);
    }

    public Matrix4f toTranslated(Vec2i vec2){
        return toTranslated(vec2.x, vec2.y);
    }

    public Matrix4f toTranslated(Vec3f vec3){
        return toTranslated(vec3.x, vec3.y, vec3.z);
    }

    public Matrix4f toTranslated(Vec3d vec3){
        return toTranslated((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public Matrix4f toTranslated(Vec3i vec3){
        return toTranslated(vec3.x, vec3.y, vec3.z);
    }


    /** To Scaled */
    public Matrix4f toScaled(float x, float y, float z){
        identity();
        val[m00] = x;
        val[m11] = y;
        val[m22] = z;
        return this;
    }

    public Matrix4f toScaled(float x, float y){
        identity();
        val[m00] = x;
        val[m11] = y;
        return this;
    }

    public Matrix4f toScaled(float scale){
        return toScaled(scale, scale, scale);
    }

    public Matrix4f toScaled(double scale){
        return toScaled((float) scale);
    }

    public Matrix4f toScaled(Vec3f vec3){
        return toScaled(vec3.x, vec3.y, vec3.z);
    }

    public Matrix4f toScaled(Vec3d vec3){
        return toScaled((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public Matrix4f toScaled(Vec3i vec3){
        return toScaled(vec3.x, vec3.y, vec3.z);
    }

    public Matrix4f toScaled(Vec2f vec2){
        return toScaled(vec2.x, vec2.y);
    }

    public Matrix4f toScaled(Vec2d vec2){
        return toScaled((float) vec2.x, (float) vec2.y);
    }

    public Matrix4f toScaled(Vec2i vec2){
        return toScaled(vec2.x, vec2.y);
    }


    /** To Rotated */
    public Matrix4f toRotatedX(double degrees){
        identity();

        final float cos = Maths.cosDeg(degrees);
        final float sin = Maths.sinDeg(degrees);

        val[m11] = cos;
        val[m21] = -sin;
        val[m12] = sin;
        val[m22] = cos;

        return this;
    }

    public Matrix4f toRotatedY(double degrees){
        identity();

        final float cos = Maths.cosDeg(degrees);
        final float sin = Maths.sinDeg(degrees);

        val[m00] = cos;
        val[m20] = sin;
        val[m02] = -sin;
        val[m22] = cos;

        return this;
    }

    public Matrix4f toRotatedZ(double degrees){
        identity();

        final float cos = Maths.cosDeg(degrees);
        final float sin = Maths.sinDeg(degrees);

        val[m00] = cos;
        val[m10] = -sin;
        val[m01] = sin;
        val[m11] = cos;

        return this;
    }


    /** To Projection */
    public Matrix4f toOrthographic(float left, float right, float bottom, float top, float near, float far){
        identity();

        final float width = right - left;
        final float height = top - bottom;
        final float depth = far - near;

        val[m00] = 2 / width;
        val[m11] = 2 / height;
        val[m22] = -2 / depth;

        val[m30] = -(right + left) / width;
        val[m31] = -(top + bottom) / height;
        val[m32] = -(far + near) / depth;

        return this;
    }

    public Matrix4f toOrthographic(float x, float y, float width, float height){
        identity();

        val[m00] = 2 / width;
        val[m11] = 2 / height;
        val[m22] = -2;

        val[m30] = -(x * 2 + width) / width;
        val[m31] = -(y * 2 + height) / height;
        val[m32] = -1;

        return this;
    }

    public Matrix4f toPerspective(float width, float height, float near, float far, float fov){
        zero();

        final float ctgFov = 1 / Maths.tanDeg(fov * 0.5);
        final float aspect = width / height;
        final float depth = far - near;

        val[m00] = ctgFov / aspect;
        val[m11] = ctgFov;
        val[m22] = (far + near) / depth;

        val[m23] = 1;
        val[m32] = -(2 * far * near) / depth;

        return this;
    }


    /** To Look At */
    public Matrix4f toLookAt(float leftX, float leftY, float leftZ, float upX, float upY, float upZ, float forwardX, float forwardY, float forwardZ){
        identity();
        
        val[m00] = leftX;
        val[m10] = leftY;
        val[m20] = leftZ;
        val[m01] = upX;
        val[m11] = upY;
        val[m21] = upZ;
        val[m02] = forwardX;
        val[m12] = forwardY;
        val[m22] = forwardZ;
        
        return this;
    }

    public Matrix4f toLookAt(Vec3f left, Vec3f up, Vec3f forward){
        return toLookAt(left.x, left.y, left.z, up.x, up.y, up.z, forward.x, forward.y, forward.z);
    }

    public Matrix4f toLookAt(float posX, float posY, float posZ, Vec3f left, Vec3f up, Vec3f forward){
        return toLookAt(left, up, forward).mul(new Matrix4f().toTranslated(-posX, -posY, -posZ));
    }

    public Matrix4f toLookAt(Vec3f pos, Vec3f left, Vec3f up, Vec3f forward){
        return toLookAt(pos.x, pos.y, pos.z, left, up, forward);
    }


    public Matrix4f toLookAt(float posX, float posY, float posZ, Vec3f direction){
        tmp_camLeft.crs(UP, direction).nor();
        tmp_camUp.crs(direction, tmp_camLeft).nor();

        return toLookAt(posX, posY, posZ, tmp_camLeft, tmp_camUp, direction);
    }

    public Matrix4f toLookAt(Vec3f position, Vec3f direction){
        return toLookAt(position.x, position.y, position.z, direction);
    }

    public Matrix4f toLookAt(Vec3f direction){
        tmp_camLeft.crs(UP, direction).nor();
        tmp_camUp.crs(direction, tmp_camLeft).nor();
        
        return toLookAt(tmp_camLeft, tmp_camUp, direction);
    }
    

    /** Culling */
    public Matrix4f cullPosition(){
        val[m30] = 0; // X
        val[m31] = 0; // Y
        val[m32] = 0; // Z
        return this;
    }
    
    public Matrix4f cullRotation(){
        val[m00] = 1; val[m10] = 0; val[m20] = 0;
        val[m01] = 0; val[m11] = 1; val[m21] = 0;
        val[m02] = 0; val[m12] = 0; val[m22] = 1;
        return this;
    }


    /** Copy */
    public Matrix4f copy(){
        return new Matrix4f(this);
    }


    /** Multiply */
    public float[] getMul(float[] values){
        return mul(this.val, values);
    }

    public float[] getMul(Matrix4f matrix){
        return mul(this, matrix);
    }

    public Matrix4f mul(Matrix4f matrix){
        return set(mul(this, matrix));
    }

    public Matrix4f mul(float[] values){
        return set(mul(this.val, values));
    }


    public static float[] mul(float[] a, float[] b){
        return new float[]{
            a[m00] * b[m00]  +  a[m10] * b[m01]  +  a[m20] * b[m02]  +  a[m30] * b[m03],
            a[m01] * b[m00]  +  a[m11] * b[m01]  +  a[m21] * b[m02]  +  a[m31] * b[m03],
            a[m02] * b[m00]  +  a[m12] * b[m01]  +  a[m22] * b[m02]  +  a[m32] * b[m03],
            a[m03] * b[m00]  +  a[m13] * b[m01]  +  a[m23] * b[m02]  +  a[m33] * b[m03],

            a[m00] * b[m10]  +  a[m10] * b[m11]  +  a[m20] * b[m12]  +  a[m30] * b[m13],
            a[m01] * b[m10]  +  a[m11] * b[m11]  +  a[m21] * b[m12]  +  a[m31] * b[m13],
            a[m02] * b[m10]  +  a[m12] * b[m11]  +  a[m22] * b[m12]  +  a[m32] * b[m13],
            a[m03] * b[m10]  +  a[m13] * b[m11]  +  a[m23] * b[m12]  +  a[m33] * b[m13],

            a[m00] * b[m20]  +  a[m10] * b[m21]  +  a[m20] * b[m22]  +  a[m30] * b[m23],
            a[m01] * b[m20]  +  a[m11] * b[m21]  +  a[m21] * b[m22]  +  a[m31] * b[m23],
            a[m02] * b[m20]  +  a[m12] * b[m21]  +  a[m22] * b[m22]  +  a[m32] * b[m23],
            a[m03] * b[m20]  +  a[m13] * b[m21]  +  a[m23] * b[m22]  +  a[m33] * b[m23],

            a[m00] * b[m30]  +  a[m10] * b[m31]  +  a[m20] * b[m32]  +  a[m30] * b[m33],
            a[m01] * b[m30]  +  a[m11] * b[m31]  +  a[m21] * b[m32]  +  a[m31] * b[m33],
            a[m02] * b[m30]  +  a[m12] * b[m31]  +  a[m22] * b[m32]  +  a[m32] * b[m33],
            a[m03] * b[m30]  +  a[m13] * b[m31]  +  a[m23] * b[m32]  +  a[m33] * b[m33]
        };
    }

    public static float[] mul(Matrix4f a, Matrix4f b){
        return mul(a.val, b.val);
    }

}