package pize.tests.minecraftosp.server.gen.structure;

import pize.math.util.Random;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.main.level.structure.Structure;
import pize.tests.minecraftosp.server.chunk.ServerChunk;
import pize.tests.minecraftosp.server.level.ServerLevel;

public class Tree extends Structure{

    public void generateSpruceTree(ServerChunk chunk, int x, int y, int z, Random random){
        final ServerLevel level = chunk.getLevel();

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

                circleFilledXZ(level, x, height, z, radius, Blocks.SPRUCE_LEAVES);
            }

            level.setBlockFast(x, height, z, Blocks.SPRUCE_LOG);
        }

        level.setBlockFast(x, peak, z, Blocks.SPRUCE_LEAVES);
    }


    public void generateOakTree(ServerChunk chunk, int x, int y, int z, Random random){
        final ServerLevel level = chunk.getLevel();

        final int logHeight = random.random(4, 9);
        final int peak = y + logHeight;

        // Верхний 1
        level.setBlockFast(x  , peak  , z  , Blocks.OAK_LEAVES);

        // Окружающие ствол дерева 1х4
        level.setBlockFast(x-1, peak  , z  , Blocks.OAK_LEAVES);
        level.setBlockFast(x-1, peak-1, z  , Blocks.OAK_LEAVES);
        level.setBlockFast(x-1, peak-2, z  , Blocks.OAK_LEAVES);
        level.setBlockFast(x-1, peak-3, z  , Blocks.OAK_LEAVES);

        level.setBlockFast(x+1, peak  , z  , Blocks.OAK_LEAVES);
        level.setBlockFast(x+1, peak-1, z  , Blocks.OAK_LEAVES);
        level.setBlockFast(x+1, peak-2, z  , Blocks.OAK_LEAVES);
        level.setBlockFast(x+1, peak-3, z  , Blocks.OAK_LEAVES);

        level.setBlockFast(x  , peak  , z-1, Blocks.OAK_LEAVES);
        level.setBlockFast(x  , peak-1, z-1, Blocks.OAK_LEAVES);
        level.setBlockFast(x  , peak-2, z-1, Blocks.OAK_LEAVES);
        level.setBlockFast(x  , peak-3, z-1, Blocks.OAK_LEAVES);

        level.setBlockFast(x  , peak  , z+1, Blocks.OAK_LEAVES);
        level.setBlockFast(x  , peak-1, z+1, Blocks.OAK_LEAVES);
        level.setBlockFast(x  , peak-2, z+1, Blocks.OAK_LEAVES);
        level.setBlockFast(x  , peak-3, z+1, Blocks.OAK_LEAVES);

        // Другие 1х3
        level.setBlockFast(x-1, peak-1, z-1, Blocks.OAK_LEAVES);
        level.setBlockFast(x-1, peak-2, z-1, Blocks.OAK_LEAVES);
        level.setBlockFast(x-1, peak-3, z-1, Blocks.OAK_LEAVES);

        level.setBlockFast(x-1, peak-1, z+1, Blocks.OAK_LEAVES);
        level.setBlockFast(x-1, peak-2, z+1, Blocks.OAK_LEAVES);
        level.setBlockFast(x-1, peak-3, z+1, Blocks.OAK_LEAVES);

        level.setBlockFast(x+1, peak-1, z-1, Blocks.OAK_LEAVES);
        level.setBlockFast(x+1, peak-2, z-1, Blocks.OAK_LEAVES);
        level.setBlockFast(x+1, peak-3, z-1, Blocks.OAK_LEAVES);

        level.setBlockFast(x+1, peak-1, z+1, Blocks.OAK_LEAVES);
        level.setBlockFast(x+1, peak-2, z+1, Blocks.OAK_LEAVES);
        level.setBlockFast(x+1, peak-3, z+1, Blocks.OAK_LEAVES);

        // Другие по краям 3х2
        level.setBlockFast(x-2, peak-2, z-1, Blocks.OAK_LEAVES);
        level.setBlockFast(x-2, peak-2, z  , Blocks.OAK_LEAVES);
        level.setBlockFast(x-2, peak-2, z+1, Blocks.OAK_LEAVES);
        level.setBlockFast(x-2, peak-3, z-1, Blocks.OAK_LEAVES);
        level.setBlockFast(x-2, peak-3, z  , Blocks.OAK_LEAVES);
        level.setBlockFast(x-2, peak-3, z+1, Blocks.OAK_LEAVES);

        level.setBlockFast(x+2, peak-2, z-1, Blocks.OAK_LEAVES);
        level.setBlockFast(x+2, peak-2, z  , Blocks.OAK_LEAVES);
        level.setBlockFast(x+2, peak-2, z+1, Blocks.OAK_LEAVES);
        level.setBlockFast(x+2, peak-3, z-1, Blocks.OAK_LEAVES);
        level.setBlockFast(x+2, peak-3, z  , Blocks.OAK_LEAVES);
        level.setBlockFast(x+2, peak-3, z+1, Blocks.OAK_LEAVES);

        level.setBlockFast(x-1, peak-2, z-2, Blocks.OAK_LEAVES);
        level.setBlockFast(x  , peak-2, z-2, Blocks.OAK_LEAVES);
        level.setBlockFast(x+1, peak-2, z-2, Blocks.OAK_LEAVES);
        level.setBlockFast(x-1, peak-3, z-2, Blocks.OAK_LEAVES);
        level.setBlockFast(x  , peak-3, z-2, Blocks.OAK_LEAVES);
        level.setBlockFast(x+1, peak-3, z-2, Blocks.OAK_LEAVES);

        level.setBlockFast(x-1, peak-2, z+2, Blocks.OAK_LEAVES);
        level.setBlockFast(x  , peak-2, z+2, Blocks.OAK_LEAVES);
        level.setBlockFast(x+1, peak-2, z+2, Blocks.OAK_LEAVES);
        level.setBlockFast(x-1, peak-3, z+2, Blocks.OAK_LEAVES);
        level.setBlockFast(x  , peak-3, z+2, Blocks.OAK_LEAVES);
        level.setBlockFast(x+1, peak-3, z+2, Blocks.OAK_LEAVES);

        for(int ly = 0; ly < logHeight; ly++)
            level.setBlockFast(x, y + ly, z, Blocks.OAK_LOG);
    }


    public void generateBirchTree(ServerChunk chunk, int x, int y, int z, Random random){
        final ServerLevel level = chunk.getLevel();

        final int logHeight = random.random(5, 10);
        final int peak = y + logHeight;

        // Верхний 1
        level.setBlockFast(x  , peak  , z  , Blocks.BIRCH_LEAVES);

        // Окружающие ствол дерева 1х4
        level.setBlockFast(x-1, peak  , z  , Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-1, peak-1, z  , Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-1, peak-2, z  , Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-1, peak-3, z  , Blocks.BIRCH_LEAVES);

        level.setBlockFast(x+1, peak  , z  , Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+1, peak-1, z  , Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+1, peak-2, z  , Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+1, peak-3, z  , Blocks.BIRCH_LEAVES);

        level.setBlockFast(x  , peak  , z-1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x  , peak-1, z-1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x  , peak-2, z-1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x  , peak-3, z-1, Blocks.BIRCH_LEAVES);

        level.setBlockFast(x  , peak  , z+1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x  , peak-1, z+1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x  , peak-2, z+1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x  , peak-3, z+1, Blocks.BIRCH_LEAVES);

        // Другие 1х3
        level.setBlockFast(x-1, peak-1, z-1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-1, peak-2, z-1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-1, peak-3, z-1, Blocks.BIRCH_LEAVES);

        level.setBlockFast(x-1, peak-1, z+1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-1, peak-2, z+1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-1, peak-3, z+1, Blocks.BIRCH_LEAVES);

        level.setBlockFast(x+1, peak-1, z-1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+1, peak-2, z-1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+1, peak-3, z-1, Blocks.BIRCH_LEAVES);

        level.setBlockFast(x+1, peak-1, z+1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+1, peak-2, z+1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+1, peak-3, z+1, Blocks.BIRCH_LEAVES);

        // Другие по краям 3х2
        level.setBlockFast(x-2, peak-2, z-1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-2, peak-2, z  , Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-2, peak-2, z+1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-2, peak-3, z-1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-2, peak-3, z  , Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-2, peak-3, z+1, Blocks.BIRCH_LEAVES);

        level.setBlockFast(x+2, peak-2, z-1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+2, peak-2, z  , Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+2, peak-2, z+1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+2, peak-3, z-1, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+2, peak-3, z  , Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+2, peak-3, z+1, Blocks.BIRCH_LEAVES);

        level.setBlockFast(x-1, peak-2, z-2, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x  , peak-2, z-2, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+1, peak-2, z-2, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-1, peak-3, z-2, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x  , peak-3, z-2, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+1, peak-3, z-2, Blocks.BIRCH_LEAVES);

        level.setBlockFast(x-1, peak-2, z+2, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x  , peak-2, z+2, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+1, peak-2, z+2, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x-1, peak-3, z+2, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x  , peak-3, z+2, Blocks.BIRCH_LEAVES);
        level.setBlockFast(x+1, peak-3, z+2, Blocks.BIRCH_LEAVES);

        for(int ly = 0; ly < logHeight; ly++)
            level.setBlockFast(x, y + ly, z, Blocks.BIRCH_LOG);
    }

}
