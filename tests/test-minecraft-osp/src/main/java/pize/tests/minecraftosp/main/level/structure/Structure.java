package pize.tests.minecraftosp.main.level.structure;

import pize.math.Maths;
import pize.math.vecmath.vector.Vec2f;
import pize.tests.minecraftosp.client.block.Block;
import pize.tests.minecraftosp.server.chunk.ServerChunk;
import pize.tests.minecraftosp.server.level.ServerLevel;

public class Structure{

    protected void circleFilledXZ(ServerChunk chunk, boolean others, ServerLevel level, int x, int y, int z, float radius, Block block){
        final int intRadius = Maths.ceil(radius);
        for(int i = 0; i < intRadius; i++){
            for(int j = 0; j < intRadius; j++){
                if(Vec2f.len(i, j) >= radius)
                    continue;

                level.setBlockDec(chunk, others, x + i, y, z + j, block);
                level.setBlockDec(chunk, others, x - i, y, z + j, block);
                level.setBlockDec(chunk, others, x - i, y, z - j, block);
                level.setBlockDec(chunk, others, x + i, y, z - j, block);
            }
        }
    }

    protected void circleXZ(ServerChunk chunk, boolean others, ServerLevel level, int x, int y, int z, float radius, Block block){
        final int intRadius = Maths.ceil(radius);
        for(int i = 0; i < intRadius; i++){
            for(int j = 0; j < intRadius; j++){
                final float len = Vec2f.len(i, j);
                if( !(len < radius && len >= radius - 1) )
                    continue;

                level.setBlockDec(chunk, others, x + i, y, z + j, block);
                level.setBlockDec(chunk, others, x - i, y, z + j, block);
                level.setBlockDec(chunk, others, x - i, y, z - j, block);
                level.setBlockDec(chunk, others, x + i, y, z - j, block);
            }
        }
    }

}
