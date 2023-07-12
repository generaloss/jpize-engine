package pize.tests.voxelgame.client.chunk;

import pize.tests.voxelgame.main.chunk.LevelChunk;
import pize.tests.voxelgame.main.chunk.storage.ChunkPos;
import pize.tests.voxelgame.main.chunk.storage.HeightmapType;
import pize.tests.voxelgame.main.level.ChunkManagerUtils;
import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.client.block.Blocks;
import pize.tests.voxelgame.client.level.ClientLevel;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.isOutOfBounds;

public class ClientChunk extends LevelChunk{
    
    public ClientChunk(ClientLevel level, ChunkPos position){
        super(level, position);
    }
    
    
    public boolean setBlock(int lx, int y, int lz, short blockState){
        if(!super.setBlock(lx, y, lz, blockState) || isOutOfBounds(lx, lz))
            return false;
        
        getHeightMap(HeightmapType.SURFACE).update(lx, y, lz, BlockState.getID(blockState) != Blocks.AIR.getID());
        rebuild(true);
        ChunkManagerUtils.rebuildNeighborChunks(this, lx, lz);
        
        return true;
    }
    
    public void setLight(int lx, int y, int lz, int level){
        super.setLight(lx, y, lz, level);
        rebuild(true);
    }
    
    
    public void rebuild(boolean important){
        getLevel().getChunkManager().rebuildChunk(this, important);
    }
    
    public ClientLevel getLevel(){
        return (ClientLevel) level;
    }
    
    public ClientChunk getNeighbor(int chunkX, int chunkZ){
        return getLevel().getChunkManager().getChunk(position.x + chunkX, position.z + chunkZ);
    }
    
}
