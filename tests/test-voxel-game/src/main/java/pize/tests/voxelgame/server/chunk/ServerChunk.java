package pize.tests.voxelgame.server.chunk;

import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.clientserver.chunk.LevelChunk;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkBlockUtils;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;
import pize.tests.voxelgame.clientserver.chunk.storage.HeightmapType;
import pize.tests.voxelgame.server.level.ServerLevel;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.isOutOfBounds;

public class ServerChunk extends LevelChunk{
    
    public ServerChunk(ServerLevel level, ChunkPos position){
        super(level, position);
    }
    
    public ServerLevel getLevel(){
        return (ServerLevel) level;
    }
    
    
    public boolean setBlock(int x, int y, int z, short state){
        if(isOutOfBounds(x, z)){
            super.setBlock(x, y, z, state);
            return false;
        }
        
        BlockProperties targetBlock = BlockState.getProps(state);
        if(!super.setBlock(x, y, z, state))
            return false;
        
        ChunkBlockUtils.updateNeighborChunksEdges(this, x, y, z, state);
        getHeightMap(HeightmapType.SURFACE).update(x, y, z, !targetBlock.isEmpty());
        
        return true;
    }
    
    public void setBlockFast(int x, int y, int z, short state){
        if(this.setBlock(x, y, z, state) && !isOutOfBounds(x, z))
            ChunkBlockUtils.updateNeighborChunksEdges(this, x, y, z, state);
    }
    
}
