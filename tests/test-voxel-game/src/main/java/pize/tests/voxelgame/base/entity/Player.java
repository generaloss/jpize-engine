package pize.tests.voxelgame.base.entity;

import pize.tests.voxelgame.base.level.Level;

public class Player extends Entity{
    
    private final String name;
    private boolean sprinting, sneaking, flyEnabled, flying;
    
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
        this.sprinting = sprinting;
    }
    
    public boolean isSneaking(){
        return sneaking;
    }
    
    public void setSneaking(boolean sneaking){
        this.sneaking = sneaking;
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
    
}
