package jpize.graphics.camera.controller;

import jpize.Jpize;
import jpize.math.util.EulerAngles;

public class Rotation3DController{
    
    private final EulerAngles rotation;
    private float mouseSensitivity;
    private boolean showMouse, nextFrameRotationLock;
    private float dAngX, dAngY;
    
    public Rotation3DController(){
        rotation = new EulerAngles();
        mouseSensitivity = 1;
        
        nextFrameRotationLock = true;
        Jpize.mouse().disable();
    }
    
    public void update(){
        if(Jpize.window().isFocused() && !showMouse){
            if(!nextFrameRotationLock){
                float x = Jpize.getX();
                float y = Jpize.getInvY();
                dAngX += (int) (Jpize.getWidth() / 2F) - x;
                dAngY += (int) (Jpize.getHeight() / 2F) - y;
                
                rotation.yaw += dAngX * 0.02F * mouseSensitivity;
                rotation.pitch += dAngY * 0.02F * mouseSensitivity;
                rotation.clampPitch90();
            
                dAngX *= 0.1F;
                dAngY *= 0.1F;
            }
            Jpize.mouse().toCenter();
            nextFrameRotationLock = false;
        }
    }
    
    
    public float getSensitivity(){
        return mouseSensitivity;
    }
    
    public void setSensitivity(float mouseSensitivity){
        this.mouseSensitivity = mouseSensitivity;
    }
    
    
    public boolean isMouseShow(){
        return showMouse;
    }
    
    public void showMouse(boolean showMouse){
        this.showMouse = showMouse;
        Jpize.mouse().setEnabled(showMouse);
    }
    
    public void toggleShowMouse(){
        showMouse(!showMouse);
    }
    
    public void lockNextFrame(){
        nextFrameRotationLock = true;
    }
    
    public EulerAngles getRotation(){
        return rotation;
    }
    
}
