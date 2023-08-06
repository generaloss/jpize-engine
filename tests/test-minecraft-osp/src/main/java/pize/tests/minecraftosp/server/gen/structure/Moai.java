package pize.tests.minecraftosp.server.gen.structure;

import pize.math.util.Random;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.main.level.structure.Structure;
import pize.tests.minecraftosp.server.chunk.ServerChunk;
import pize.tests.minecraftosp.server.level.ServerLevel;

public class Moai extends Structure{

    public void generate(ServerChunk chunk, int x, int y, int z, Random noise){
        final ServerLevel level = chunk.getLevel();
        final short stoneDefaultData = Blocks.STONE.getDefaultData();

        for(int i = 0; i < 16; i++){
            level.setBlock(x - 1, y + i, z - 1, stoneDefaultData);
            level.setBlock(x    , y + i, z - 1, stoneDefaultData);
            level.setBlock(x + 1, y + i, z - 1, stoneDefaultData);
            level.setBlock(x - 1, y + i, z    , stoneDefaultData);
            level.setBlock(x    , y + i, z    , stoneDefaultData);
            level.setBlock(x + 1, y + i, z    , stoneDefaultData);
            level.setBlock(x - 1, y + i, z + 1, stoneDefaultData);
            level.setBlock(x    , y + i, z + 1, stoneDefaultData);
            level.setBlock(x + 1, y + i, z + 1, stoneDefaultData);
        }

        level.setBlock(x, y + 16, z, Blocks.GLASS.getDefaultData());
        level.setBlock(x, y + 17, z, Blocks.LAMP.getDefaultData());
        level.setBlock(x, y + 17, z, Blocks.WATER.getDefaultData());
    }

}
