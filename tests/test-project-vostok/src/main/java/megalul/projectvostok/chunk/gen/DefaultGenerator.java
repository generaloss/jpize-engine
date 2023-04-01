package megalul.projectvostok.chunk.gen;

import glit.math.Maths;
import glit.math.function.FastNoiseLite;
import megalul.projectvostok.block.BlockState;
import megalul.projectvostok.block.blocks.Block;
import megalul.projectvostok.chunk.Chunk;

import static megalul.projectvostok.chunk.ChunkUtils.*;

public class DefaultGenerator implements ChunkGenerator{

    private final FastNoiseLite noise = new FastNoiseLite();

    private DefaultGenerator(){
        noise.setFrequency(0.007F);
        noise.setSeed((int) Maths.randomSeed(8));
    }

    @Override
    public void generate(Chunk chunk){
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++){
                int y = Maths.round( noise.getNoise(i + SIZE * chunk.getPos().x, j + SIZE * chunk.getPos().z) * 16 + 128);
                chunk.setBlock(i, y, j, new BlockState(Block.DIRT));
            }
    }


    private static DefaultGenerator instance;

    public static DefaultGenerator getInstance(){
        if(instance == null)
            instance = new DefaultGenerator();

        return instance;
    }

}
