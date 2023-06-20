package pize.tests.voxelgame.clientserver.level;

import pize.tests.voxelgame.clientserver.chunk.LevelChunk;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;

public abstract class ChunkManager{
    
    public abstract LevelChunk getChunk(ChunkPos chunkPos);
    
    public abstract LevelChunk getChunk(int chunkX, int chunkZ);
    
}
