package jpize.math.util;

import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.math.vecmath.matrix.Matrix4f;
import jpize.math.vecmath.vector.Vec3f;

public class Quaternion{

    public float x, y, z, w;
    
    public Quaternion(){
        this.w = 1;
    }
    
    public Quaternion(Quaternion quat){
        set(quat);
    }
    
    public Quaternion(float x, float y, float z, float w){
        set(x, y, z, w);
    }
    
    public Quaternion(Vec3f axis, float angle, boolean degrees){
        set(axis, angle, degrees);
    }
    
    public Quaternion(EulerAngles eulerAngles){
        set(eulerAngles);
    }
    
    
    public Quaternion set(Quaternion quat){
        return set(quat.x, quat.y, quat.z, quat.w);
    }
    
    public Quaternion set(float x, float y, float z, float w){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
        
        return this;
    }
    
    public Quaternion set(double x, double y, double z, double w){
        return set((float) x, (float) y, (float) z, (float) w);
    }
    
    public Quaternion set(Vec3f axis, double angleRad){
        final float sin = Mathc.sin(angleRad * 0.5);
        x = axis.x * sin;
        y = axis.y * sin;
        z = axis.z * sin;
        w = Mathc.cos(angleRad * 0.5);
        
        return this;
    }
    
    public Quaternion set(Vec3f axis, double angle, boolean degrees){
        return set(axis, (degrees ? (angle * Maths.toRad) : angle));
    }
    
    public Quaternion set(double yaw, double pitch, double roll){
        final double hy = 0.5 * yaw;
        final double hp = 0.5 * pitch;
        final double hr = 0.5 * roll;

        final float chr = Mathc.cos(hr);
        final float shr = Mathc.sin(hr);
        final float chp = Mathc.cos(hp);
        final float shp = Mathc.sin(hp);
        final float chy = Mathc.cos(hy);
        final float shy = Mathc.sin(hy);

        final float chy_shp = chy * shp;
        final float shy_chp = shy * chp;
        final float chy_chp = chy * chp;
        final float shy_shp = shy * shp;
    
        x =  chy_shp * chr  +  shy_chp * shr;
        y =  shy_chp * chr  -  chy_shp * shr;
        z =  chy_chp * shr  -  shy_shp * chr;
        w =  chy_chp * chr  +  shy_shp * shr;
        return this;
    }
    
    public Quaternion set(EulerAngles eulerAngles){
        return set(
            Maths.toRad * eulerAngles.yaw,
            Maths.toRad * eulerAngles.pitch,
            Maths.toRad * eulerAngles.roll
        );
    }

    
    public float len2(){
        return x * x + y * y + z * z + w * w;
    }
    
    public float len(){
        return Mathc.sqrt(len2());
    }
    
    public Quaternion nor(){
        float len = len2();
        
        if(len == 0 || len == 1)
            return this;
        
        len = Maths.invSqrt(len);
        w *= len;
        x *= len;
        y *= len;
        z *= len;
        
        return this;
    }

    public Quaternion conjugate(){
        x = -x;
        y = -y;
        z = -z;
        return this;
    }
    
    
    public int getGimbalPole(){
        final float t = x * y + z * w;
        return t > 0.499 ? (1) : (t < -0.499 ? -1 : 0);
    }
    
    public float getRollRad(){
        final int pole = getGimbalPole();
        return pole == 0 ?
            Mathc.atan2(2 * (w * z + y * x), 1 - 2 * (x * x + z * z)) :
            pole * 2 * Mathc.atan2(y, w);
    }
    
    public float getRoll(){
        return getRollRad() * Maths.toDeg;
    }
    
    public float getPitchRad(){
        final int pole = getGimbalPole();
        return pole == 0 ? Mathc.asin(Maths.clamp(2 * (w * x - z * y), -1, 1)) : pole * Maths.pi * 0.5F;
    }
    
    public float getPitch(){
        return getPitchRad() * Maths.toDeg;
    }
    
    public float getYawRad(){
        return getGimbalPole() == 0 ? Mathc.atan2(2 * (y * w + x * z), 1 - 2 * (y * y + x * x)) : 0;
    }
    
    public float getYaw(){
        return getYawRad() * Maths.toDeg;
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
        result.val[Matrix4f.m10] = 2 * (xy - zw);
        result.val[Matrix4f.m20] = 2 * (xz + yw);
        result.val[Matrix4f.m30] = 0;
        result.val[Matrix4f.m01] = 2 * (xy + zw);
        result.val[Matrix4f.m11] = 1 - 2 * (xx + zz);
        result.val[Matrix4f.m21] = 2 * (yz - xw);
        result.val[Matrix4f.m31] = 0;
        result.val[Matrix4f.m02] = 2 * (xz - yw);
        result.val[Matrix4f.m12] = 2 * (yz + xw);
        result.val[Matrix4f.m22] = 1 - 2 * (xx + yy);
        result.val[Matrix4f.m32] = 0;
        result.val[Matrix4f.m03] = 0;
        result.val[Matrix4f.m13] = 0;
        result.val[Matrix4f.m23] = 0;
        result.val[Matrix4f.m33] = 1;
        
        return result;
    }
    
    public Quaternion mul(Quaternion quat){
        this.x = this.w * quat.x + this.x * quat.w + this.y * quat.z - this.z * quat.y;
        this.y = this.w * quat.y - this.x * quat.z + this.y * quat.w + this.z * quat.x;
        this.z = this.w * quat.z + this.x * quat.y - this.y * quat.x + this.z * quat.w;
        this.w = this.w * quat.w - this.x * quat.x - this.y * quat.y - this.z * quat.z;
        
        return this;
    }

    public Quaternion mul(float x, float y, float z, float w){
        this.x = this.w * x + this.x * w + this.y * z - this.z * y;
        this.y = this.w * y + this.y * w + this.z * x - this.x * z;
        this.z = this.w * z + this.z * w + this.x * y - this.y * x;
        this.w = this.w * w - this.x * x - this.y * y - this.z * z;
        
        return this;
    }

    public Quaternion add(Quaternion quat){
        this.x += quat.x;
        this.y += quat.y;
        this.z += quat.z;
        this.w += quat.w;
        
        return this;
    }

    public Quaternion add(float x, float y, float z, float w){
        this.x += x;
        this.y += y;
        this.z += z;
        this.w += w;
        
        return this;
    }
    
    
    public Quaternion copy(){
        return new Quaternion(this);
    }
    
    @Override
    public String toString(){
        return x + ", " + y + ", " + z + ", " + w;
    }
    
    
    public static Quaternion fromRotation(float x, float y, float z){
        final Quaternion quaternion = new Quaternion();
        quaternion.mul(new Quaternion(Mathc.sin(x / 2), 0, 0, Mathc.cos(x / 2)));
        quaternion.mul(new Quaternion(0, Mathc.sin(y / 2), 0, Mathc.cos(y / 2)));
        quaternion.mul(new Quaternion(0, 0, Mathc.sin(z / 2), Mathc.cos(z / 2)));
        return quaternion;
    }

}
