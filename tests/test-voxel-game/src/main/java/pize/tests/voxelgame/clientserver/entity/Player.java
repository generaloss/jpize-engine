package pize.tests.voxelgame.clientserver.entity;

import pize.tests.voxelgame.clientserver.level.Level;

public class Player extends Entity{
    
    private final String name;
    private boolean sprinting, sneaking;
    
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
    
}
