package pize.tests.voxelgame.client.control;

import pize.Pize;
import pize.math.util.EulerAngles;

public class CameraRotationController{
    
    private final EulerAngles rotation;
    private float mouseSensitivity;
    private boolean showMouse, nextFrameRotationLock;
    private float dAngX, dAngY;
    
    public CameraRotationController(){
        rotation = new EulerAngles();
        mouseSensitivity = 1;
        
        nextFrameRotationLock = true;
        Pize.mouse().show(showMouse);
    }
    
    public void update(){
        if(Pize.window().isFocused() && !showMouse){
            if(!nextFrameRotationLock && Pize.mouse().inWindow()){
                float x = Pize.mouse().getX();
                float y = Pize.mouse().getY();
                dAngX += Pize.getWidth() / 2F - x;
                dAngY += Pize.getHeight() / 2F - y;
                
                rotation.yaw += dAngX * 0.02 * mouseSensitivity;
                rotation.pitch += dAngY * 0.02 * mouseSensitivity;
                rotation.constrain();
            
                dAngX *= 0.1;
                dAngY *= 0.1;
            }
            Pize.mouse().setPos(Pize.getWidth() / 2, Pize.getHeight() / 2);
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
        Pize.mouse().show(showMouse);
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
