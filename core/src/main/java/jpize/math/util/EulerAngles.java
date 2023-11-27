package jpize.math.util;

import jpize.math.Mathc;
import jpize.math.Maths;
import jpize.math.vecmath.matrix.Matrix4f;
import jpize.math.vecmath.vector.Vec3d;
import jpize.math.vecmath.vector.Vec3f;

import java.util.Objects;

public class EulerAngles{

    public float yaw, pitch, roll;
    
    public EulerAngles(){ }

    public EulerAngles(float yaw, float pitch){
        set(yaw, pitch);
    }

    public EulerAngles(float yaw, float pitch, float roll){
        set(yaw, pitch, roll);
    }
    
    public EulerAngles(EulerAngles eulerAngles){
        set(eulerAngles);
    }
    

    public void constrain(){
        if(yaw >= 360)
            yaw -= 360;
        else if(yaw <= -360)
            yaw += 360;
    }
    
    public void clampPitch90(){
        pitch = Maths.clamp(pitch, -90, 90);
    }

    public Vec3f getDirection(){
        final float cosPitch = Maths.cosDeg(pitch);
        return new Vec3f(
            cosPitch * Maths.cosDeg(yaw),
            Maths.sinDeg(pitch),
            cosPitch * Maths.sinDeg(yaw)
        );
    }
    
    public Vec3f getDirectionHorizontal(){
        return new Vec3f(Maths.cosDeg(yaw), 0, Maths.sinDeg(yaw));
    }

    public EulerAngles setDirection(double x, double y, double z){
        yaw = -Mathc.atan2(x, z) * Maths.ToDeg + 90;
        pitch = Mathc.atan2(y, Math.sqrt(x * x + z * z)) * Maths.ToDeg;
        
        return this;
    }
    
    public EulerAngles setDirection(Vec3d dir){
        return setDirection(dir.x, dir.y, dir.z);
    }

    public EulerAngles setDirection(Vec3f dir){
        return setDirection(dir.x, dir.y, dir.z);
    }
    
    
    public Matrix4f toMatrix(){
        return new Matrix4f()
            //.toRotatedX(roll)
            //.mul(new Matrix4f()
            .toRotatedY(yaw) //)
            .mul(new Matrix4f().toRotatedZ(pitch));
    }
    

    public EulerAngles set(EulerAngles eulerAngles){
        yaw = eulerAngles.yaw;
        pitch = eulerAngles.pitch;
        roll = eulerAngles.roll;
        
        return this;
    }

    public EulerAngles set(float yaw, float pitch, float roll){
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
        
        return this;
    }

    public EulerAngles set(float yaw, float pitch){
        this.yaw = yaw;
        this.pitch = pitch;
        
        return this;
    }
    
    
    public EulerAngles add(float yaw, float pitch, float roll){
        this.yaw += yaw;
        this.pitch += pitch;
        this.roll += roll;
        
        return this;
    }
    
    public EulerAngles add(float yaw, float pitch){
        this.yaw += yaw;
        this.pitch += pitch;
        
        return this;
    }
    
    
    public EulerAngles lerp(EulerAngles start, EulerAngles end, float t){
        yaw = Maths.lerp(start.yaw, end.yaw, t);
        pitch = Maths.lerp(start.pitch, end.pitch, t);
        roll = Maths.lerp(start.roll, end.roll, t);
        
        return this;
    }
    
    
    public EulerAngles copy(){
        return new EulerAngles(this);
    }


    @Override
    public String toString(){
        return yaw + ", " + pitch + ", " + roll;
    }


    @Override
    public boolean equals(Object object){
        if(this == object)
            return true;
        if(object == null || getClass() != object.getClass())
            return false;

        final EulerAngles eulerAngles = (EulerAngles) object;
        return this.yaw == eulerAngles.yaw && this.pitch == eulerAngles.pitch && this.roll == eulerAngles.roll;
    }

    @Override
    public int hashCode(){
        return Objects.hash(yaw, pitch, roll);
    }

}
