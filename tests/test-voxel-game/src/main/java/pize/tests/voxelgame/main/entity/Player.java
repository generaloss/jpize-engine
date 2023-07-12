package pize.tests.voxelgame.main.entity;

import pize.tests.voxelgame.main.level.Level;

public class Player extends Entity{
    
    private final String name;
    private boolean sprinting, sneaking, flyEnabled, flying, jumping;
    
    
    public Player(Level level, String name){
        super(EntityType.PLAYER, level);
        
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    
    
    public boolean isSprinting(){
        return sprinting;
    }
    
    public void setSprinting(boolean sprinting){
        if(sprinting && isCollidedTo(getRotation().getDirection().mul(1, 0, 1)))
            this.sprinting = false;
        else
            this.sprinting = sprinting;
    }
    
    public boolean isSneaking(){
        return sneaking;
    }
    
    public void setSneaking(boolean sneaking){
        this.sneaking = sneaking;
        getBoundingBox().getMax().y = sneaking ? 1.5F : 1.8F;
    }
    
    public boolean isFlyEnabled(){
        return flyEnabled;
    }
    
    public void setFlyEnabled(boolean flyEnabled){
        this.flyEnabled = flyEnabled;
    }
    
    public boolean isFlying(){
        return flying;
    }
    
    public void setFlying(boolean flying){
        this.flying = flying;
    }
    
    public boolean isJumping(){
        return jumping;
    }
    
    public void setJumping(boolean jumping){
        this.jumping = jumping;
    }
    
    @Override
    public float getEyeHeight(){
        return sneaking ? 1.27F : 1.62F;
    }
    
}
