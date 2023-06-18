package pize.tests.voxelgame.server.chunk;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.clientserver.chunk.Chunk;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkBlockUtils;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkHeightUtils;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;
import pize.tests.voxelgame.server.level.ServerChunkManager;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.isOutOfBounds;

public class ServerChunk extends Chunk{
    
    private final ServerChunkManager chunkManagerOf;
    
    public ServerChunk(ServerChunkManager chunkManagerOf, ChunkPos position){
        super(position);
        
        this.chunkManagerOf = chunkManagerOf;
    }
    
    public ServerChunkManager getManagerOf(){
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
    }
    
    public void setBlockFast(int x, int y, int z, short state){
        if(storage.setBlock(x, y, z, state) != BlockState.getID(state) && !isOutOfBounds(x, z))
            ChunkBlockUtils.updateNeighborChunksEdges(this, x, y, z, state);
    }
    
}
