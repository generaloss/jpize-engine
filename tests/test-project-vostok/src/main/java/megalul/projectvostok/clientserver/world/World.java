package megalul.projectvostok.clientserver.world;

import megalul.projectvostok.clientserver.entity.Entity;

import java.util.Collection;

public interface World{
    
    short getBlock(int x, int y, int z);
    
    byte getBlockID(int x, int y, int z);
    
    void setBlock(int x, int y, int z, short block);
    
    
    int getLight(int x, int y, int z);
    
    Collection<Entity> getEntities();
    
}
