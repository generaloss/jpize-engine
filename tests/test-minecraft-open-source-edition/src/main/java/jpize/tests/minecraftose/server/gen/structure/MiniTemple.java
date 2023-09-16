package jpize.tests.minecraftose.server.gen.structure;

import jpize.math.util.Random;
import jpize.tests.minecraftose.main.level.structure.Structure;
import jpize.tests.minecraftose.server.gen.pool.BlockPool;

public class MiniTemple extends Structure{

    public static void generate(BlockPool pool, int x, int y, int z, Random random){
        loadTo(pool, "mini_temple", x, y, z);
    }

}
