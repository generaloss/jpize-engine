package pize.tests.voxelgame.clientserver.chunk;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkStorage;
import pize.tests.voxelgame.clientserver.net.packet.PacketChunk;

public class Chunk{
    
    protected final ChunkStorage storage;
    protected final ChunkPos position;

    public Chunk(ChunkPos position){
        storage = new ChunkStorage(this);
        this.position = position;
    }
    
    public Chunk(PacketChunk packet){
        storage = new ChunkStorage(this, packet);
        this.position = new ChunkPos(packet.chunkX, packet.chunkZ);
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
    
    public BlockProperties getBlockProps(int x, int y, int z){
        return BlockState.getProps(getBlock(x, y, z));
    }
    
    
    public void setBlock(int x, int y, int z, short state, boolean net){
        storage.setBlock(x, y, z, state);
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
