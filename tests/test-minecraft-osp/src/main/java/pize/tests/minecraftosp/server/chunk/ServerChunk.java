package pize.tests.minecraftosp.server.chunk;

import pize.tests.minecraftosp.client.block.BlockProperties;
import pize.tests.minecraftosp.server.level.ServerLevel;
import pize.tests.minecraftosp.main.block.BlockData;
import pize.tests.minecraftosp.main.chunk.LevelChunk;
import pize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import pize.tests.minecraftosp.main.chunk.storage.HeightmapType;

public class ServerChunk extends LevelChunk{

    public ServerChunk(ServerLevel level, ChunkPos position){
        super(level, position);
    }
    
    public ServerLevel getLevel(){
        return (ServerLevel) level;
    }
    
    
    public boolean setBlock(int lx, int y, int lz, short blockData){
        final BlockProperties blockProperties = BlockData.getProps(blockData);
        final boolean result = super.setBlock(lx, y, lz, blockData);
        if(result){
            getHeightMap(HeightmapType.HIGHEST).update(lx, y, lz, !blockProperties.isEmpty());
            return true;
        }

        return false;
    }
    
    public boolean setBlockFast(int lx, int y, int lz, short data){
        return super.setBlock(lx, y, lz, data);
    }
    
    
    public void setLight(int lx, int y, int lz, int level){
        super.setLight(lx, y, lz, level);
    }
    
}
