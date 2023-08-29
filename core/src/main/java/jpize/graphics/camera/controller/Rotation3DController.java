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
        Jpize.mouse().setShow(showMouse);
    }
    
    public void update(){
        if(Jpize.window().isFocused() && !showMouse){
            if(!nextFrameRotationLock && Jpize.mouse().isInWindow()){
                float x = Jpize.mouse().getX();
                float y = Jpize.mouse().getY();
                dAngX += (int) (Jpize.getWidth() / 2F) - x;
                dAngY += (int) (Jpize.getHeight() / 2F) - y;
                
                rotation.yaw += dAngX * 0.02 * mouseSensitivity;
                rotation.pitch += dAngY * 0.02 * mouseSensitivity;
                rotation.clampPitch90();
            
                dAngX *= 0.1;
                dAngY *= 0.1;
            }
            Jpize.mouse().setPos(Jpize.getWidth() / 2, Jpize.getHeight() / 2);
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
        Jpize.mouse().setShow(showMouse);
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
