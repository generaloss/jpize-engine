package jpize.tests.minecraftosp.server.gen.structure;

import jpize.math.util.Random;
import jpize.tests.minecraftosp.client.block.Blocks;
import jpize.tests.minecraftosp.server.gen.pool.BlockPool;

public class Cactus{

    public static void generate(BlockPool pool, int x, int y, int z, Random random){
        final int cactusHeight = random.random(1, 4);
        for(int i = 0; i < cactusHeight; i++)
            pool.genBlock(x, y + i, z, Blocks.CACTUS);
    }

}
