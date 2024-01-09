package jpize.graphics.camera.ctrl;

import jpize.Jpize;
import jpize.math.Maths;
import jpize.math.util.EulerAngles;
import jpize.math.vecmath.vector.Vec2i;
import jpize.sdl.Sdl;

public class EulerRotCtrl{

    private static final float SPEED_MULTIPLIER = 10F / Sdl.getDisplayVDPI();

    private EulerAngles target;
    private float yaw, pitch;
    private boolean lockNextFrame;

    private boolean mirrorHorizontal, mirrorVertical;
    private float speed, smoothness;
    private boolean clampPitch;
    private boolean enabled;
    private final Vec2i mouseRelState;

    public EulerRotCtrl(EulerAngles target, boolean enabled){
        System.out.println(SPEED_MULTIPLIER);
        this.target = target;
        this.lockNextFrame = true;
        this.speed = 1F;
        this.smoothness = 0F;
        this.clampPitch = true;
        setEnabled(enabled);
        this.mouseRelState = new Vec2i();
    }

    public EulerRotCtrl(EulerAngles target){
        this(target, true);
    }


    public EulerAngles getTarget(){
        return target;
    }

    public void setTarget(EulerAngles target){
        this.target = target;
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
        Jpize.input().getRelativeState(mouseRelState);

        if(Jpize.window().isInputFocus() && enabled){

            if(!lockNextFrame){
                final float invSmoothness = (1 - smoothness);

                yaw   -= SPEED_MULTIPLIER * speed * Maths.sigFlag(mirrorHorizontal) * mouseRelState.x;
                pitch -= SPEED_MULTIPLIER * speed * Maths.sigFlag(mirrorVertical  ) * mouseRelState.y;
                if(clampPitch)
                    pitch = Maths.clamp(pitch, -90, 90);

                target.yaw   += (  yaw - target.yaw  ) * invSmoothness;
                target.pitch += (pitch - target.pitch) * invSmoothness;
                if(clampPitch)
                    target.clampPitch();
            }
            lockNextFrame = false;
        }
    }


    public boolean isMirrorHorizontal(){
        return mirrorHorizontal;
    }

    public boolean isMirrorVertical(){
        return mirrorVertical;
    }

    public void setMirrorHorizontal(boolean mirrorHorizontal){
        this.mirrorHorizontal = mirrorHorizontal;
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


    public boolean isClampPitch(){
        return clampPitch;
    }

    public void setClampPitch(boolean clampPitch){
        this.clampPitch = clampPitch;
    }


    public void lockNextFrame(){
        lockNextFrame = true;
    }
    
}
