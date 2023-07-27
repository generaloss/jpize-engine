package pize.tests.minecraftosp.main.level;

import pize.tests.minecraftosp.main.chunk.LevelChunk;
import pize.tests.minecraftosp.main.chunk.storage.ChunkPos;

public abstract class ChunkManager{
    
    public abstract LevelChunk getChunk(ChunkPos chunkPos);
    
    public abstract LevelChunk getChunk(int chunkX, int chunkZ);
    
}
