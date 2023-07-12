package pize.tests.voxelgame.client.entity;

import pize.io.glfw.Key;
import pize.math.Mathc;
import pize.math.Maths;
import pize.math.vecmath.vector.Vec3f;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.Blocks;
import pize.tests.voxelgame.main.level.Level;

public class LocalPlayer extends AbstractClientPlayer{
    
    private final Vec3f moveControl;
    private float jumpDownY, lastMotionY, fallHeight;
    
    public BlockProperties holdBlock = Blocks.GRASS;
    
    public LocalPlayer(Level levelOF, String name){
        super(levelOF, name);

        moveControl = new Vec3f();
    }
    
    
    public void tick(){
        super.tick();
        
        /** -------- Vertical Move -------- */
        
        // Jumping
        if(isJumping()){
            if(isOnGround()){
                // Jump
                getMotion().y = 0.42F;
                
                // Jump-boost
                if(isSprinting()){
                    final float yaw = getRotation().yaw * Maths.toRad;
                    final float jumpBoost = 0.2F;
                    getMotion().x += jumpBoost * Mathc.cos(yaw);
                    getMotion().z += jumpBoost * Mathc.sin(yaw);
                }
            }
            
            // Activate Flying
            else if(isFlyEnabled() && !isFlying() && Key.SPACE.isDown())
                setFlying(true);
        }
        
        // Flying
        if(isOnGround() && isFlying())
            setFlying(false);
        
        if(isFlying()){
            if(isSneaking())
                getMotion().y -= 0.05F;
            
            if(isJumping())
                getMotion().y += 0.05F;
            
            if(!isFlyEnabled())
                setFlying(false);
        }
        
        // Gravity
        if(!isOnGround() && !isFlying())
            getMotion().y -= 0.08;
        
        // Reduce Vertical Motion
        getMotion().y *= 0.98;
        
        
        /** -------- Horizontal Move -------- */
        
        // Movement multiplier
        float movementMul = 0.98F; // Default
        if(isSneaking() && !isFlying())
            movementMul *= 0.3; // Sneaking
        if(isSprinting())
            movementMul *= 1.3; // Sprinting
        if(isFlying())
            movementMul *= 10; // Flying
        
        
        // Slipperiness multiplier
        float slipperinessMul = 1; // Air
        if(isOnGround())
            slipperinessMul *= 0.6; // Ground
        
        // Reduce Last Motion
        final float reduceHorizontal = slipperinessMul * 0.91F;
        getMotion().mul(reduceHorizontal, 1, reduceHorizontal);
        
        // Move
        float moveControlLen = moveControl.len();
        if(moveControlLen > 0){
            final Vec3f acceleration = new Vec3f(moveControl.x, 0, moveControl.z);
            
            if(isOnGround()){
                final float slipperiness = 0.6F / slipperinessMul;
                acceleration.mul(0.1 * movementMul * slipperiness * slipperiness * slipperiness);
            }else
                acceleration.mul(0.02 * movementMul);
            
            getMotion().add(acceleration);
        }
        
        
        /** -------- Other -------- */
        
        // Fall height
        if(getMotion().y < 0 && lastMotionY >= 0)
            jumpDownY = getPosition().y;
        
        if(isOnGround() && jumpDownY != 0){
            fallHeight = jumpDownY - getPosition().y;
            jumpDownY = 0;
        }
        
        lastMotionY = getMotion().y;
        
        // Move entity
        final Vec3f collidedMotion = moveEntity(getMotion());
        getMotion().collidedAxesToZero(collidedMotion);
        
        // Disable sprinting
        if(collidedMotion.x == 0 || collidedMotion.z == 0)
            setSprinting(false);
    }
    
    public float getFallHeight(){
        return fallHeight;
    }
    
    
    public void moveControl(Vec3f motion){
        moveControl.set(motion);
    }
    
}
