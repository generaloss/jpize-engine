package glit.tests.minecraft.client.world.chunk;

import glit.tests.minecraft.client.world.chunk.utils.ChunkPos;
import glit.tests.minecraft.client.world.chunk.utils.EmptyChunk;
import glit.tests.minecraft.client.world.chunk.utils.IChunk;

import java.util.HashMap;
import java.util.Map;

public class ChunkManager{

    private final Map<ChunkPos, IChunk> chunks;

    public ChunkManager(){
        chunks = new HashMap<>();
    }


    public IChunk get(ChunkPos chunkPos){
        return chunks.getOrDefault(chunkPos, EMPTY_CHUNK);
    }

    public void put(IChunk chunk){
        if(chunk == null || chunks.containsValue(chunk))
            return;

        chunks.put(chunk.getPos(),chunk);
    }


    public void remove(ChunkPos chunkPos){
        chunks.remove(chunkPos);
    }

    public void remove(Chunk chunk){
        if(chunk != null)
            remove(chunk.getPos());
    }


    public boolean contains(ChunkPos chunkPos){
        return chunks.containsKey(chunkPos);
    }

    public boolean contains(Chunk chunk){
        return contains(chunk.getPos());
    }


    public static final EmptyChunk EMPTY_CHUNK = new EmptyChunk();

}