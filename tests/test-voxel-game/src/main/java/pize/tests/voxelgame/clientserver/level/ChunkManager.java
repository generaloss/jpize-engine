package pize.tests.voxelgame.clientserver.level;

import pize.tests.voxelgame.clientserver.chunk.Chunk;
import pize.tests.voxelgame.clientserver.chunk.storage.ChunkPos;

public abstract class ChunkManager{
    
    public abstract Chunk getChunk(ChunkPos chunkPos);
    
    public abstract Chunk getChunk(int chunkX, int chunkZ);
    
}
