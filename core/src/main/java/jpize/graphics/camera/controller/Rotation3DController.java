package jpize.graphics.camera.controller;

import jpize.Jpize;
import jpize.math.Maths;
import jpize.math.util.EulerAngles;

public class Rotation3DController{

    private final EulerAngles rotation;
    private float speed, smoothness, targetYaw, targetPitch;
    private boolean enabled;
    private boolean lockNextFrame;
    private boolean mirrorHorizontal, mirrorVertical;

    public Rotation3DController(boolean enabled){
        this.rotation = new EulerAngles();
        this.speed = 0.3F;
        this.smoothness = 0.4F;
        this.lockNextFrame = true;
        setEnabled(enabled);
    }

    public Rotation3DController(){
        this(true);
    }


    public boolean isEnabled(){
        return enabled;
    }

    public void setEnabled(boolean enabled){
        this.enabled = enabled;
        Jpize.input().setRelativeMode(enabled);
    }

    public void toggleEnabled(){
        setEnabled(!enabled);
    }

    
    public void update(){
        if(Jpize.window().isInputFocus() && enabled){
            if(!lockNextFrame){
                targetYaw -= Jpize.input().getDx() * speed * Maths.sigFlag(mirrorHorizontal);
                targetPitch -= Jpize.input().getDy() * speed * Maths.sigFlag(mirrorVertical);

                targetPitch = Maths.clamp(targetPitch, -90, 90);

                rotation.yaw += (targetYaw - rotation.yaw) * (1 - smoothness);
                rotation.pitch += (targetPitch - rotation.pitch) * (1 - smoothness);

                rotation.clampPitch90();
            }
            lockNextFrame = false;
        }
    }


    public boolean isMirrorHorizontal(){
        return mirrorHorizontal;
    }

    public void setMirrorHorizontal(boolean mirrorHorizontal){
        this.mirrorHorizontal = mirrorHorizontal;
    }

    public boolean isMirrorVertical(){
        return mirrorVertical;
    }

    public void setMirrorVertical(boolean mirrorVertical){
        this.mirrorVertical = mirrorVertical;
    }
    
    
    public float getSpeed(){
        return speed;
    }
    
    public void setSpeed(float speed){
        this.speed = speed;
    }


    public float getSmoothness(){
        return smoothness;
    }

    public void setSmoothness(float smoothness){
        this.smoothness = smoothness;
    }


    public void lockNextFrame(){
        lockNextFrame = true;
    }
    
    public EulerAngles getRotation(){
        return rotation;
    }
    
}
