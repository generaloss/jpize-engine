package jpize.tests.mcose.main.level.structure;

import jpize.files.Resource;
import jpize.math.Maths;
import jpize.math.vecmath.vector.Vec2f;
import jpize.math.vecmath.vector.Vec3i;
import jpize.tests.mcose.client.block.Block;
import jpize.tests.mcose.server.gen.pool.BlockPool;
import jpize.util.io.JpizeInputStream;

import java.io.IOException;

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

    public static void loadTo(BlockPool pool, String name, int x, int y, int z){
        // File
        try{
            final Resource file = new Resource("struct/" + name + ".struct");
            final JpizeInputStream inStream = file.getJpizeIn();

            // Read
            final Vec3i size = inStream.readVec3i();

            for(int i = 0; i < size.x; i++)
                for(int j = 0; j < size.y; j++)
                    for(int k = 0; k < size.z; k++){

                        final short block = inStream.readShort();
                        pool.setBlockData(x + i, y + j, z + k, block);
                    }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

}
