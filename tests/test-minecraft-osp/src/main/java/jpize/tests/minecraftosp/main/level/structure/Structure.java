package jpize.tests.minecraftosp.main.level.structure;

import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec2f;
import jpize.tests.minecraftosp.client.block.Block;
import jpize.tests.minecraftosp.server.gen.pool.BlockPool;

public class Structure{

    public static void circleFilledXZ(BlockPool pool, int x, int y, int z, float radius, Block block){
        final int intRadius = Maths.ceil(radius);
        for(int i = 0; i < intRadius; i++){
            for(int j = 0; j < intRadius; j++){
                if(Vec2f.len(i, j) >= radius)
                    continue;

                pool.genBlock(x + i, y, z + j, block);
                pool.genBlock(x - i, y, z + j, block);
                pool.genBlock(x - i, y, z - j, block);
                pool.genBlock(x + i, y, z - j, block);
            }
        }
    }

    public static void circleXZ(BlockPool pool, int x, int y, int z, float radius, Block block){
        final int intRadius = Maths.ceil(radius);
        for(int i = 0; i < intRadius; i++){
            for(int j = 0; j < intRadius; j++){
                final float len = Vec2f.len(i, j);
                if( !(len < radius && len >= radius - 1) )
                    continue;

                pool.genBlock(x + i, y, z + j, block);
                pool.genBlock(x - i, y, z + j, block);
                pool.genBlock(x - i, y, z - j, block);
                pool.genBlock(x + i, y, z - j, block);
            }
        }
    }

}
