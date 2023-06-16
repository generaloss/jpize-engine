package pize.math.vecmath.matrix;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.vector.*;

import java.util.Arrays;

public class Matrix4f implements Matrix4{

    public final float[] val;

    public Matrix4f(){
        val = new float[16];

        val[m00] = 1;
        val[m11] = 1;
        val[m22] = 1;
        val[m33] = 1;
    }

    public Matrix4f(Matrix4f matrix4){
        val = new float[16];

        set(matrix4);
    }

    public Matrix4f(float[] values){
        val = new float[16];

        set(values);
    }


    public Matrix4f identity(){
        val[m00] = 1;
        val[m10] = 0;
        val[m20] = 0;
        val[m30] = 0;

        val[m01] = 0;
        val[m11] = 1;
        val[m21] = 0;
        val[m31] = 0;

        val[m02] = 0;
        val[m12] = 0;
        val[m22] = 1;
        val[m32] = 0;

        val[m03] = 0;
        val[m13] = 0;
        val[m23] = 0;
        val[m33] = 1;

        return this;
    }

    public Matrix4f zero(){
        Arrays.fill(val, 0);

        return this;
    }

    public Matrix4f set(float[] values){
        val[m00] = values[m00];
        val[m10] = values[m10];
        val[m20] = values[m20];
        val[m30] = values[m30];

        val[m01] = values[m01];
        val[m11] = values[m11];
        val[m21] = values[m21];
        val[m31] = values[m31];

        val[m02] = values[m02];
        val[m12] = values[m12];
        val[m22] = values[m22];
        val[m32] = values[m32];

        val[m03] = values[m03];
        val[m13] = values[m13];
        val[m23] = values[m23];
        val[m33] = values[m33];

        return this;
    }

    public Matrix4f set(Matrix4f matrix){
        set(matrix.val);

        return this;
    }


    public Matrix4f translate(Vec2f v){
        return translate(v.x, v.y, 0);
    }

    public Matrix4f translate(Vec3f v){
        return translate(v.x, v.y, v.z);
    }

    public Matrix4f translate(Vec3d v){
        return translate(v.x, v.y, v.z);
    }

    public Matrix4f translate(double x, double y, double z){
        val[m03] += val[m00] * x + val[m01] * y + val[m02] * z;
        val[m13] += val[m10] * x + val[m11] * y + val[m12] * z;
        val[m23] += val[m20] * x + val[m21] * y + val[m22] * z;
        val[m33] += val[m30] * x + val[m31] * y + val[m32] * z;

        return this;
    }

    public Matrix4f toScaled(Vec3f v){
        return toScaled(v.x, v.y, v.z);
    }

    public Matrix4f toScaled(Vec3d v){
        return toScaled((float) v.x, (float) v.y, (float) v.z);
    }

    public Matrix4f toScaled(float v){
        return toScaled(v, v, v);
    }

    public Matrix4f toScaled(float x, float y, float z){
        identity();

        val[m00] = x;
        val[m11] = y;
        val[m22] = z;

        return this;
    }

    public Matrix4f toTranslated(Vec2f v){
        return toTranslated(v.x, v.y, 0);
    }
    
    public Matrix4f toTranslated(Vec2i v){
        return toTranslated(v.x, v.y, 0);
    }

    public Matrix4f toTranslated(Vec3f v){
        return toTranslated(v.x, v.y, v.z);
    }
    
    public Matrix4f toTranslated(Vec3i v){
        return toTranslated(v.x, v.y, v.z);
    }

    public Matrix4f toTranslated(Vec3d v){
        return toTranslated((float) v.x, (float) v.y, (float) v.z);
    }

    public Matrix4f toTranslated(float x, float y, float z){
        identity();

        val[m03] = x;
        val[m13] = y;
        val[m23] = z;

        return this;
    }

    public Matrix4f toRotatedX(double angle){
        identity();

        float cos = Mathc.cos(angle * Maths.toRad);
        float sin = Mathc.sin(angle * Maths.toRad);

        val[m11] = cos;
        val[m12] = -sin;
        val[m21] = sin;
        val[m22] = cos;

        return this;
    }

    public Matrix4f toRotatedY(double angle){
        identity();

        float cos = Mathc.cos(angle * Maths.toRad);
        float sin = Mathc.sin(angle * Maths.toRad);

        val[m00] = cos;
        val[m02] = sin;
        val[m20] = -sin;
        val[m22] = cos;

        return this;
    }

    public Matrix4f toRotatedZ(double angle){
        identity();

        float cos = Mathc.cos(angle * Maths.toRad);
        float sin = Mathc.sin(angle * Maths.toRad);

        val[m00] = cos;
        val[m01] = -sin;
        val[m10] = sin;
        val[m11] = cos;

        return this;
    }

    public Matrix4f toOrthographic(float left, float right, float bottom, float top, float near, float far){
        identity();

        val[m00] = 2 / (right - left);
        val[m11] = 2 / (top - bottom);
        val[m22] = -2 / (far - near);

        val[m03] = -(right + left) / (right - left);
        val[m13] = -(top + bottom) / (top - bottom);
        val[m23] = -(far + near) / (far - near);

        return this;
    }

    public Matrix4f toOrthographic(float x, float y, float width, float height){
        identity();

        val[m00] = 2 / width;
        val[m11] = 2 / height;
        val[m22] = -2;

        val[m03] = -(x * 2 + width) / width;
        val[m13] = -(y * 2 + height) / height;
        val[m23] = -1;

        return this;
    }

