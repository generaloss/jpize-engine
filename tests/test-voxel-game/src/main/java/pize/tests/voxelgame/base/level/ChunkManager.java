package pize.tests.voxelgame.base.level;

import pize.tests.voxelgame.base.chunk.LevelChunk;
import pize.tests.voxelgame.base.chunk.storage.ChunkPos;

public abstract class ChunkManager{
    
    public abstract LevelChunk getChunk(ChunkPos chunkPos);
    
    public abstract LevelChunk getChunk(int chunkX, int chunkZ);
    
}
