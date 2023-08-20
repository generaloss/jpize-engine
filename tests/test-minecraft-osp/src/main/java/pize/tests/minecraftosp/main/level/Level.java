package pize.tests.minecraftosp.main.level;

import pize.tests.minecraftosp.client.block.Block;
import pize.tests.minecraftosp.client.block.BlockProps;
import pize.tests.minecraftosp.main.Tickable;
import pize.tests.minecraftosp.main.block.BlockData;
import pize.tests.minecraftosp.main.chunk.LevelChunk;
import pize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import pize.tests.minecraftosp.main.entity.Entity;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Level implements Tickable {
    
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


    public abstract short getBlockState(int x, int y, int z);

    public int getBlockID(int x, int y, int z){
        return BlockData.getID(getBlockState(x, y, z));
    }

    public abstract Block getBlock(int x, int y, int z);

    public BlockProps getBlockProps(int x, int y, int z){
        return BlockData.getProps(getBlockState(x, y, z));
    }


    public abstract boolean setBlockState(int x, int y, int z, short block);

    public abstract boolean setBlock(int x, int y, int z, Block block);

    
    public abstract int getHeight(HeightmapType heightmapType, int x, int z);


    public abstract int getSkyLight(int x, int y, int z);

    public abstract void setSkyLight(int x, int y, int z, int level);

    public abstract int getBlockLight(int x, int y, int z);

    public abstract void setBlockLight(int x, int y, int z, int level);

    
    public abstract LevelConfiguration getConfiguration();
    
    public abstract ChunkManager getChunkManager();

    public abstract LevelChunk getBlockChunk(int blockX, int blockZ);

}
