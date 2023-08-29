package pize.tests.minecraftosp.server.gen;

import pize.math.Maths;
import pize.math.function.FastNoiseLite;
import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.main.chunk.storage.HeightmapType;
import pize.tests.minecraftosp.server.chunk.ServerChunk;

import static pize.tests.minecraftosp.main.chunk.ChunkUtils.*;

public class FlatGenerator implements ChunkGenerator{
    
    private final FastNoiseLite noiseLight = new FastNoiseLite();
    
    public FlatGenerator(){
        noiseLight.setFrequency(0.03F);
    }
    
    
    @Override
    public void generate(ServerChunk chunk){
        final int baseX = SIZE * chunk.getPosition().x;
        final int baseZ = SIZE * chunk.getPosition().z;
        
        // Stopwatch timer = new Stopwatch().start();
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++)
                for(int k = 0; k < 5; k++)
                    chunk.setBlockDec(i, k, j, Blocks.STONE);
        
        chunk.getHeightMap(HeightmapType.SURFACE).update();
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++){
                int y = chunk.getHeightMap(HeightmapType.SURFACE).getHeight(i, j);
                chunk.setBlockDec(i, y, j, Blocks.GRASS_BLOCK);
            }
        // System.out.println("Gen: " + timer.getMillis());
        
        for(int lx = 0; lx < SIZE; lx++){
            final int x = lx + baseX;
            
            for(int lz = 0; lz < SIZE; lz++){
                final int z = lz + baseZ;
                
                for(int y = 0; y < HEIGHT; y++)
                    chunk.setSkyLight(lx, y, lz, Maths.round((noiseLight.getNoise(x, y, z) + 1) / 2 * MAX_LIGHT_LEVEL));
            }
        }
    }

    @Override
    public void decorate(ServerChunk chunk, boolean other){ }

    private static FlatGenerator instance;
    
    public static FlatGenerator getInstance(){
        if(instance == null)
            instance = new FlatGenerator();
        
        return instance;
    }

    @Override
    public String getID(){
        return "flat";
    }

}