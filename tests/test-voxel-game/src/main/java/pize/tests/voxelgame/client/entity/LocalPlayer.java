package pize.tests.voxelgame.client.entity;

import pize.Pize;
import pize.math.Mathc;
import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3d;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.clientserver.level.Level;

public class LocalPlayer extends AbstractClientPlayer{
    
    public static float speed;
    
    
    private float gravity, timeToMaxJumpY, jumpMotion;
    private final Vec2f moveControl;
    private float jumpDownY, oldMotionY, fallHeight;
    
    public LocalPlayer(Level levelOF, String name){
        super(levelOF, name); // 3) random skin ID

        moveControl = new Vec2f();
        setJumpHeight(1.25F);
    }
    
    
    public void tick(){
        super.tick();
        
        // Horizontal move
        float reduce = 0.91F;
        float movementFactor = 0.34F * Pize.getDt();
        if(isSprinting())
            movementFactor *= 1.3;
        
        float dist = moveControl.len();
        if(dist > 0){
            moveControl.mul(movementFactor);
            moveControl.rotDeg(getRotation().yaw);
            
            getMotion().x += moveControl.x;
            getMotion().z += moveControl.y;
        }
        
        // Gravity
        if(!isOnGround())
            getMotion().y += gravity * (Pize.getDt() * Pize.getDt());
        
        // Fall height
        if(getMotion().y < 0 && oldMotionY >= 0)
            jumpDownY = getPosition().y;
        
        if(isOnGround() && jumpDownY != 0){
            fallHeight = jumpDownY - getPosition().y;
            jumpDownY = 0;
        }
        
        oldMotionY = (float) getMotion().y;
        
        // Move entity & Measure speed
        final Vec3f oldPosition = getPosition().clone();
        {
            final Vec3d collidedMotion = moveEntity(getMotion());
            getMotion().collidedAxesToZero(collidedMotion);
        }
        speed = ((Vec3f) oldPosition.sub(getPosition())).len() / Pize.getDt();
        
        // Reduce motion
        getMotion().mul(reduce, 1, reduce);
    }
    
    public void setJumpHeight(float height){
        timeToMaxJumpY = 0.27F * Mathc.sqrt(height);
        
        gravity = -2 * height / (timeToMaxJumpY * timeToMaxJumpY);
        jumpMotion = 2 * height / timeToMaxJumpY;
    }
    
    public float getFallHeight(){
        return fallHeight;
    }
    
    
    public void moveControl(float forward, float strafe){
        moveControl.set(forward, strafe);
    }
    
    public void jump(){
        if(isOnGround()){
            // Jump
            getMotion().y = jumpMotion * Pize.getDt();
            
            // Jump-boost
            if(isSprinting()){
                final Vec2f jumpBoost = new Vec2f(2 * Pize.getDt(), 0).rotDeg(getRotation().yaw);
                getMotion().x += jumpBoost.x;
                getMotion().z += jumpBoost.y;
            }
        }
    }
    
}
