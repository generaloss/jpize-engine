package pize.tests.voxelgame.client.entity;

import pize.Pize;
import pize.io.glfw.Key;
import pize.math.Mathc;
import pize.math.util.EulerAngles;
import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3d;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.base.level.Level;

public class LocalPlayer extends AbstractClientPlayer{
    
    public static float speed;
    
    
    private float gravity, timeToMaxJumpY, jumpMotion;
    private final Vec2f moveControl;
    private float jumpDownY, oldMotionY, fallHeight;
    
    private final Vec3f oldPos;
    private final EulerAngles oldRot;
    
    public short holdBlock = Block.STONE.getDefaultState();
    
    public LocalPlayer(Level levelOF, String name){
        super(levelOF, name);

        moveControl = new Vec2f();
        setJumpHeight(1.25F);
        
        oldPos = new Vec3f();
        oldRot = new EulerAngles();
    }
    
    
    public boolean isPosOrRotChanged(){
        final boolean changed = !oldPos.equals(getPosition()) || !oldRot.equals(getRotation());
        
        oldPos.set(getPosition());
        oldRot.set(getRotation());
        
        return changed;
    }
    
    
    public void tick(){
        super.tick();
        
        final float deltaTime = Pize.getDt();
        
        // Horizontal move
        float reduce = 0.91F;
        float speed = 0.34F * Pize.getDt();
        if(isSneaking() && !isFlying())
            speed *= 0.5;
        if(isSprinting())
            speed *= 1.3;
        if(isFlying())
            speed *= 10;
        
        float dist = moveControl.nor().len();
        if(dist > 0){
            moveControl.mul(speed);
            moveControl.rotDeg(getRotation().yaw);
            
            getMotion().x += moveControl.x;
            getMotion().z += moveControl.y;
        }
        
        // Gravity
        if(!isOnGround() && !isFlying())
            getMotion().y += gravity * (deltaTime * deltaTime) / 0.91;
        
        // Fly
        if(isFlying() && !isFlyEnabled())
            setFlying(false);
        
        if(isSneaking())
            getMotion().y -= 0.01F;
        
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
        LocalPlayer.speed = ((Vec3f) oldPosition.sub(getPosition())).len() / deltaTime;
        
        // Reduce motion
        getMotion().mul(reduce, 0.995F, reduce);
    }
    
    public void setJumpHeight(float height){
        timeToMaxJumpY = 0.27F * Mathc.sqrt(height);
        
        gravity = -2 * height / (timeToMaxJumpY * timeToMaxJumpY);
        jumpMotion = 2 * height / timeToMaxJumpY / Pize.monitor().getRefreshRate();
    }
    
    public float getFallHeight(){
        return fallHeight;
    }
    
    
    public void moveControl(float forward, float strafe){
        moveControl.set(forward, strafe);
    }
    
    public void jump(){
        if(isOnGround() && isFlying())
            setFlying(false);
        
        if(isFlying()){
            getMotion().y += 0.01F;
            
            return;
        }
        
        if(isOnGround()){
            // Jump
            getMotion().y = jumpMotion;
            
            // Jump-boost
            if(isSprinting()){
                final Vec2f jumpBoost = new Vec2f(2 * Pize.getDt(), 0).rotDeg(getRotation().yaw);
                getMotion().x += jumpBoost.x;
                getMotion().z += jumpBoost.y;
            }
        }else if(isFlyEnabled() && !isFlying() && Key.SPACE.isDown())
            setFlying(true);
    }
    
}
