package jpize.tests.mcose.server.gen.structure;

import jpize.math.util.Random;
import jpize.tests.mcose.main.level.structure.Structure;
import jpize.tests.mcose.server.gen.pool.BlockPool;

public class House extends Structure{

    public static void generate(BlockPool pool, int x, int y, int z, Random random){
        loadTo(pool, "house", x, y, z);
    }

}
