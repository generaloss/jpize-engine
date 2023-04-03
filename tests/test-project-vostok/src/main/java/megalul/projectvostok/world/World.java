package megalul.projectvostok.world;

import megalul.projectvostok.Main;
import megalul.projectvostok.block.BlockState;
import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.chunk.Chunk;

import static megalul.projectvostok.chunk.ChunkUtils.*;

public class World{

    private final ChunkProvider chunkProvider;

    public World(Main session){
        chunkProvider = new ChunkProvider(session);
    }


    public short getBlock(int x, int y, int z){
        Chunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            return targetChunk.getBlock(getLocalPos(x), y, getLocalPos(z));

        return Block.AIR.getState();
    }

    public void setBlock(int x, int y, int z, short block){
        Chunk targetChunk = getChunk(x, z);
        if(targetChunk != null)
            targetChunk.setBlock(getLocalPos(x), y, getLocalPos(z), block);
    }


    public Chunk getChunk(int x, int z){
        return chunkProvider.getChunk(getChunkPos(x), getChunkPos(z));
    }

    public ChunkProvider getChunks(){
        return chunkProvider;
    }

}
