package pize.tests.voxelgame.main.level;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.main.block.BlockData;
import pize.tests.voxelgame.main.Tickable;
import pize.tests.voxelgame.main.chunk.LevelChunk;
import pize.tests.voxelgame.main.entity.Entity;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Level implements Tickable{
    
    private final Map<UUID, Entity> entityMap;
    
    
    public Level(){
        this.entityMap = new ConcurrentHashMap<>();
    }

    @Override
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
    
    public BlockProperties getBlockProps(int x, int y, int z){
        return BlockData.getProps(getBlock(x, y, z));
    }
    
    public abstract boolean setBlock(int x, int y, int z, short block);
    
    public abstract int getHeight(int x, int z);
    
    
    public abstract void setLight(int x, int y, int z, int level);
    
    public abstract byte getLight(int x, int y, int z);
    
    
    public abstract LevelConfiguration getConfiguration();
    
    public abstract ChunkManager getChunkManager();

    public abstract LevelChunk getBlockChunk(int blockX, int blockZ);

}
