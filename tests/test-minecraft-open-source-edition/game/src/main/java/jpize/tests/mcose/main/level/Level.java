package jpize.tests.mcose.main.level;

import jpize.tests.mcose.client.block.Block;
import jpize.tests.mcose.client.block.BlockProps;
import jpize.tests.mcose.main.Tickable;
import jpize.tests.mcose.main.block.BlockData;
import jpize.tests.mcose.main.chunk.LevelChunk;
import jpize.tests.mcose.main.chunk.storage.HeightmapType;
import jpize.tests.mcose.main.entity.Entity;

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
