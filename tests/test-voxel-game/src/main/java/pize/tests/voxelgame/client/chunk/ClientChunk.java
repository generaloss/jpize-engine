package pize.tests.voxelgame.client.chunk;

import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.level.ClientLevel;
import pize.tests.voxelgame.clientserver.chunk.LevelChunk;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkBlockUtils;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;
import pize.tests.voxelgame.clientserver.chunk.storage.HeightmapType;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.isOutOfBounds;

public class ClientChunk extends LevelChunk{
    
    public ClientChunk(ClientLevel level, ChunkPos position){
        super(level, position);
    }
    
    public ClientLevel getLevel(){
        return (ClientLevel) level;
    }
    
    
    public void rebuild(boolean important){
        getLevel().getChunkManager().rebuildChunk(this, important);
    }
    
    
    public boolean setBlock(int lx, int y, int lz, short blockState){
        if(!super.setBlock(lx, y, lz, blockState) || isOutOfBounds(lx, lz))
            return false;
        
        ChunkBlockUtils.updateNeighborChunksEdges(this, lx, y, lz, blockState);
        getHeightMap(HeightmapType.SURFACE).update(lx, y, lz, BlockState.getID(blockState) != Block.AIR.ID);
        rebuild(true);
        
        return true;
    }
    
    
}
