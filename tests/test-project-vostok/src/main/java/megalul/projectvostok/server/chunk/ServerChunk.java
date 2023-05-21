package megalul.projectvostok.server.chunk;

import megalul.projectvostok.client.block.BlockProperties;
import megalul.projectvostok.client.block.BlockState;
import megalul.projectvostok.clientserver.chunk.Chunk;
import megalul.projectvostok.clientserver.chunk.storage.ChunkBlockUtils;
import megalul.projectvostok.clientserver.chunk.storage.ChunkHeightUtils;
import megalul.projectvostok.clientserver.chunk.storage.ChunkLightUtils;
import megalul.projectvostok.clientserver.chunk.storage.ChunkPos;
import megalul.projectvostok.server.world.ServerChunkManager;

import static megalul.projectvostok.clientserver.chunk.ChunkUtils.isOutOfBounds;

public class ServerChunk extends Chunk{
    
    private final ServerChunkManager chunkManagerOf;
    
    public ServerChunk(ServerChunkManager chunkManagerOf, ChunkPos position){
        super(chunkManagerOf, position);
        
        this.chunkManagerOf = chunkManagerOf;
    }
    
    public ServerChunkManager getProviderOf(){
        return chunkManagerOf;
    }
    
    
    public void setBlock(int x, int y, int z, short state){
        if(isOutOfBounds(x, z)){
            storage.setBlock(x, y, z, state);
            return;
        }
        
        BlockProperties previousBlock = BlockState.getProps(storage.setBlock(x, y, z, state));
        BlockProperties targetBlock = BlockState.getProps(state);
        if(previousBlock.equals(targetBlock))
            return;
        
        ChunkBlockUtils.updateNeighborChunksEdges(this, x, y, z, state);
        ChunkHeightUtils.updateHeight(storage, x, y, z, !targetBlock.isEmpty());
        
        if(targetBlock.isTransparent())
            getProviderOf().getWorldOf().getLight().updateBrokeBlockLight(this, x, y, z);
        else
            getProviderOf().getWorldOf().getLight().updatePlaceBlockLight(this, x, y, z);
        
        if(previousBlock.isGlow())
            getProviderOf().getWorldOf().getLight().decreaseBlockLight(this, x, y, z, previousBlock.getLightLevel());
        else if(targetBlock.isGlow())
            getProviderOf().getWorldOf().getLight().increaseBlockLight(this, x, y, z, targetBlock.getLightLevel());
    }
    
    public void setBlockFast(int x, int y, int z, short state){
        if(storage.setBlock(x, y, z, state) != BlockState.getID(state) && !isOutOfBounds(x, z))
            ChunkBlockUtils.updateNeighborChunksEdges(this, x, y, z, state);
    }
    
    
    public void setSkyLight(int x, int y, int z, int level){
        storage.setSkyLight(x, y, z, level);
        ChunkLightUtils.updateSkyLightEdgesOfNeighborChunks(this, x, y, z, level);
    }
    
    public void setBlockLight(int x, int y, int z, int level){
        storage.setBlockLight(x, y, z, level);
        ChunkLightUtils.updateBlockLightEdgesOfNeighborChunks(this, x, y, z, level);
    }
    
}
