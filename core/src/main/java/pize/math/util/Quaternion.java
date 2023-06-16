package pize.math.util;

import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.matrix.Matrix4f;

public class Quaternion{

    public float x, y, z, w;


    public Quaternion(){
        this.w = 1.0F;
    }

    public Quaternion(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }


    public float x(){
        return this.x;
    }

    public float y(){
        return this.y;
    }

    public float z(){
        return this.z;
    }

    public float w(){
        return this.w;
    }

    public float len(){
        return (float) Math.sqrt(x * x + y * y + z * z + w * w);
    }

    public Quaternion setEulerAngles(double yaw, double pitch, double roll){
        return setEulerAnglesRad(Maths.toRad * yaw, Maths.toRad * pitch, Maths.toRad * roll);
    }
    
    public Quaternion setEulerAngles(EulerAngles eulerAngles){
        return setEulerAngles(eulerAngles.yaw, eulerAngles.pitch, eulerAngles.roll);
    }

    public Quaternion setEulerAnglesRad(double yaw, double pitch, double roll){
        final double hYaw = yaw * 0.5F;
        final float hYawSin = (float) Math.sin(hYaw);
        final float hYawCos = (float) Math.cos(hYaw);
        
        final double hPitch = pitch * 0.5F;
        final float hPitchSin = (float) Math.sin(hPitch);
        final float hPitchCos = (float) Math.cos(hPitch);
        
        final double hRoll = roll * 0.5F;
        final float hRollSin = (float) Math.sin(hRoll);
        final float hRollCos = (float) Math.cos(hRoll);
        
        final float chy_shp = hYawCos * hPitchSin;
        final float shy_chp = hYawSin * hPitchCos;
        final float chy_chp = hYawCos * hPitchCos;
        final float shy_shp = hYawSin * hPitchSin;

        x = (chy_shp * hRollCos) + (shy_chp * hRollSin); // cos(yaw/2) * sin(pitch/2) * cos(roll/2) + sin(yaw/2) * cos(pitch/2) * sin(roll/2)
        y = (shy_chp * hRollCos) - (chy_shp * hRollSin); // sin(yaw/2) * cos(pitch/2) * cos(roll/2) - cos(yaw/2) * sin(pitch/2) * sin(roll/2)
        z = (chy_chp * hRollSin) - (shy_shp * hRollCos); // cos(yaw/2) * cos(pitch/2) * sin(roll/2) - sin(yaw/2) * sin(pitch/2) * cos(roll/2)
        w = (chy_chp * hRollCos) + (shy_shp * hRollSin); // cos(yaw/2) * cos(pitch/2) * cos(roll/2) + sin(yaw/2) * sin(pitch/2) * sin(roll/2)
        return this;
    }

    public int getGimbalPole(){
        final float t = y * x + z * w;
        return t > 0.499F ? 1 : (t < -0.499F ? -1 : 0);
    }

    public float getRollRad(){
        final int pole = getGimbalPole();
        return pole == 0 ? (float) Math.atan2(2F * (w * z + y * x), 1F - 2F * (x * x + z * z)) : (float) pole * 2F * (float) Math.atan2(y, w);
    }

    public float getRoll(){
        return getRollRad() * Maths.toDeg;
    }

    public float getPitchRad(){
        final int pole = getGimbalPole();
        return pole == 0 ? (float) Math.asin(Maths.clamp(2F * (w * x - z * y), -1F, 1F)) : (float) pole * Maths.PI * 0.5F;
    }

    public float getPitch(){
        return getPitchRad() * Maths.toDeg;
    }

    public float getYawRad(){
        return getGimbalPole() == 0 ? (float) Math.atan2(2F * (y * w + x * z), 1F - 2F * (y * y + x * x)) : 0F;
    }

    public float getYaw(){
        return getYawRad() * Maths.toDeg;
    }

    public static float len2(final float x, final float y, final float z, final float w){
        return x * x + y * y + z * z + w * w;
    }

    public float len2(){
        return x * x + y * y + z * z + w * w;
    }

    public Quaternion nor(){
        float len = len2();
        if(len != 0.f && len != 1F){
            len = Mathc.sqrt(len);
            w /= len;
            x /= len;
            y /= len;
            z /= len;
        }
        return this;
    }

    public Quaternion conjugate(){
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Quaternion mul(final Quaternion other){
        final float newX = this.w * other.x + this.x * other.w + this.y * other.z - this.z * other.y;
        final float newY = this.w * other.y + this.y * other.w + this.z * other.x - this.x * other.z;
        final float newZ = this.w * other.z + this.z * other.w + this.x * other.y - this.y * other.x;
        final float newW = this.w * other.w - this.x * other.x - this.y * other.y - this.z * other.z;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        this.w = newW;
        return this;
    }

    public Quaternion mul(final float x, final float y, final float z, final float w){
        final float newX = this.w * x + this.x * w + this.y * z - this.z * y;
        final float newY = this.w * y + this.y * w + this.z * x - this.x * z;
        final float newZ = this.w * z + this.z * w + this.x * y - this.y * x;
        final float newW = this.w * w - this.x * x - this.y * y - this.z * z;
        this.x = newX;
        this.y = newY;
        this.z = newZ;
        this.w = newW;
        return this;
    }

    public Quaternion add(Quaternion quaternion){
        this.x += quaternion.x;
        this.y += quaternion.y;
        this.z += quaternion.z;
        this.w += quaternion.w;
        return this;
    }

    public Quaternion add(float qx, float qy, float qz, float qw){
        this.x += qx;
        this.y += qy;
        this.z += qz;
        this.w += qw;
        return this;
    }

    public Matrix4f toMatrix(){
        final float xx = x * x;
        final float xy = x * y;
        final float xz = x * z;
        final float xw = x * w;
        final float yy = y * y;
        final float yz = y * z;
        final float yw = y * w;
        final float zz = z * z;
        final float zw = z * w;

        final Matrix4f result = new Matrix4f();

        result.val[Matrix4f.m00] = 1 - 2 * (yy + zz);
        result.val[Matrix4f.m01] = 2 * (xy - zw);
        result.val[Matrix4f.m02] = 2 * (xz + yw);
        result.val[Matrix4f.m03] = 0;
        result.val[Matrix4f.m10] = 2 * (xy + zw);
        result.val[Matrix4f.m11] = 1 - 2 * (xx + zz);
        result.val[Matrix4f.m12] = 2 * (yz - xw);
        result.val[Matrix4f.m13] = 0;
        result.val[Matrix4f.m20] = 2 * (xz - yw);
        result.val[Matrix4f.m21] = 2 * (yz + xw);
        result.val[Matrix4f.m22] = 1 - 2 * (xx + yy);
        result.val[Matrix4f.m23] = 0;
        result.val[Matrix4f.m30] = 0;
        result.val[Matrix4f.m31] = 0;
        result.val[Matrix4f.m32] = 0;
        result.val[Matrix4f.m33] = 1;

        return result;
    }

    @Override
    public String toString(){
        return "[" + x + "|" + y + "|" + z + "|" + w + "]";
    }

}
