package megalul.projectvostok.chunk;

import megalul.projectvostok.block.BlockProperties;
import megalul.projectvostok.block.BlockState;
import megalul.projectvostok.chunk.storage.*;
import megalul.projectvostok.world.ChunkProvider;

import static megalul.projectvostok.chunk.ChunkUtils.isOutOfBounds;

public class Chunk{

    private final ChunkProvider providerOF;
    private final ChunkStorage storage;
    private final ChunkPos position;

    public Chunk(ChunkProvider providerOF, ChunkPos position){
        this.providerOF = providerOF;

        this.position = position;
        storage = new ChunkStorage();
    }
    
    public ChunkProvider getProviderOf(){
        return providerOF;
    }
    
    
    public ChunkStorage getStorage(){
        return storage;
    }
    
    public ChunkPos getPos(){
        return position;
    }
    
    public void rebuild(){
        providerOF.rebuildChunk(this);
    }
    
    
    public byte getSkyLight(int x, int y, int z){
        return storage.getSkyLight(x, y, z);
    }
    
    public void setSkyLight(int x, int y, int z, int level){
        storage.setSkyLight(x, y, z, level);
        ChunkLightUtils.updateSkyLightEdgesOfNeighborChunks(this, x, y, z, level);
    }
    
    public byte getBlockLight(int x, int y, int z){
        return storage.getBlockLight(x, y, z);
    }
    
    public void setBlockLight(int x, int y, int z, int level){
        storage.setBlockLight(x, y, z, level);
        ChunkLightUtils.updateBlockLightEdgesOfNeighborChunks(this, x, y, z, level);
    }
    
    public int getLight(int x, int y, int z){
        return Math.max(getSkyLight(x, y, z), getBlockLight(x, y, z));
    }
    
    
    
    public short getBlock(int x, int y, int z){
        return storage.getBlock(x, y, z);
    }
    
    public BlockProperties getBlockProps(int x, int y, int z){
        return BlockState.getProps(getBlock(x, y, z));
    }
    
    public byte getBlockID(int x, int y, int z){
        return storage.getBlockID(x, y, z);
    }
    
    public void setBlock(int x, int y, int z, short state){
        BlockProperties previousBlock = BlockState.getProps(storage.setBlock(x, y, z, state));
        if(isOutOfBounds(x, z))
            return;
        
        BlockProperties targetBlock = BlockState.getProps(state);
        
        if(previousBlock.equals(targetBlock))
            return;
        
        ChunkBlockUtils.updateNeighborChunksEdges(this, x, y, z, state);
        ChunkHeightUtils.updateHeight(storage, x, y, z, !targetBlock.isEmpty());
        
        if(previousBlock.isGlow())
            getProviderOf().getWorldOf().getLight().decreaseBlockLight(this, x, y, z, previousBlock.getLightLevel());
        else if(targetBlock.isGlow())
            getProviderOf().getWorldOf().getLight().increaseBlockLight(this, x, y, z, targetBlock.getLightLevel());
        
        rebuild();
    }
    
    public void setBlockFast(int x, int y, int z, short state){
        if(storage.setBlock(x, y, z, state) != BlockState.getID(state) && !isOutOfBounds(x, z))
            ChunkBlockUtils.updateNeighborChunksEdges(this, x, y, z, state);
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
