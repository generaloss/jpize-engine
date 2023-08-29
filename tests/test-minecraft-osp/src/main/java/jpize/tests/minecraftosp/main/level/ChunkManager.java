package jpize.tests.minecraftosp.main.level;

import jpize.tests.minecraftosp.main.chunk.LevelChunk;
import jpize.tests.minecraftosp.main.chunk.storage.ChunkPos;

public abstract class ChunkManager{
    
    public abstract LevelChunk getChunk(ChunkPos chunkPos);
    
    public abstract LevelChunk getChunk(int chunkX, int chunkZ);
    
}
