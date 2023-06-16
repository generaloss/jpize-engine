package pize.tests.voxelgame.client.entity;

import pize.Pize;
import pize.io.glfw.Key;
import pize.math.Mathc;
import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec3d;
import pize.math.vecmath.vector.Vec3f;
import pize.physic.BoundingBox;
import pize.tests.voxelgame.clientserver.entity.Entity;
import pize.tests.voxelgame.clientserver.world.World;

public class LocalPlayer extends Entity{
    
    public static final float PLAYER_HEIGHT = 1.8F;
    public static final float PLAYER_WIDTH = 0.6F;
    public static final float PLAYER_EYES_HEIGHT = 1.62F;
    public static final float PLAYER_JUMP_HEIGHT = 1.25F;
    
    public static float speed;


    private float gravity, timeToMaxJumpY, jumpMotion;
    private final Vec2f playerMoving;
    private boolean sprinting, sneaking;
    private float jumpDownY, oldMotionY;
    
    public LocalPlayer(World worldOF){
        super(new BoundingBox(
            -PLAYER_WIDTH / 2, 0            , -PLAYER_WIDTH / 2,
             PLAYER_WIDTH / 2, PLAYER_HEIGHT,  PLAYER_WIDTH / 2
        ), worldOF);

        playerMoving = new Vec2f();
        setJumpHeight(PLAYER_JUMP_HEIGHT);
    }
    
    
    @Override
    public void onLivingUpdate(){
        // Sprint
        if(Key.W.isReleased() && sprinting)
            sprinting = false;
        
        // Horizontal move
        float reduce = 0.91F;
        float movementFactor = 0.34F * Pize.getDt();
        if(sprinting)
            movementFactor *= 1.3;
        
        float dist = playerMoving.len();
        if(dist > 0){
            playerMoving.mul(movementFactor);
            playerMoving.rotDeg(rotation.yaw);
            
            motion.x += playerMoving.x;
            motion.z += playerMoving.y;
        }
        
        // Gravity
        if(!onGround)
            motion.y += gravity * (Pize.getDt() * Pize.getDt());
        
        // Fall height
        if(motion.y < 0 && oldMotionY >= 0)
            jumpDownY = position.y;
        
        if(onGround && jumpDownY != 0){
            System.out.println(jumpDownY - position.y);
            jumpDownY = 0;
        }
        
        oldMotionY = (float) motion.y;
        
        // Move entity & Measure speed
        final Vec3f oldPosition = position.clone();
        {
            final Vec3d collidedMotion = moveEntity(new Vec3d(motion.x, motion.y, motion.z));
            motion.collidedAxesToZero(collidedMotion);
        }
        speed = ((Vec3f) oldPosition.sub(position)).len() / Pize.getDt();
        
        // Reduce motion
        motion.mul(reduce, 1, reduce);
    }
    
    public void setMoving(float forward, float strafe){
        playerMoving.set(forward, strafe);
    }
    
    public void jump(){
        if(onGround){
            // Jump
            motion.y = jumpMotion * Pize.getDt();
            
            // Jump-boost
            if(sprinting){
                final Vec2f jumpBoost = new Vec2f(2 * Pize.getDt(), 0).rotDeg(rotation.yaw);
                motion.x += jumpBoost.x;
                motion.z += jumpBoost.y;
            }
        }
    }
    
    public boolean isSprinting(){
        return sprinting;
    }
    
    public void sprint(){
        // if(onGround)
        sprinting = true;
    }
    
    public boolean isSneaking(){
        return sneaking;
    }
    
    public void setSneaking(boolean sneaking){
        this.sneaking = sneaking;
    }
    
    public void setJumpHeight(float height){
        timeToMaxJumpY = 0.27F * Mathc.sqrt(height);
        
        gravity = -2 * height / (timeToMaxJumpY * timeToMaxJumpY);
        jumpMotion = 2 * height / timeToMaxJumpY;
    }
    
    
    @Override
    public float getEyes(){
        return PLAYER_EYES_HEIGHT;
    }
    
}
