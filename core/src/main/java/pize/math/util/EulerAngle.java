package pize.math.util;

import pize.math.Maths;
import pize.math.vecmath.vector.Vec3d;
import pize.math.vecmath.vector.Vec3f;

public class EulerAngle{

    public double pitch, yaw, roll;


    public EulerAngle(){}

    public EulerAngle(double pitch, double yaw){
        this.pitch = pitch;
        this.yaw = yaw;
    }

    public EulerAngle(double yaw, double pitch, double roll){
        this.pitch = pitch;
        this.yaw = yaw;
        this.roll = roll;
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

    public void setDirection(double x, double y, double z){
        yaw = -Math.atan2(x, z) * Maths.toDeg + 90;
        pitch = Math.atan2(y, Math.sqrt(x * x + z * z)) * Maths.toDeg;
    }

    public void setDirection(Vec3d dir){
        setDirection(dir.x, dir.y, dir.z);
    }

    public void setDirection(Vec3f dir){
        setDirection(dir.x, dir.y, dir.z);
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

    public void rotate(EulerAngle eulerAngle){
        yaw += eulerAngle.yaw;
        pitch += eulerAngle.pitch;
        roll += eulerAngle.roll;
    }

    public void rotate(double yaw, double pitch, double roll){
        this.yaw += yaw;
        this.pitch += pitch;
        this.roll += roll;
    }

    public void rotate(double yaw, double pitch){
        this.yaw += yaw;
        this.pitch += pitch;
    }

    public void rotatePitch(double pitch){
        this.pitch += pitch;
    }

    public void rotateYaw(double yaw){
        this.yaw += yaw;
    }

    public void rotateRoll(double roll){
        this.roll += roll;
    }

}
