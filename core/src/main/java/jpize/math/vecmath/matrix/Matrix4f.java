package jpize.math.vecmath.matrix;

import jpize.math.Maths;
import jpize.math.util.EulerAngles;
import jpize.math.util.Quaternion;
import jpize.math.vecmath.vector.*;

import java.util.Arrays;

public class Matrix4f implements Matrix4{

    private static final Vec3f UP = new Vec3f(0, 1, 0);
    private final Vec3f tmp_camRight = new Vec3f();
    private final Vec3f tmp_camUp = new Vec3f();


    public final float[] val;

    /* Constructor */

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


    /* Set */

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


    /* To Projection */

    public Matrix4f setOrthographic(float left, float right, float bottom, float top, float near, float far){
        final float iw = 1 / (right - left);
        final float ih = 1 / (top - bottom);
        final float id = 1 / (far - near);

        final float A =  2 * iw;
        final float B =  2 * ih;
        final float C = -2 * id;
        final float D = -(right + left) * iw;
        final float E = -(top + bottom) * ih;
        final float F = -(far + near)   * id;

        val[m00] = A; val[m10] = 0; val[m20] = 0; val[m30] = D;
        val[m01] = 0; val[m11] = B; val[m21] = 0; val[m31] = E;
        val[m02] = 0; val[m12] = 0; val[m22] = C; val[m32] = F;
        val[m03] = 0; val[m13] = 0; val[m23] = 0; val[m33] = 1;
        return this;
    }

    public Matrix4f setOrthographic(float x, float y, float width, float height){
        final float iw = 1 / width;
        final float ih = 1 / height;

        final float A = 2 * iw;
        final float B = 2 * ih;
        final float C = -(2 * x + width) * iw;
        final float D = -(2 * y + height) * ih;

        val[m00] = A; val[m10] = 0; val[m20] =  0; val[m30] =  C;
        val[m01] = 0; val[m11] = B; val[m21] =  0; val[m31] =  D;
        val[m02] = 0; val[m12] = 0; val[m22] = -2; val[m32] = -1;
        val[m03] = 0; val[m13] = 0; val[m23] =  0; val[m33] =  1;
        return this;
    }

    public Matrix4f setPerspective(float aspect, float near, float far, float fovY){
        final float fmn = far - near;

        final float A = 1 / Maths.tanDeg(fovY * 0.5);
        final float B = A * aspect;
        final float C = (far + near) / fmn;
        final float D = (2 * far * near) / fmn;

        val[m00] = -A; val[m10] = 0; val[m20] =  0; val[m30] = 0;
        val[m01] =  0; val[m11] = B; val[m21] =  0; val[m31] = 0;
        val[m02] =  0; val[m12] = 0; val[m22] = -C; val[m32] = 1;
        val[m03] =  0; val[m13] = 0; val[m23] =  D; val[m33] = 0;
        return this;
    }

    public Matrix4f setPerspective(float width, float height, float near, float far, float fovY){
        return setPerspective(width / height, near, far, fovY);
    }


    /* To Look At */

    public Matrix4f setLookAt(float rightX, float rightY, float rightZ, float upX, float upY, float upZ, float forwardX, float forwardY, float forwardZ){
        val[m00] = rightX;   val[m10] = rightY;   val[m20] = rightZ;   val[m30] = 0;
        val[m01] = upX;      val[m11] = upY;      val[m21] = upZ;      val[m31] = 0;
        val[m02] = forwardX; val[m12] = forwardY; val[m22] = forwardZ; val[m32] = 0;
        val[m03] = 0;        val[m13] = 0;        val[m23] = 0;        val[m33] = 1;
        return this;
    }

    public Matrix4f setLookAt(Vec3f right, Vec3f up, Vec3f forward){
        return setLookAt(right.x, right.y, right.z, up.x, up.y, up.z, forward.x, forward.y, forward.z);
    }

    public Matrix4f setLookAt(float posX, float posY, float posZ, Vec3f right, Vec3f up, Vec3f forward){
        return setLookAt(right, up, forward)
            .translate(-posX, -posY, -posZ);
    }

    public Matrix4f setLookAt(Vec3f pos, Vec3f right, Vec3f up, Vec3f forward){
        return setLookAt(pos.x, pos.y, pos.z, right, up, forward);
    }


    public Matrix4f setLookAt(Vec3f direction){
        tmp_camRight.crs(direction, UP).nor();
        tmp_camUp.crs(tmp_camRight, direction).nor();
        return setLookAt(tmp_camRight, tmp_camUp, direction);
    }

    public Matrix4f setLookAt(float posX, float posY, float posZ, Vec3f direction){
        return setLookAt(direction)
            .translate(-posX, -posY, -posZ);
    }

    public Matrix4f setLookAt(Vec3f position, Vec3f direction){
        return setLookAt(position.x, position.y, position.z, direction);
    }


