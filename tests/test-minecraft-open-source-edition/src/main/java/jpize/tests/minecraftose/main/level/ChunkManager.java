package jpize.tests.minecraftose.main.level;

import jpize.tests.minecraftose.main.chunk.LevelChunk;
import jpize.tests.minecraftose.main.chunk.storage.ChunkPos;

public abstract class ChunkManager{
    
    public abstract LevelChunk getChunk(ChunkPos chunkPos);
    
    public abstract LevelChunk getChunk(int chunkX, int chunkZ);
    
}
