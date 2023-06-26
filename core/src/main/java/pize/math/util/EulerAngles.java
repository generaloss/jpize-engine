package pize.math.util;

import pize.math.Maths;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec3d;
import pize.math.vecmath.vector.Vec3f;

public class EulerAngles implements Cloneable{

    public double pitch, yaw, roll;
    
    public EulerAngles(){ }

    public EulerAngles(double yaw, double pitch){
        set(yaw, pitch);
    }

    public EulerAngles(double yaw, double pitch, double roll){
        set(yaw, pitch, roll);
    }
    
    public EulerAngles(EulerAngles eulerAngles){
        set(eulerAngles);
    }
    

    public void constrain(){
        if(pitch > 90)
            pitch = 90;
        else if(pitch < -90)
            pitch = -90;

        if(yaw >= 360)
            yaw -= 360;
        else if(yaw <= -360)
            yaw += 360;
    }

    public Vec3f direction(){
        double yaw = Math.toRadians(this.yaw);
        double pitch = Math.toRadians(this.pitch);

        return new Vec3f(Math.cos(pitch) * Math.cos(yaw), Math.sin(pitch), Math.cos(pitch) * Math.sin(yaw));
    }

    public EulerAngles setDirection(double x, double y, double z){
        yaw = -Math.atan2(x, z) * Maths.toDeg + 90;
        pitch = Math.atan2(y, Math.sqrt(x * x + z * z)) * Maths.toDeg;
        
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

    public EulerAngles set(double yaw, double pitch, double roll){
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
        
        return this;
    }

    public EulerAngles set(double yaw, double pitch){
        this.yaw = yaw;
        this.pitch = pitch;
        
        return this;
    }
    
    
    public EulerAngles add(double yaw, double pitch, double roll){
        this.yaw += yaw;
        this.pitch += pitch;
        this.roll += roll;
        
        return this;
    }
    
    public EulerAngles add(double yaw, double pitch){
        this.yaw += yaw;
        this.pitch += pitch;
        
        return this;
    }
    
    
    @Override
    public EulerAngles clone(){
        return new EulerAngles(this);
    }

}
