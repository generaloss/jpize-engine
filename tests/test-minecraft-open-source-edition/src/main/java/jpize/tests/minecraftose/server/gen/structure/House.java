package jpize.tests.minecraftose.server.gen.structure;

import jpize.math.util.Random;
import jpize.tests.minecraftose.main.level.structure.Structure;
import jpize.tests.minecraftose.server.gen.pool.BlockPool;

public class House extends Structure{

    public static void generate(BlockPool pool, int x, int y, int z, Random random){
        loadTo(pool, "house", x, y, z);
    }

}
