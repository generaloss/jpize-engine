package pize.tests.voxelgame.clientserver.world;

import pize.tests.voxelgame.clientserver.entity.Entity;

import java.util.Collection;

public interface World{
    
    short getBlock(int x, int y, int z);
    
    byte getBlockID(int x, int y, int z);
    
    void setBlock(int x, int y, int z, short block, boolean net);
    
    
    int getLight(int x, int y, int z);
    
    Collection<Entity> getEntities();
    
}
