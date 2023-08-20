package pize.tests.minecraftosp.server.gen.structure;

import pize.math.util.Random;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.main.level.structure.Structure;
import pize.tests.minecraftosp.server.chunk.ServerChunk;
import pize.tests.minecraftosp.server.level.ServerLevel;

public class Tower extends Structure{

    public void generate(ServerChunk chunk, int x, int y, int z, Random random){
        final ServerLevel level = chunk.getLevel();

        final int offsetY = -8;
        final int radius = 12;
        final int floors = random.random(6, 12);
        final int floorHeight = 4;
        final int peakY = floors * (floorHeight + 1);

        for(int i = 0; i < floors; i++){
            final int floorBaseY = i * (floorHeight + 1);

            circleFilledXZ(level, x, y + offsetY + floorBaseY, z, radius, Blocks.STONE);

            for(int j = 0; j < floorHeight; j++)
                circleXZ(level, x, y + offsetY + floorBaseY + j + 1, z, radius, Blocks.STONE);
        }

        circleFilledXZ(level, x, y + offsetY + peakY, z, radius, Blocks.STONE);

        level.setBlock(x, y + offsetY + peakY + 1, z, Blocks.GLASS);
        level.setBlock(x, y + offsetY + peakY + 2, z, Blocks.LAMP);
        // level.getBlockLight().increase(chunk, x, y + offsetY + peakY + 2, z, MAX_LIGHT_LEVEL);
        level.setBlock(x, y + offsetY + peakY + 3, z, Blocks.WATER);
    }

    private void pillar(ServerChunk chunk, int x, int y, int z){
        final ServerLevel level = chunk.getLevel();

        for(int i = 0; i < 16; i++){
            level.setBlock(x - 1, y + i, z - 1, Blocks.STONE);
            level.setBlock(x    , y + i, z - 1, Blocks.STONE);
            level.setBlock(x + 1, y + i, z - 1, Blocks.STONE);
            level.setBlock(x - 1, y + i, z    , Blocks.STONE);
            level.setBlock(x    , y + i, z    , Blocks.STONE);
            level.setBlock(x + 1, y + i, z    , Blocks.STONE);
            level.setBlock(x - 1, y + i, z + 1, Blocks.STONE);
            level.setBlock(x    , y + i, z + 1, Blocks.STONE);
            level.setBlock(x + 1, y + i, z + 1, Blocks.STONE);
        }

        level.setBlock(x, y + 16, z, Blocks.GLASS);
        level.setBlock(x, y + 17, z, Blocks.LAMP);
        level.setBlock(x, y + 18, z, Blocks.WATER);
    }

}
