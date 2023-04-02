package megalul.projectvostok.chunk.gen;

import glit.math.Maths;
import glit.math.function.FastNoiseLite;
import glit.util.time.Stopwatch;
import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.chunk.Chunk;

import static megalul.projectvostok.chunk.ChunkUtils.SIZE;

public class DefaultGenerator implements ChunkGenerator{

    private final FastNoiseLite noise = new FastNoiseLite();

    private DefaultGenerator(){
        noise.setFrequency(0.007F);
        noise.setSeed((int) Maths.randomSeed(8));
    }

    @Override
    public void generate(Chunk chunk){
        Stopwatch timer = new Stopwatch().start();
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++){
                int y = Maths.round( noise.getNoise(i + SIZE * chunk.getPos().x, j + SIZE * chunk.getPos().z) * 16 + 128);
                for(int k = 0; k < y; k++){
                    float n3d = (noise.getNoise(i + SIZE * chunk.getPos().x, k, j + SIZE * chunk.getPos().z) + 1) / 2;
                    if(n3d > (float) k / 16 || n3d < (float) (k - 4) / y)
                        chunk.fastSetBlock(i, k, j, Block.DIRT.getState());
                }
                // chunk.setBlock(i, y, j, new BlockState(Block.DIRT));
            }
        
        chunk.getField().updateHeightDepthMap();
        // System.out.println("Gen: " + timer.getMillis());
    }


    private static DefaultGenerator instance;

    public static DefaultGenerator getInstance(){
        if(instance == null)
            instance = new DefaultGenerator();

        return instance;
    }

}
