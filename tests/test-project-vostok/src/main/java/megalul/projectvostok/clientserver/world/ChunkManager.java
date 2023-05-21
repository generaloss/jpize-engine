package megalul.projectvostok.clientserver.world;

import megalul.projectvostok.clientserver.chunk.Chunk;
import megalul.projectvostok.clientserver.chunk.storage.ChunkPos;

public abstract class ChunkManager{
    
    public abstract Chunk getChunk(ChunkPos chunkPos);
    
    public abstract Chunk getChunk(int chunkX, int chunkZ);
    
}
