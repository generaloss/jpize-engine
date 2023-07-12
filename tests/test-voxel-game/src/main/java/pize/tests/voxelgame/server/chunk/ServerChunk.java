package pize.tests.voxelgame.server.chunk;

import pize.tests.voxelgame.main.chunk.LevelChunk;
import pize.tests.voxelgame.main.chunk.storage.ChunkPos;
import pize.tests.voxelgame.main.chunk.storage.HeightmapType;
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
    
    public boolean setBlockFast(int lx, int y, int lz, short state){
        return super.setBlock(lx, y, lz, state);
    }
    
    
    public void setLight(int lx, int y, int lz, int level){
        super.setLight(lx, y, lz, level);
    }
    
}