    public Matrix4f toPerspective(float width, float height, float near, float far, float fov){
        zero();

        float ctgFov = 1 / Mathc.tan(Math.toRadians(fov * 0.5));
        float aspect = width / height;

        val[m00] = ctgFov / aspect;
        val[m11] = ctgFov;
        val[m22] = (far + near) / (far - near);

        val[m32] = 1;
        val[m23] = -(2 * far * near) / (far - near);

        return this;
    }

    public Matrix4f toLookAt(float posX, float posY, float posZ, float leftX, float leftY, float leftZ, float upX, float upY, float upZ, float forwardX, float forwardY, float forwardZ){
        identity();

        val[m00] = leftX;
        val[m01] = leftY;
        val[m02] = leftZ;
        val[m10] = upX;
        val[m11] = upY;
        val[m12] = upZ;
        val[m20] = forwardX;
        val[m21] = forwardY;
        val[m22] = forwardZ;

        mul(new Matrix4f().toTranslated(-posX, -posY, -posZ));

        return this;
    }

    public Matrix4f toLookAt(Vec3d pos, Vec3d left, Vec3d up, Vec3d forward){
        return toLookAt((float) pos.x, (float) pos.y, (float) pos.z, (float) left.x, (float) left.y, (float) left.z, (float) up.x, (float) up.y, (float) up.z, (float) forward.x, (float) forward.y, (float) forward.z);
    }

    public Matrix4f toLookAt(Vec3f pos, Vec3f left, Vec3f up, Vec3f forward){
        return toLookAt(pos.x, pos.y, pos.z, left.x, left.y, left.z, up.x, up.y, up.z, forward.x, forward.y, forward.z);
    }

    public Matrix4f toLookAt(float posX, float posY, float posZ, Vec3f left, Vec3f up, Vec3f forward){
        return toLookAt(posX, posY, posZ, left.x, left.y, left.z, up.x, up.y, up.z, forward.x, forward.y, forward.z);
    }

    private static final Vec3d upDouble = new Vec3d(0, 1, 0);

    private static final Vec3f up = new Vec3f(0, 1, 0);
    private static final Vec3f left = new Vec3f();
    private static final Vec3f camUp = new Vec3f();

    public Matrix4f toLookAt(Vec3d position, Vec3d direction){
        Vec3d left = Vec3d.crs(upDouble, direction.nor()).nor();
        Vec3d up = Vec3d.crs(direction, left).nor();

        return toLookAt(position, left, up, direction);
    }

    public Matrix4f toLookAt(Vec3f position, Vec3f direction){
        left.set(Vec3f.crs(up, direction.nor()).nor());
        camUp.set(Vec3f.crs(direction, left).nor());

        return toLookAt(position, left, camUp, direction);
    }

    public Matrix4f toLookAt(float posX, float posY, float posZ, Vec3f direction){
        left.set(Vec3f.crs(up, direction.nor()).nor());
        camUp.set(Vec3f.crs(direction, left).nor());

        return toLookAt(posX, posY, posZ, left, camUp, direction);
    }
    
    
    public void cullPosition(){
        val[m03] = 0;
        val[m13] = 0;
        val[m23] = 0;
    }
    
    public void cullRotation(){
        val[m00] = 1;
        val[m10] = 0;
        val[m20] = 0;
        
        val[m01] = 0;
        val[m11] = 1;
        val[m21] = 0;
        
        val[m02] = 0;
        val[m12] = 0;
        val[m22] = 1;
    }


    public float[] getMul(Matrix4f matrix){
        return mul(this, matrix);
    }

    public Matrix4f mul(Matrix4f matrix){
        return set(mul(this, matrix));
    }

    public float[] getMul(float[] matrix){
        return mul(this.val, matrix);
    }

    public Matrix4f mul(float[] matrix){
        return set(mul(this.val, matrix));
    }


    public static float[] mul(Matrix4f a, Matrix4f b){
        return mul(a.val, b.val);
    }

    public static float[] mul(float[] a, float[] b){
        return new float[]{ a[m00] * b[m00] + a[m01] * b[m10] + a[m02] * b[m20] + a[m03] * b[m30], a[m10] * b[m00] + a[m11] * b[m10] + a[m12] * b[m20] + a[m13] * b[m30], a[m20] * b[m00] + a[m21] * b[m10] + a[m22] * b[m20] + a[m23] * b[m30], a[m30] * b[m00] + a[m31] * b[m10] + a[m32] * b[m20] + a[m33] * b[m30],

            a[m00] * b[m01] + a[m01] * b[m11] + a[m02] * b[m21] + a[m03] * b[m31], a[m10] * b[m01] + a[m11] * b[m11] + a[m12] * b[m21] + a[m13] * b[m31], a[m20] * b[m01] + a[m21] * b[m11] + a[m22] * b[m21] + a[m23] * b[m31], a[m30] * b[m01] + a[m31] * b[m11] + a[m32] * b[m21] + a[m33] * b[m31],

            a[m00] * b[m02] + a[m01] * b[m12] + a[m02] * b[m22] + a[m03] * b[m32], a[m10] * b[m02] + a[m11] * b[m12] + a[m12] * b[m22] + a[m13] * b[m32], a[m20] * b[m02] + a[m21] * b[m12] + a[m22] * b[m22] + a[m23] * b[m32], a[m30] * b[m02] + a[m31] * b[m12] + a[m32] * b[m22] + a[m33] * b[m32],

            a[m00] * b[m03] + a[m01] * b[m13] + a[m02] * b[m23] + a[m03] * b[m33], a[m10] * b[m03] + a[m11] * b[m13] + a[m12] * b[m23] + a[m13] * b[m33], a[m20] * b[m03] + a[m21] * b[m13] + a[m22] * b[m23] + a[m23] * b[m33], a[m30] * b[m03] + a[m31] * b[m13] + a[m32] * b[m23] + a[m33] * b[m33] };
    }

}