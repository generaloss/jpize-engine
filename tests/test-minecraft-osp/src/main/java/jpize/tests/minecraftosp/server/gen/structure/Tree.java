package jpize.tests.minecraftosp.server.gen.structure;

import jpize.math.util.Random;
import jpize.tests.minecraftosp.client.block.Blocks;
import jpize.tests.minecraftosp.main.level.structure.Structure;
import jpize.tests.minecraftosp.server.gen.pool.BlockPool;

public class Tree{

    public static void generateSpruceTree(BlockPool pool, int x, int y, int z, Random random){
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

                Structure.circleFilledXZ(pool, x, height, z, radius, Blocks.SPRUCE_LEAVES);
            }

            pool.genBlock(x, height, z, Blocks.SPRUCE_LOG);
        }

        pool.genBlock(x, peak, z, Blocks.SPRUCE_LEAVES);
    }


    public static void generateOakTree(BlockPool pool, int x, int y, int z, Random random){
        final int logHeight = random.random(4, 9);
        final int peak = y + logHeight;

        // Верхний 1
        pool.genBlock(x  , peak  , z  , Blocks.OAK_LEAVES);

        // Окружающие ствол дерева 1х4
        pool.genBlock(x-1, peak  , z  , Blocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-1, z  , Blocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-2, z  , Blocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-3, z  , Blocks.OAK_LEAVES);

        pool.genBlock(x+1, peak  , z  , Blocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-1, z  , Blocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-2, z  , Blocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-3, z  , Blocks.OAK_LEAVES);

        pool.genBlock(x  , peak  , z-1, Blocks.OAK_LEAVES);
        pool.genBlock(x  , peak-1, z-1, Blocks.OAK_LEAVES);
        pool.genBlock(x  , peak-2, z-1, Blocks.OAK_LEAVES);
        pool.genBlock(x  , peak-3, z-1, Blocks.OAK_LEAVES);

        pool.genBlock(x  , peak  , z+1, Blocks.OAK_LEAVES);
        pool.genBlock(x  , peak-1, z+1, Blocks.OAK_LEAVES);
        pool.genBlock(x  , peak-2, z+1, Blocks.OAK_LEAVES);
        pool.genBlock(x  , peak-3, z+1, Blocks.OAK_LEAVES);

        // Другие 1х3
        pool.genBlock(x-1, peak-1, z-1, Blocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-2, z-1, Blocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-3, z-1, Blocks.OAK_LEAVES);

        pool.genBlock(x-1, peak-1, z+1, Blocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-2, z+1, Blocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-3, z+1, Blocks.OAK_LEAVES);

        pool.genBlock(x+1, peak-1, z-1, Blocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-2, z-1, Blocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-3, z-1, Blocks.OAK_LEAVES);

        pool.genBlock(x+1, peak-1, z+1, Blocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-2, z+1, Blocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-3, z+1, Blocks.OAK_LEAVES);

        // Другие по краям 3х2
        pool.genBlock(x-2, peak-2, z-1, Blocks.OAK_LEAVES);
        pool.genBlock(x-2, peak-2, z  , Blocks.OAK_LEAVES);
        pool.genBlock(x-2, peak-2, z+1, Blocks.OAK_LEAVES);
        pool.genBlock(x-2, peak-3, z-1, Blocks.OAK_LEAVES);
        pool.genBlock(x-2, peak-3, z  , Blocks.OAK_LEAVES);
        pool.genBlock(x-2, peak-3, z+1, Blocks.OAK_LEAVES);

        pool.genBlock(x+2, peak-2, z-1, Blocks.OAK_LEAVES);
        pool.genBlock(x+2, peak-2, z  , Blocks.OAK_LEAVES);
        pool.genBlock(x+2, peak-2, z+1, Blocks.OAK_LEAVES);
        pool.genBlock(x+2, peak-3, z-1, Blocks.OAK_LEAVES);
        pool.genBlock(x+2, peak-3, z  , Blocks.OAK_LEAVES);
        pool.genBlock(x+2, peak-3, z+1, Blocks.OAK_LEAVES);

        pool.genBlock(x-1, peak-2, z-2, Blocks.OAK_LEAVES);
        pool.genBlock(x  , peak-2, z-2, Blocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-2, z-2, Blocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-3, z-2, Blocks.OAK_LEAVES);
        pool.genBlock(x  , peak-3, z-2, Blocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-3, z-2, Blocks.OAK_LEAVES);

        pool.genBlock(x-1, peak-2, z+2, Blocks.OAK_LEAVES);
        pool.genBlock(x  , peak-2, z+2, Blocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-2, z+2, Blocks.OAK_LEAVES);
        pool.genBlock(x-1, peak-3, z+2, Blocks.OAK_LEAVES);
        pool.genBlock(x  , peak-3, z+2, Blocks.OAK_LEAVES);
        pool.genBlock(x+1, peak-3, z+2, Blocks.OAK_LEAVES);

        for(int ly = 0; ly < logHeight; ly++)
            pool.genBlock(x, y + ly, z, Blocks.OAK_LOG);
    }


    public static void generateBirchTree(BlockPool pool, int x, int y, int z, Random random){
        final int logHeight = random.random(5, 10);
        final int peak = y + logHeight;

        // Верхний 1
        pool.genBlock(x  , peak  , z  , Blocks.BIRCH_LEAVES);

        // Окружающие ствол дерева 1х4
        pool.genBlock(x-1, peak  , z  , Blocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-1, z  , Blocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-2, z  , Blocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-3, z  , Blocks.BIRCH_LEAVES);

        pool.genBlock(x+1, peak  , z  , Blocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-1, z  , Blocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-2, z  , Blocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-3, z  , Blocks.BIRCH_LEAVES);

        pool.genBlock(x  , peak  , z-1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-1, z-1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-2, z-1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-3, z-1, Blocks.BIRCH_LEAVES);

        pool.genBlock(x  , peak  , z+1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-1, z+1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-2, z+1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-3, z+1, Blocks.BIRCH_LEAVES);

        // Другие 1х3
        pool.genBlock(x-1, peak-1, z-1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-2, z-1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-3, z-1, Blocks.BIRCH_LEAVES);

        pool.genBlock(x-1, peak-1, z+1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-2, z+1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-3, z+1, Blocks.BIRCH_LEAVES);

        pool.genBlock(x+1, peak-1, z-1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-2, z-1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-3, z-1, Blocks.BIRCH_LEAVES);

        pool.genBlock(x+1, peak-1, z+1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-2, z+1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-3, z+1, Blocks.BIRCH_LEAVES);

        // Другие по краям 3х2
        pool.genBlock(x-2, peak-2, z-1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x-2, peak-2, z  , Blocks.BIRCH_LEAVES);
        pool.genBlock(x-2, peak-2, z+1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x-2, peak-3, z-1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x-2, peak-3, z  , Blocks.BIRCH_LEAVES);
        pool.genBlock(x-2, peak-3, z+1, Blocks.BIRCH_LEAVES);

        pool.genBlock(x+2, peak-2, z-1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x+2, peak-2, z  , Blocks.BIRCH_LEAVES);
        pool.genBlock(x+2, peak-2, z+1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x+2, peak-3, z-1, Blocks.BIRCH_LEAVES);
        pool.genBlock(x+2, peak-3, z  , Blocks.BIRCH_LEAVES);
        pool.genBlock(x+2, peak-3, z+1, Blocks.BIRCH_LEAVES);

        pool.genBlock(x-1, peak-2, z-2, Blocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-2, z-2, Blocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-2, z-2, Blocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-3, z-2, Blocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-3, z-2, Blocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-3, z-2, Blocks.BIRCH_LEAVES);

        pool.genBlock(x-1, peak-2, z+2, Blocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-2, z+2, Blocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-2, z+2, Blocks.BIRCH_LEAVES);
        pool.genBlock(x-1, peak-3, z+2, Blocks.BIRCH_LEAVES);
        pool.genBlock(x  , peak-3, z+2, Blocks.BIRCH_LEAVES);
        pool.genBlock(x+1, peak-3, z+2, Blocks.BIRCH_LEAVES);

        for(int ly = 0; ly < logHeight; ly++)
            pool.genBlock(x, y + ly, z, Blocks.BIRCH_LOG);
    }

}
