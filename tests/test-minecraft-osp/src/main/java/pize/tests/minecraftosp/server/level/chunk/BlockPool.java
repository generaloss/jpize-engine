package pize.tests.minecraftosp.server.level.chunk;

import pize.tests.minecraftosp.main.chunk.storage.ChunkPos;
import pize.tests.minecraftosp.server.chunk.ServerChunk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.*;

public class BlockPool{

    private final ServerChunkManager chunkManager;
    private final Map<ChunkPos, List<ChunkBlock>> chunkPools;

    public BlockPool(ServerChunkManager chunkManager){
        this.chunkManager = chunkManager;
        this.chunkPools = new HashMap<>();
    }

    public void loadBlocksFor(ServerChunk chunk){
        final List<ChunkBlock> blocks = getBlocks(chunk);
        if(blocks == null)
            return;

        for(ChunkBlock block: blocks)
            chunk.setBlock(block.lx, block.y, block.lz, block.blockData);

        chunkPools.remove(chunk.getPosition());
    }

    public List<ChunkBlock> getBlocks(ServerChunk chunk){
        return chunkPools.get(chunk.getPosition());
    }

    public void setBlock(ChunkPos chunkPos, int lx, int y, int lz, short blockData){
        final List<ChunkBlock> list = chunkPools.getOrDefault(chunkPos, new ArrayList<>());
        list.add(new ChunkBlock(lx, y, lz, blockData));
        chunkPools.put(chunkPos, list);
    }

    public void setBlock(int x, int y, int z, short blockData){
        final ChunkPos chunkPos = new ChunkPos(getChunkPos(x), getChunkPos(z));
        final int lx = getLocalCoord(x);
        final int lz = getLocalCoord(z);

        final ServerChunk chunk = chunkManager.getProcessChunk(chunkPos);
        if(chunk == null)
            setBlock(chunkPos, lx, y, lz, blockData);
        else
            chunk.setBlock(lx, y, lz, blockData);
    }

}
