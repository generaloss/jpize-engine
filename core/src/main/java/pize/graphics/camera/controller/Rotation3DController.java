package pize.graphics.camera.controller;

import pize.Jize;
import pize.math.util.EulerAngles;

public class Rotation3DController{
    
    private final EulerAngles rotation;
    private float mouseSensitivity;
    private boolean showMouse, nextFrameRotationLock;
    private float dAngX, dAngY;
    
    public Rotation3DController(){
        rotation = new EulerAngles();
        mouseSensitivity = 1;
        
        nextFrameRotationLock = true;
        Jize.mouse().setShow(showMouse);
    }
    
    public void update(){
        if(Jize.window().isFocused() && !showMouse){
            if(!nextFrameRotationLock && Jize.mouse().isInWindow()){
                float x = Jize.mouse().getX();
                float y = Jize.mouse().getY();
                dAngX += (int) (Jize.getWidth() / 2F) - x;
                dAngY += (int) (Jize.getHeight() / 2F) - y;
                
                rotation.yaw += dAngX * 0.02 * mouseSensitivity;
                rotation.pitch += dAngY * 0.02 * mouseSensitivity;
                rotation.clampPitch90();
            
                dAngX *= 0.1;
                dAngY *= 0.1;
            }
            Jize.mouse().setPos(Jize.getWidth() / 2, Jize.getHeight() / 2);
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
        Jize.mouse().setShow(showMouse);
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
