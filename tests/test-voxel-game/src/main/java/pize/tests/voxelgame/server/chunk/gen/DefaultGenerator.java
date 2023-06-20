package pize.tests.voxelgame.server.chunk.gen;

import pize.math.Maths;
import pize.math.function.FastNoiseLite;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.clientserver.chunk.storage.HeightmapType;
import pize.tests.voxelgame.server.chunk.ServerChunk;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.SIZE;

public class DefaultGenerator implements ChunkGenerator{

    private final FastNoiseLite noise = new FastNoiseLite();

    private DefaultGenerator(){
        noise.setFrequency(0.007F);
    }

    @Override
    public void generate(ServerChunk chunk){
        noise.setSeed(chunk.getLevel().getConfiguration().getSeed());
        
        // Stopwatch timer = new Stopwatch().start();
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++){
                int y = Maths.round( noise.getNoise(i + SIZE * chunk.getPosition().x, j + SIZE * chunk.getPosition().z) * 16 + 128);
                for(int k = 0; k < y; k++){
                    float n3d = (noise.getNoise(i + SIZE * chunk.getPosition().x, k, j + SIZE * chunk.getPosition().z) + 1) / 2;
                    if(n3d > (float) k / 16 || n3d < (float) (k - 4) / y)
                        chunk.setBlockFast(i, k, j, k > 60 ? Block.DIRT.getDefaultState() : Block.STONE.getDefaultState());
                }
            }
        
        chunk.getHeightMap(HeightmapType.SURFACE).update();
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++){
                int y = chunk.getHeightMap(HeightmapType.SURFACE).getHeight(i, j);
                if(y > 60)
                    chunk.setBlockFast(i, y, j, Block.GRASS_BLOCK.getDefaultState());
            }
        // System.out.println("Gen: " + timer.getMillis());
    }
    
    
    private static DefaultGenerator instance;

    public static DefaultGenerator getInstance(){
        if(instance == null)
            instance = new DefaultGenerator();

        return instance;
    }

}
