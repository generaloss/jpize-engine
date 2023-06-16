package pize.math.util;

import pize.math.Maths;
import pize.math.vecmath.matrix.Matrix4f;
import pize.math.vecmath.vector.Vec3d;
import pize.math.vecmath.vector.Vec3f;

public class EulerAngle implements Cloneable{

    public double pitch, yaw, roll;
    
    public EulerAngle(){ }

    public EulerAngle(double yaw, double pitch){
        set(yaw, pitch);
    }

    public EulerAngle(double yaw, double pitch, double roll){
        set(yaw, pitch, roll);
    }
    
    public EulerAngle(EulerAngle eulerAngle){
        set(eulerAngle);
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

    public EulerAngle setDirection(double x, double y, double z){
        yaw = -Math.atan2(x, z) * Maths.toDeg + 90;
        pitch = Math.atan2(y, Math.sqrt(x * x + z * z)) * Maths.toDeg;
        
        return this;
    }
    
    public EulerAngle setDirection(Vec3d dir){
        return setDirection(dir.x, dir.y, dir.z);
    }

    public EulerAngle setDirection(Vec3f dir){
        return setDirection(dir.x, dir.y, dir.z);
    }
    
    
    public Matrix4f toMatrix(){
        return new Matrix4f()
            //.toRotatedX(roll)
            //.mul(new Matrix4f()
            .toRotatedY(yaw) //)
            .mul(new Matrix4f().toRotatedZ(pitch));
    }
    

    public void set(EulerAngle eulerAngle){
        yaw = eulerAngle.yaw;
        pitch = eulerAngle.pitch;
        roll = eulerAngle.roll;
    }

    public void set(double yaw, double pitch, double roll){
        this.yaw = yaw;
        this.pitch = pitch;
        this.roll = roll;
    }

    public void set(double yaw, double pitch){
        this.yaw = yaw;
        this.pitch = pitch;
    }
    
    
    @Override
    public EulerAngle clone(){
        return new EulerAngle(this);
    }

}
