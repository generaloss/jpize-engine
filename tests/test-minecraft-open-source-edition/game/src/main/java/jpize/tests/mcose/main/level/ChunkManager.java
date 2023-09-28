package jpize.tests.mcose.main.level;

import jpize.tests.mcose.main.chunk.LevelChunk;
import jpize.tests.mcose.main.chunk.storage.ChunkPos;

public abstract class ChunkManager{
    
    public abstract LevelChunk getChunk(ChunkPos chunkPos);
    
    public abstract LevelChunk getChunk(int chunkX, int chunkZ);
    
}
