package pize.tests.voxelgame.server.chunk;

import pize.tests.voxelgame.base.chunk.LevelChunk;
import pize.tests.voxelgame.base.chunk.storage.ChunkPos;
import pize.tests.voxelgame.base.chunk.storage.HeightmapType;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.server.level.ServerLevel;

public class ServerChunk extends LevelChunk{
    
    public ServerChunk(ServerLevel level, ChunkPos position){
        super(level, position);
    }
    
    public ServerLevel getLevel(){
        return (ServerLevel) level;
    }
    
    
    public boolean setBlock(int lx, int y, int lz, short state){
        final BlockProperties blockProperties = BlockState.getProps(state);
        if(super.setBlock(lx, y, lz, state))
            getHeightMap(HeightmapType.SURFACE).update(lx, y, lz, !blockProperties.isEmpty());
        
        return true;
    }
    
    public void setBlockFast(int lx, int y, int lz, short state){
        super.setBlock(lx, y, lz, state);
    }
    
    
    public void setLight(int lx, int y, int lz, int level){
        super.setLight(lx, y, lz, level);
    }
    
}
