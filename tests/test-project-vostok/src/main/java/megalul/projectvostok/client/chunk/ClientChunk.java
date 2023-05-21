package megalul.projectvostok.client.chunk;

import megalul.projectvostok.client.block.BlockProperties;
import megalul.projectvostok.client.block.BlockState;
import megalul.projectvostok.client.world.ClientChunkManager;
import megalul.projectvostok.clientserver.chunk.Chunk;
import megalul.projectvostok.clientserver.chunk.storage.ChunkBlockUtils;
import megalul.projectvostok.clientserver.chunk.storage.ChunkHeightUtils;
import megalul.projectvostok.clientserver.chunk.storage.ChunkPos;

import static megalul.projectvostok.clientserver.chunk.ChunkUtils.isOutOfBounds;

public class ClientChunk extends Chunk{
    
    private final ClientChunkManager chunkManagerOf;
    
    public ClientChunk(ClientChunkManager chunkManagerOf, ChunkPos position){
        super(chunkManagerOf, position);
        
        this.chunkManagerOf = chunkManagerOf;
    }
    
    public ClientChunkManager getManagerOf(){
        return chunkManagerOf;
    }
    
    
    public void rebuild(boolean important){
        chunkManagerOf.rebuildChunk(this, important);
    }
    
    public void rebuild(){
        rebuild(false);
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
        
        rebuild(true);
    }
    
    public void setBlockFast(int x, int y, int z, short state){
        if(storage.setBlock(x, y, z, state) != BlockState.getID(state) && !isOutOfBounds(x, z))
            ChunkBlockUtils.updateNeighborChunksEdges(this, x, y, z, state);
    }
    
    
    public void setSkyLight(int x, int y, int z, int level){
        storage.setSkyLight(x, y, z, level);
    }
    
    public void setBlockLight(int x, int y, int z, int level){
        storage.setBlockLight(x, y, z, level);
    }
    
}
