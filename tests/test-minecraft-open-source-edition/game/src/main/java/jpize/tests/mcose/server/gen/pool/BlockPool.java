package jpize.tests.mcose.server.gen.pool;

import jpize.tests.mcose.client.block.Block;
import jpize.tests.mcose.main.chunk.storage.ChunkPos;
import jpize.tests.mcose.server.chunk.ServerChunk;
import jpize.tests.mcose.server.level.ServerLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static jpize.tests.mcose.main.chunk.ChunkUtils.getChunkPos;
import static jpize.tests.mcose.main.chunk.ChunkUtils.getLocalCoord;

public class BlockPool{

    private final ServerLevel level;
    private final Map<ChunkPos, List<ChunkBlock>> chunkPools;
    private ServerChunk generatingChunk;

    public BlockPool(ServerLevel level){
        this.level = level;
        this.chunkPools = new HashMap<>();
    }

    public void loadBlocksFor(ServerChunk chunk){
        final List<ChunkBlock> blocks = getBlocks(chunk);
        if(blocks == null)
            return;

        for(ChunkBlock block: blocks)
            //if(chunk.getBlock(block.lx, block.y, block.lz) == Blocks.AIR)
                chunk.setBlockDataFast(block.lx, block.y, block.lz, block.blockData);

        chunkPools.remove(chunk.getPosition());
    }

    public void setGeneratingChunk(ServerChunk chunk){
        this.generatingChunk = chunk;
    }

    public List<ChunkBlock> getBlocks(ServerChunk chunk){
        return chunkPools.get(chunk.getPosition());
    }

    public void putBlock(ChunkPos chunkPos, int lx, int y, int lz, short blockData){
        final List<ChunkBlock> list = chunkPools.getOrDefault(chunkPos, new ArrayList<>());
        list.add(new ChunkBlock(lx, y, lz, blockData));
        chunkPools.put(chunkPos, list);
    }

    public void setBlockData(int x, int y, int z, short blockData){
        final ChunkPos chunkPos = new ChunkPos(getChunkPos(x), getChunkPos(z));
        final int lx = getLocalCoord(x);
        final int lz = getLocalCoord(z);

        final ServerChunk chunk = level.getChunkManager().getGeneratingChunk(chunkPos);
        if(chunk == null || chunk != generatingChunk)
            this.putBlock(chunkPos, lx, y, lz, blockData);
        else
            chunk.setBlockDataFast(lx, y, lz, blockData);
    }

    public void genBlock(int x, int y, int z, Block block){
        this.setBlockData(x, y, z, block.getDefaultData());
    }

}
