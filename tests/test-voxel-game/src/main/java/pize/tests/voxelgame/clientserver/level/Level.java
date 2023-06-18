package pize.tests.voxelgame.clientserver.level;

import pize.tests.voxelgame.clientserver.chunk.Chunk;
import pize.tests.voxelgame.clientserver.entity.Entity;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Level{
    
    private final String name;
    private final Map<UUID, Entity> entityMap;
    
    public Level(String name){
        this.name = name;
        this.entityMap = new ConcurrentHashMap<>();
    }
    
    public String getName(){
        return name;
    }
    
    
    public void tick(){
        for(Entity entity : entityMap.values())
            entity.tick();
    }
    
    
    public Collection<Entity> getEntities(){
        return entityMap.values();
    }
    
    public Entity getEntity(UUID uuid){
        return entityMap.get(uuid);
    }
    
    public void addEntity(Entity entity){
        entityMap.put(entity.getUUID(), entity);
    }
    
    public void removeEntity(Entity entity){
        removeEntity(entity.getUUID());
    }
    
    public void removeEntity(UUID entityUUID){
        entityMap.remove(entityUUID);
    }
    
    
    public abstract short getBlock(int x, int y, int z);
    
    public abstract void setBlock(int x, int y, int z, short block);
    
    public abstract int getHeight(int x, int z);
    
    
    public abstract <M extends ChunkManager> M getChunkManager();
    
    public abstract  <C extends Chunk> C getChunk(int blockX, int blockZ);

}
