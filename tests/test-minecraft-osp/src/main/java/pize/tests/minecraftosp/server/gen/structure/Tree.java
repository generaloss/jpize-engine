package pize.tests.minecraftosp.server.gen.structure;

import pize.math.util.Random;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.main.level.structure.Structure;
import pize.tests.minecraftosp.server.chunk.ServerChunk;
import pize.tests.minecraftosp.server.level.ServerLevel;
import pize.tests.minecraftosp.server.level.chunk.BlockPool;

public class Tree extends Structure{

    public void generate(ServerChunk chunk, int x, int y, int z, Random noise){
        final ServerLevel level = chunk.getLevel();
        final BlockPool pool = level.getChunkManager().getBlockPool();
        final short leavesDefaultData = Blocks.OAK_LEAVES.getDefaultData();

        final int logHeight = noise.random(4, 9);
        final int peak = y + logHeight;

        for(int ly = 0; ly < logHeight; ly++)
            pool.setBlock(x, y + ly, z, Blocks.OAK_LOG.getDefaultData());

        // Верхний 1
        level.setBlock(x  , peak  , z  , leavesDefaultData);

        // Окружающие ствол дерева 1х4
        pool.setBlock(x-1, peak  , z  , leavesDefaultData);
        pool.setBlock(x-1, peak-1, z  , leavesDefaultData);
        pool.setBlock(x-1, peak-2, z  , leavesDefaultData);
        pool.setBlock(x-1, peak-3, z  , leavesDefaultData);

        pool.setBlock(x+1, peak  , z  , leavesDefaultData);
        pool.setBlock(x+1, peak-1, z  , leavesDefaultData);
        pool.setBlock(x+1, peak-2, z  , leavesDefaultData);
        pool.setBlock(x+1, peak-3, z  , leavesDefaultData);

        pool.setBlock(x  , peak  , z-1, leavesDefaultData);
        pool.setBlock(x  , peak-1, z-1, leavesDefaultData);
        pool.setBlock(x  , peak-2, z-1, leavesDefaultData);
        pool.setBlock(x  , peak-3, z-1, leavesDefaultData);

        pool.setBlock(x  , peak  , z+1, leavesDefaultData);
        pool.setBlock(x  , peak-1, z+1, leavesDefaultData);
        pool.setBlock(x  , peak-2, z+1, leavesDefaultData);
        pool.setBlock(x  , peak-3, z+1, leavesDefaultData);

        // Другие 1х3
        pool.setBlock(x-1, peak-1, z-1, leavesDefaultData);
        pool.setBlock(x-1, peak-2, z-1, leavesDefaultData);
        pool.setBlock(x-1, peak-3, z-1, leavesDefaultData);

        pool.setBlock(x-1, peak-1, z+1, leavesDefaultData);
        pool.setBlock(x-1, peak-2, z+1, leavesDefaultData);
        pool.setBlock(x-1, peak-3, z+1, leavesDefaultData);

        pool.setBlock(x+1, peak-1, z-1, leavesDefaultData);
        pool.setBlock(x+1, peak-2, z-1, leavesDefaultData);
        pool.setBlock(x+1, peak-3, z-1, leavesDefaultData);

        pool.setBlock(x+1, peak-1, z+1, leavesDefaultData);
        pool.setBlock(x+1, peak-2, z+1, leavesDefaultData);
        pool.setBlock(x+1, peak-3, z+1, leavesDefaultData);

        // Другие по краям 3х2
        pool.setBlock(x-2, peak-2, z-1, leavesDefaultData);
        pool.setBlock(x-2, peak-2, z  , leavesDefaultData);
        pool.setBlock(x-2, peak-2, z+1, leavesDefaultData);
        pool.setBlock(x-2, peak-3, z-1, leavesDefaultData);
        pool.setBlock(x-2, peak-3, z  , leavesDefaultData);
        pool.setBlock(x-2, peak-3, z+1, leavesDefaultData);

        pool.setBlock(x+2, peak-2, z-1, leavesDefaultData);
        pool.setBlock(x+2, peak-2, z  , leavesDefaultData);
        pool.setBlock(x+2, peak-2, z+1, leavesDefaultData);
        pool.setBlock(x+2, peak-3, z-1, leavesDefaultData);
        pool.setBlock(x+2, peak-3, z  , leavesDefaultData);
        pool.setBlock(x+2, peak-3, z+1, leavesDefaultData);

        pool.setBlock(x-1, peak-2, z-2, leavesDefaultData);
        pool.setBlock(x  , peak-2, z-2, leavesDefaultData);
        pool.setBlock(x+1, peak-2, z-2, leavesDefaultData);
        pool.setBlock(x-1, peak-3, z-2, leavesDefaultData);
        pool.setBlock(x  , peak-3, z-2, leavesDefaultData);
        pool.setBlock(x+1, peak-3, z-2, leavesDefaultData);

        pool.setBlock(x-1, peak-2, z+2, leavesDefaultData);
        pool.setBlock(x  , peak-2, z+2, leavesDefaultData);
        pool.setBlock(x+1, peak-2, z+2, leavesDefaultData);
        pool.setBlock(x-1, peak-3, z+2, leavesDefaultData);
        pool.setBlock(x  , peak-3, z+2, leavesDefaultData);
        pool.setBlock(x+1, peak-3, z+2, leavesDefaultData);
    }

}
