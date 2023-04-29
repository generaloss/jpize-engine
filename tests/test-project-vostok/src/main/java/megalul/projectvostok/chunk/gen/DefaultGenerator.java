package megalul.projectvostok.chunk.gen;

import pize.math.Maths;
import pize.math.function.FastNoiseLite;
import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.chunk.Chunk;
import megalul.projectvostok.chunk.storage.ChunkHeightUtils;

import static megalul.projectvostok.chunk.ChunkUtils.SIZE;

public class DefaultGenerator implements ChunkGenerator{

    private final FastNoiseLite noise = new FastNoiseLite();

    private DefaultGenerator(){
        noise.setFrequency(0.007F);
        noise.setSeed(22854); //(int) Maths.randomSeed(8));
    }

    @Override
    public void generate(Chunk chunk){
        // Stopwatch timer = new Stopwatch().start();
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++){
                int y = Maths.round( noise.getNoise(i + SIZE * chunk.getPos().x, j + SIZE * chunk.getPos().z) * 16 + 128);
                for(int k = 0; k < y; k++){
                    float n3d = (noise.getNoise(i + SIZE * chunk.getPos().x, k, j + SIZE * chunk.getPos().z) + 1) / 2;
                    if(n3d > (float) k / 16 || n3d < (float) (k - 4) / y)
                        chunk.setBlockFast(i, k, j, k > 60 ? Block.DIRT.getState() : Block.STONE.getState());
                }
            }
        
        ChunkHeightUtils.updateHeightMap(chunk.getStorage());
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++){
                int y = chunk.getStorage().getHeight(i, j);
                if(y > 60)
                    chunk.setBlockFast(i, y, j, Block.GRASS_BLOCK.getState());
            }
        
        chunk.rebuild();
        // System.out.println("Gen: " + timer.getMillis());
    }


    private static DefaultGenerator instance;

    public static DefaultGenerator getInstance(){
        if(instance == null)
            instance = new DefaultGenerator();

        return instance;
    }

}
