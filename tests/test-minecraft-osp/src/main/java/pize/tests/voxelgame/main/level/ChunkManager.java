package pize.tests.voxelgame.main.level;

import pize.tests.voxelgame.main.chunk.LevelChunk;
import pize.tests.voxelgame.main.chunk.storage.ChunkPos;

public abstract class ChunkManager{
    
    public abstract LevelChunk getChunk(ChunkPos chunkPos);
    
    public abstract LevelChunk getChunk(int chunkX, int chunkZ);
    
}
