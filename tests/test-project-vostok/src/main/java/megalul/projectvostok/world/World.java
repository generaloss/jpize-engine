package megalul.projectvostok.world;

import megalul.projectvostok.Main;
import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.chunk.Chunk;
import megalul.projectvostok.entity.Entity;
import megalul.projectvostok.world.light.WorldLight;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static megalul.projectvostok.chunk.ChunkUtils.getChunkPos;
import static megalul.projectvostok.chunk.ChunkUtils.getLocalPos;

public class World{

    private final Main sessionOF;
    private final ChunkProvider chunkProvider;
    private final List<Entity> entities;
    private final WorldLight light;

    public World(Main sessionOF){
        this.sessionOF = sessionOF;
        
        chunkProvider = new ChunkProvider(this);
        entities = new ArrayList<>();
        light = new WorldLight(this);
    }
    
    
    public Main getSessionOf(){
        return sessionOF;
    }


    public short getBlock(int x, int y, int z){
        Chunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(getLocalPos(x), y, getLocalPos(z));

        return Block.AIR.getState();
    }
    
    public byte getBlockID(int x, int y, int z){
        Chunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlockID(getLocalPos(x), y, getLocalPos(z));
        
        return 0;
    }

    public void setBlock(int x, int y, int z, short block){
        Chunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlock(getLocalPos(x), y, getLocalPos(z), block);
    }
    
    
    public byte getSkyLight(int x, int y, int z){
        Chunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getSkyLight(getLocalPos(x), y, getLocalPos(z));
        
        return -1;
    }
    
    public void setLight(int x, int y, int z, int level){ //: REMOVE
        Chunk targetChunk = getChunk(x, z);
        if(targetChunk != null && level > targetChunk.getSkyLight(getLocalPos(x), y, getLocalPos(z))){
            targetChunk.setSkyLight(getLocalPos(x), y, getLocalPos(z), level);
            targetChunk.rebuild();
        }
    }
    
    public void updateSkyLight(int x, int z){
        Chunk targetChunk = getChunk(x, z);
        if(targetChunk != null){
            light.updateChunkSkyLight(targetChunk, getLocalPos(x), getLocalPos(z));
            
            System.out.println(targetChunk.getPos().x + " " + targetChunk.getPos().z);
        }
    }
    
    
    public void updateEntities(){
        for(Entity entity: entities)
            entity.update();
    }
    
    public void putEntity(Entity entity){
        entities.add(entity);
    }


    public Chunk getChunk(int x, int z){
        return chunkProvider.getChunk(getChunkPos(x), getChunkPos(z));
    }

    public ChunkProvider getProvider(){
        return chunkProvider;
    }
    
    public Collection<Entity> getEntities(){
        return entities;
    }
    
    public WorldLight getLight(){
        return light;
    }

}
