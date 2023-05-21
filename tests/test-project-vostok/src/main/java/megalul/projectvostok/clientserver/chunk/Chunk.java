package megalul.projectvostok.clientserver.chunk;

import megalul.projectvostok.client.block.BlockProperties;
import megalul.projectvostok.client.block.BlockState;
import megalul.projectvostok.clientserver.world.ChunkManager;
import megalul.projectvostok.clientserver.chunk.storage.ChunkPos;
import megalul.projectvostok.clientserver.chunk.storage.ChunkStorage;

public class Chunk{
    
    protected final ChunkManager chunkManagerOF;
    protected final ChunkStorage storage;
    protected final ChunkPos position;

    public Chunk(ChunkManager chunkManagerOF, ChunkPos position){
        this.chunkManagerOF = chunkManagerOF;
        
        storage = new ChunkStorage();
        this.position = position;
    }
    
    public ChunkManager getChunkManagerOf(){
        return chunkManagerOF;
    }
    
    
    public ChunkStorage getStorage(){
        return storage;
    }
    
    public ChunkPos getPosition(){
        return position;
    }
    
    
    public short getBlock(int x, int y, int z){
        return storage.getBlock(x, y, z);
    }
    
    public byte getBlockID(int x, int y, int z){
        return storage.getBlockID(x, y, z);
    }
    
    public BlockProperties getBlockProps(int x, int y, int z){
        return BlockState.getProps(getBlock(x, y, z));
    }
    
    
    public void setBlock(int x, int y, int z, short state){
        storage.setBlock(x, y, z, state);
    }
    
    
    public byte getSkyLight(int x, int y, int z){
        return storage.getSkyLight(x, y, z);
    }
    
    public byte getBlockLight(int x, int y, int z){
        return storage.getBlockLight(x, y, z);
    }
    
    public int getLight(int x, int y, int z){
        return Math.max(getSkyLight(x, y, z), getBlockLight(x, y, z));
    }
    
    
    public void setSkyLight(int x, int y, int z, int level){
        storage.setSkyLight(x, y, z, level);
    }
    
    public void setBlockLight(int x, int y, int z, int level){
        storage.setBlockLight(x, y, z, level);
    }
    
    
    @Override
    public boolean equals(Object object){
        if(object == this)
            return true;
        if(object == null || object.getClass() != getClass())
            return false;
        Chunk chunk = (Chunk) object;
        return position.equals(chunk.position);
    }

    @Override
    public int hashCode(){
        return position.hashCode();
    }
    
}
