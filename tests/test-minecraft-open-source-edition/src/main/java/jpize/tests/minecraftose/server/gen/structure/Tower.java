package jpize.tests.minecraftose.server.gen.structure;

import jpize.math.util.Random;
import jpize.tests.minecraftose.client.block.Blocks;
import jpize.tests.minecraftose.main.level.structure.Structure;
import jpize.tests.minecraftose.server.chunk.ServerChunk;
import jpize.tests.minecraftose.server.gen.pool.BlockPool;
import jpize.tests.minecraftose.server.level.ServerLevel;

public class Tower{

    public static void generate(BlockPool pool, int x, int y, int z, Random random){
        final int offsetY = -8;
        final int radius = 12;
        final int floors = random.random(6, 12);
        final int floorHeight = 4;
        final int peakY = floors * (floorHeight + 1);

        for(int i = 0; i < floors; i++){
            final int floorBaseY = i * (floorHeight + 1);

            Structure.circleFilledXZ(pool, x, y + offsetY + floorBaseY, z, radius, Blocks.MOSSY_COBBLESTONE);

            for(int j = 0; j < floorHeight; j++)
                Structure.circleXZ(pool, x, y + offsetY + floorBaseY + j + 1, z, radius, Blocks.STONE_BRICKS);
        }

        Structure.circleFilledXZ(pool, x, y + offsetY + peakY, z, radius, Blocks.STONE);

        pool.genBlock(x, y + offsetY + peakY + 1, z, Blocks.GLASS);
        pool.genBlock(x, y + offsetY + peakY + 2, z, Blocks.LAMP);
        // level.getBlockLight().increase(chunk, x, y + offsetY + peakY + 2, z, MAX_LIGHT_LEVEL);
        pool.genBlock(x, y + offsetY + peakY + 3, z, Blocks.WATER);
    }

    private static void pillar(ServerChunk chunk, int x, int y, int z){
        final ServerLevel level = chunk.getLevel();

        for(int i = 0; i < 16; i++){
            level.genBlock(x - 1, y + i, z - 1, Blocks.STONE);
            level.genBlock(x    , y + i, z - 1, Blocks.STONE);
            level.genBlock(x + 1, y + i, z - 1, Blocks.STONE);
            level.genBlock(x - 1, y + i, z    , Blocks.STONE);
            level.genBlock(x    , y + i, z    , Blocks.STONE);
            level.genBlock(x + 1, y + i, z    , Blocks.STONE);
            level.genBlock(x - 1, y + i, z + 1, Blocks.STONE);
            level.genBlock(x    , y + i, z + 1, Blocks.STONE);
            level.genBlock(x + 1, y + i, z + 1, Blocks.STONE);
        }

        level.genBlock(x, y + 16, z, Blocks.GLASS);
        level.genBlock(x, y + 17, z, Blocks.LAMP);
        level.genBlock(x, y + 18, z, Blocks.WATER);
    }

}
