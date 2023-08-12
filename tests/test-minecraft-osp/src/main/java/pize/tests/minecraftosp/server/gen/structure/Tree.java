package pize.tests.minecraftosp.server.gen.structure;

import pize.math.Maths;
import pize.math.util.Random;
import pize.math.vecmath.vector.Vec2f;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.main.level.structure.Structure;
import pize.tests.minecraftosp.server.chunk.ServerChunk;
import pize.tests.minecraftosp.server.level.ServerLevel;
import pize.tests.minecraftosp.server.level.chunk.BlockPool;

public class Tree extends Structure{

    private void circleFilledXZ(BlockPool pool, int x, int y, int z, float radius, short blockData){
        final int intRadius = Maths.ceil(radius);
        for(int i = 0; i < intRadius; i++){
            for(int j = 0; j < intRadius; j++){
                if(Vec2f.len(i, j) > radius)
                    continue;

                pool.setBlock(x + i, y, z + j, blockData);
                pool.setBlock(x - i, y, z + j, blockData);
                pool.setBlock(x - i, y, z - j, blockData);
                pool.setBlock(x + i, y, z - j, blockData);
            }
        }
    }


    public void generateSpruceTree(ServerChunk chunk, int x, int y, int z, Random random){
        final ServerLevel level = chunk.getLevel();
        final BlockPool pool = level.getChunkManager().getBlockPool();

        final int logHeight = random.random(10, 16);
        final int peak = y + logHeight;
        final int leavesHeight = random.random(2, 5);

        for(int ly = 0; ly < logHeight; ly++){
            final int height = y + ly;

            if(ly >= leavesHeight){
                final float leavesNorY = (float) ly / (logHeight - leavesHeight);

                final float radius;
                if(leavesNorY < 0.3)
                    radius = random.random(2.2F, 3.8F);
                else if(leavesNorY < 0.7)
                    radius = random.random(0.8F, 3.2F);
                else
                    radius = random.random(0.8F, 2.2F);

                circleFilledXZ(pool, x, height, z, radius, Blocks.SPRUCE_LEAVES.getDefaultData());
            }

            pool.setBlock(x, height, z, Blocks.SPRUCE_LOG.getDefaultData());
        }

        pool.setBlock(x, peak, z, Blocks.SPRUCE_LEAVES.getDefaultData());
    }


    public void generateOakTree(ServerChunk chunk, int x, int y, int z, Random random){
        final ServerLevel level = chunk.getLevel();
        final BlockPool pool = level.getChunkManager().getBlockPool();
        final short leavesDefaultData = Blocks.OAK_LEAVES.getDefaultData();

        final int logHeight = random.random(4, 9);
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


    public void generateBirchTree(ServerChunk chunk, int x, int y, int z, Random random){
        final ServerLevel level = chunk.getLevel();
        final BlockPool pool = level.getChunkManager().getBlockPool();
        final short leavesDefaultData = Blocks.BIRCH_LEAVES.getDefaultData();

        final int logHeight = random.random(5, 10);
        final int peak = y + logHeight;

        for(int ly = 0; ly < logHeight; ly++)
            pool.setBlock(x, y + ly, z, Blocks.BIRCH_LOG.getDefaultData());

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