    /* Culling */

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


    /* Translate */

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


    /* Set Translated */

    public Matrix4f setTranslate(float x, float y, float z){
        identity();
        val[m30] = x;
        val[m31] = y;
        val[m32] = z;
        return this;
    }

    public Matrix4f setTranslate(float x, float y){
        identity();
        val[m30] = x;
        val[m31] = y;
        return this;
    }

    public Matrix4f setTranslate(Vec2f vec2){
        return setTranslate(vec2.x, vec2.y);
    }

    public Matrix4f setTranslate(Vec2d vec2){
        return setTranslate((float) vec2.x, (float) vec2.y);
    }

    public Matrix4f setTranslate(Vec2i vec2){
        return setTranslate(vec2.x, vec2.y);
    }

    public Matrix4f setTranslate(Vec3f vec3){
        return setTranslate(vec3.x, vec3.y, vec3.z);
    }

    public Matrix4f setTranslate(Vec3d vec3){
        return setTranslate((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public Matrix4f setTranslate(Vec3i vec3){
        return setTranslate(vec3.x, vec3.y, vec3.z);
    }


    /* Scale */

    public Matrix4f scale(float x, float y, float z){
        identity();
        val[m00] *= x;
        val[m11] *= y;
        val[m22] *= z;
        return this;
    }

    public Matrix4f scale(float x, float y){
        identity();
        val[m00] *= x;
        val[m11] *= y;
        return this;
    }

    public Matrix4f scale(float scale){
        return scale(scale, scale, scale);
    }

    public Matrix4f scale(double scale){
        return scale((float) scale);
    }

    public Matrix4f scale(Vec3f vec3){
        return scale(vec3.x, vec3.y, vec3.z);
    }

    public Matrix4f scale(Vec3d vec3){
        return scale((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public Matrix4f scale(Vec3i vec3){
        return scale(vec3.x, vec3.y, vec3.z);
    }

    public Matrix4f scale(Vec2f vec2){
        return scale(vec2.x, vec2.y);
    }

    public Matrix4f scale(Vec2d vec2){
        return scale((float) vec2.x, (float) vec2.y);
    }

    public Matrix4f scale(Vec2i vec2){
        return scale(vec2.x, vec2.y);
    }


    /* Set Scale */

    public Matrix4f setScale(float x, float y, float z){
        identity();
        val[m00] = x;
        val[m11] = y;
        val[m22] = z;
        return this;
    }

    public Matrix4f setScale(float x, float y){
        identity();
        val[m00] = x;
        val[m11] = y;
        return this;
    }

    public Matrix4f setScale(float scale){
        return setScale(scale, scale, scale);
    }

    public Matrix4f setScale(double scale){
        return setScale((float) scale);
    }

    public Matrix4f setScale(Vec3f vec3){
        return setScale(vec3.x, vec3.y, vec3.z);
    }

    public Matrix4f setScale(Vec3d vec3){
        return setScale((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    public Matrix4f setScale(Vec3i vec3){
        return setScale(vec3.x, vec3.y, vec3.z);
    }

    public Matrix4f setScale(Vec2f vec2){
        return setScale(vec2.x, vec2.y);
    }

    public Matrix4f setScale(Vec2d vec2){
        return setScale((float) vec2.x, (float) vec2.y);
    }

    public Matrix4f setScale(Vec2i vec2){
        return setScale(vec2.x, vec2.y);
    }


    /* Rotate */

    public Matrix4 rotate(Quaternion rotation){
        final float x = rotation.x;
        final float y = rotation.y;
        final float z = rotation.z;
        final float w = rotation.w;

        final float xx = x * x;
        final float xy = x * y;
        final float xz = x * z;
        final float xw = x * w;
        final float yy = y * y;
        final float yz = y * z;
        final float yw = y * w;
        final float zz = z * z;
        final float zw = z * w;

        final float r00 = 1 - (yy + zz) * 2;
        final float r01 =     (xy - zw) * 2;
        final float r02 =     (xz + yw) * 2;
        final float r10 =     (xy + zw) * 2;
        final float r11 = 1 - (xx + zz) * 2;
        final float r12 =     (yz - xw) * 2;
        final float r20 =     (xz - yw) * 2;
        final float r21 =     (yz + xw) * 2;
        final float r22 = 1 - (xx + yy) * 2;

        final float M00 =  val[m00] * r00  +  val[m10] * r10  +  val[m20] * r20;
        final float M01 =  val[m00] * r01  +  val[m10] * r11  +  val[m20] * r21;
        final float M02 =  val[m00] * r02  +  val[m10] * r12  +  val[m20] * r22;
        final float M10 =  val[m01] * r00  +  val[m11] * r10  +  val[m21] * r20;
        final float M11 =  val[m01] * r01  +  val[m11] * r11  +  val[m21] * r21;
        final float M12 =  val[m01] * r02  +  val[m11] * r12  +  val[m21] * r22;
        final float M20 =  val[m02] * r00  +  val[m12] * r10  +  val[m22] * r20;
        final float M21 =  val[m02] * r01  +  val[m12] * r11  +  val[m22] * r21;
        final float M22 =  val[m02] * r02  +  val[m12] * r12  +  val[m22] * r22;
        final float M30 =  val[m03] * r00  +  val[m13] * r10  +  val[m23] * r20;
        final float M31 =  val[m03] * r01  +  val[m13] * r11  +  val[m23] * r21;
        final float M32 =  val[m03] * r02  +  val[m13] * r12  +  val[m23] * r22;

        val[m00] = M00;  val[m10] = M01;  val[m20] = M02;
        val[m01] = M10;  val[m11] = M11;  val[m21] = M12;
        val[m02] = M20;  val[m12] = M21;  val[m22] = M22;
        val[m03] = M30;  val[m13] = M31;  val[m23] = M32;
        return this;
    }


    /* Set Rotation */

    public Matrix4f setRotationX(double degrees){
        identity();
        final float cos = Maths.cosDeg(degrees);
        final float sin = Maths.sinDeg(degrees);
        val[m11] = cos;
        val[m21] = -sin;
        val[m12] = sin;
        val[m22] = cos;
        return this;
    }

    public Matrix4f setRotationY(double degrees){
        identity();
        final float cos = Maths.cosDeg(degrees);
        final float sin = Maths.sinDeg(degrees);
        val[m00] = cos;
        val[m20] = sin;
        val[m02] = -sin;
        val[m22] = cos;
        return this;
    }

    public Matrix4f setRotationZ(double degrees){
        identity();
        final float cos = Maths.cosDeg(degrees);
        final float sin = Maths.sinDeg(degrees);
        val[m00] = cos;
        val[m10] = -sin;
        val[m01] = sin;
        val[m11] = cos;
        return this;
    }

    public Matrix4f setRotation(double yaw, double pitch, double roll){
        setRotationZ(roll).mul(
            new Matrix4f().setRotationX(pitch).mul(
                new Matrix4f().setRotationY(yaw)
            )
        );
        return this;
    }

    public Matrix4f setRotation(EulerAngles angles){
        return setRotation(angles.yaw, angles.pitch, angles.roll);
    }


    /* Quaternion */

    public Matrix4 setQuaternion(float qx, float qy, float qz, float qw){
        final float qx2 = qx * 2;
        final float qy2 = qy * 2;
        final float qz2 = qz * 2;

        final float qwx = qw * qx2;
        final float qxx = qx * qx2;

        final float qwy = qw * qy2;
        final float qxy = qx * qy2;
        final float qyy = qy * qy2;

        final float qwz = qw * qz2;
        final float qxz = qx * qz2;
        final float qyz = qy * qz2;
        final float qzz = qz * qz2;

        val[m00] = 1 - (qyy + qzz);
        val[m10] = qxy - qwz;
        val[m20] = qxz + qwy;
        val[m30] = 0;

        val[m01] = qxy + qwz;
        val[m11] = 1 - (qxx + qzz);
        val[m21] = qyz - qwx;
        val[m31] = 0;

        val[m02] = qxz - qwy;
        val[m12] = qyz + qwx;
        val[m22] = 1 - (qxx + qyy);
        val[m32] = 0;

        val[m03] = 0;
        val[m13] = 0;
        val[m23] = 0;
        val[m33] = 1;

        return this;
    }

    public Matrix4 setQuaternion(Quaternion quaternion){
        return setQuaternion(quaternion.x, quaternion.y, quaternion.z, quaternion.w);
    }

    public Matrix4 setQuaternion(float x, float y, float z, float quaternionX, float quaternionY, float quaternionZ, float quaternionW){
        final float qx2 = quaternionX * 2;
        final float qy2 = quaternionY * 2;
        final float qz2 = quaternionZ * 2;

        final float qwx = quaternionW * qx2;
        final float qxx = quaternionX * qx2;

        final float qwy = quaternionW * qy2;
        final float qxy = quaternionX * qy2;
        final float qyy = quaternionY * qy2;

        final float qwz = quaternionW * qz2;
        final float qxz = quaternionX * qz2;
        final float qyz = quaternionY * qz2;
        final float qzz = quaternionZ * qz2;

        val[m00] = 1 - (qyy + qzz);
        val[m10] = qxy - qwz;
        val[m20] = qxz + qwy;
        val[m30] = x;

        val[m01] = qxy + qwz;
        val[m11] = 1 - (qxx + qzz);
        val[m21] = qyz - qwx;
        val[m31] = y;

        val[m02] = qxz - qwy;
        val[m12] = qyz + qwx;
        val[m22] = 1 - (qxx + qyy);
        val[m32] = z;

        val[m03] = 0;
        val[m13] = 0;
        val[m23] = 0;
        val[m33] = 1;

        return this;
    }

    public Matrix4 setQuaternion(Vec3f position, float qx, float qy, float qz, float qw){
        return setQuaternion(position.x, position.y, position.z, qx, qy, qz, qw);
    }

    public Matrix4 setQuaternion(float x, float y, float z, Quaternion quaternion){
        return setQuaternion(x, y, z, quaternion.x, quaternion.y, quaternion.z, quaternion.w);
    }

    public Matrix4 setQuaternion(Vec3f position, Quaternion quaternion){
        return setQuaternion(position.x, position.y, position.z, quaternion.x, quaternion.y, quaternion.z, quaternion.w);
    }


    public Matrix4 setQuaternion(float x, float y, float z, float scaleX, float scaleY, float scaleZ, float quaternionX, float quaternionY, float quaternionZ, float quaternionW){
        final float qx2 = quaternionX * 2;
        final float qy2 = quaternionY * 2;
        final float qz2 = quaternionZ * 2;

        final float qwx = quaternionW * qx2;
        final float qxx = quaternionX * qx2;

        final float qwy = quaternionW * qy2;
        final float qxy = quaternionX * qy2;
        final float qyy = quaternionY * qy2;

        final float qwz = quaternionW * qz2;
        final float qxz = quaternionX * qz2;
        final float qyz = quaternionY * qz2;
        final float qzz = quaternionZ * qz2;

        val[m00] = scaleX * (1 - (qyy + qzz));
        val[m10] = scaleY *      (qxy - qwz);
        val[m20] = scaleZ *      (qxz + qwy);
        val[m30] = x;

        val[m01] = scaleX *      (qxy + qwz);
        val[m11] = scaleY * (1 - (qxx + qzz));
        val[m21] = scaleZ *      (qyz - qwx);
        val[m31] = y;

        val[m02] = scaleX *      (qxz - qwy);
        val[m12] = scaleY *      (qyz + qwx);
        val[m22] = scaleZ * (1 - (qxx + qyy));
        val[m32] = z;

        val[m03] = 0;
        val[m13] = 0;
        val[m23] = 0;
        val[m33] = 1;

        return this;
    }

    public Matrix4 setQuaternion(Vec3f position, float scaleX, float scaleY, float scaleZ, float quaternionX, float quaternionY, float quaternionZ, float quaternionW){
        return setQuaternion(position.x, position.y, position.z, scaleX, scaleY, scaleZ, quaternionX, quaternionY, quaternionZ, quaternionW);
    }

    public Matrix4 setQuaternion(float x, float y, float z, Vec3f scale, float quaternionX, float quaternionY, float quaternionZ, float quaternionW){
        return setQuaternion(x, y, z, scale.x, scale.y, scale.z, quaternionX, quaternionY, quaternionZ, quaternionW);
    }

    public Matrix4 setQuaternion(Vec3f position, Vec3f scale, float quaternionX, float quaternionY, float quaternionZ, float quaternionW){
        return setQuaternion(position.x, position.y, position.z, scale.x, scale.y, scale.z, quaternionX, quaternionY, quaternionZ, quaternionW);
    }

    public Matrix4 setQuaternion(float x, float y, float z, float scaleX, float scaleY, float scaleZ, Quaternion quaternion){
        return setQuaternion(x, y, z, scaleX, scaleY, scaleZ, quaternion.x, quaternion.y, quaternion.z, quaternion.w);
    }

    public Matrix4 setQuaternion(Vec3f position, float scaleX, float scaleY, float scaleZ, Quaternion quaternion){
        return setQuaternion(position.x, position.y, position.z, scaleX, scaleY, scaleZ, quaternion.x, quaternion.y, quaternion.z, quaternion.w);
    }

    public Matrix4 setQuaternion(float x, float y, float z, Vec3f scale, Quaternion quaternion){
        return setQuaternion(x, y, z, scale.x, scale.y, scale.z, quaternion.x, quaternion.y, quaternion.z, quaternion.w);
    }

    public Matrix4 setQuaternion(Vec3f position, Vec3f scale, Quaternion quaternion){
        return setQuaternion(position.x, position.y, position.z, scale.x, scale.y, scale.z, quaternion.x, quaternion.y, quaternion.z, quaternion.w);
    }


    /* Linear Interpolation */

    public Matrix4f lerp(Matrix4f matrix, float t){
        final float ti = 1 - t;
        for(int i = 0; i < 16; i++)
            val[i] = val[i] * ti  +  matrix.val[i] * t;
        return this;
    }


    /* Copy */

    public Matrix4f copy(){
        return new Matrix4f(this);
    }


    /* Multiply */

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