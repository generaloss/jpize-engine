package jpize.util.math.util;

import jpize.util.math.Mathc;
import jpize.util.math.Maths;
import jpize.util.math.vecmath.vector.Vec2f;
import jpize.util.math.vecmath.vector.Vec3d;
import jpize.util.math.vecmath.vector.Vec3f;

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
    
    public void clampPitch(){
        pitch = Maths.clamp(pitch, -90, 90);
    }

    public Vec3f getDir(){
        return dirOf(yaw, pitch);
    }
    
    public Vec3f getDirHorizontal(){
        return new Vec3f(Maths.cosDeg(yaw), 0, Maths.sinDeg(yaw));
    }

    public EulerAngles setDir(double x, double y, double z){
        yaw = yawOfDir(x, z) * Maths.toDeg;
        pitch = pitchOfDir(x, y, z) * Maths.toDeg;
        return this;
    }
    
    public EulerAngles setDir(Vec3d dir){
        return setDir(dir.x, dir.y, dir.z);
    }

    public EulerAngles setDir(Vec3f dir){
        return setDir(dir.x, dir.y, dir.z);
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


    public static EulerAngles ofDir(float x, float y, float z){
        final float yaw = yawOfDir(x, z) * Maths.toDeg;
        final float pitch = pitchOfDir(x, y, z) * Maths.toDeg;
        return new EulerAngles(yaw, pitch);
    }

    public static float yawOfDir(double x, double z){
        return Mathc.atan2(-x, z);
    }

    public static float pitchOfDir(double x, double y, double z){
        return Mathc.atan2(y, Vec2f.len(x, z));
    }


    public static Vec3f dirOf(double yaw, double pitch){
        final float pitchCos = Maths.cosDeg(pitch);
        return new Vec3f(
            -Maths.sinDeg(yaw) * pitchCos,
            Maths.sinDeg(pitch),
            Maths.cosDeg(yaw) * pitchCos
        );
    }

    public static Vec3f dirOf(double yaw){
        return new Vec3f(Maths.cosDeg(yaw), 0, Maths.sinDeg(yaw));
    }

}
