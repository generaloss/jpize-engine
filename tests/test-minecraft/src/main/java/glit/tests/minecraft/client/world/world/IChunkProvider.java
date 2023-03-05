package glit.tests.minecraft.client.world.world;

import glit.tests.minecraft.client.world.chunk.ChunkManager;
import glit.tests.minecraft.client.world.chunk.utils.ChunkPos;
import glit.tests.minecraft.client.world.chunk.utils.IChunk;

public abstract class IChunkProvider{

    abstract public IChunk getChunk(ChunkPos chunkPos,boolean load);

    public boolean chunkExists(ChunkPos chunkPos){
        return getChunk(chunkPos, false) != ChunkManager.EMPTY_CHUNK;
    }

}
