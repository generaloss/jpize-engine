package pize.tests.voxelgame.client.chunk;

import pize.tests.voxelgame.base.chunk.LevelChunk;
import pize.tests.voxelgame.base.chunk.storage.ChunkPos;
import pize.tests.voxelgame.base.chunk.storage.HeightmapType;
import pize.tests.voxelgame.base.level.ChunkManagerUtils;
import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.client.level.ClientLevel;

import static pize.tests.voxelgame.base.chunk.ChunkUtils.isOutOfBounds;

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
        
        getHeightMap(HeightmapType.SURFACE).update(lx, y, lz, BlockState.getID(blockState) != Block.AIR.ID);
        rebuild(true);
        ChunkManagerUtils.rebuildNeighborChunks(this, lx, lz);
        
        return true;
    }
    
    
    public void setLight(int lx, int y, int lz, int level){
        super.setLight(lx, y, lz, level);
        rebuild(true);
    }
    
}
