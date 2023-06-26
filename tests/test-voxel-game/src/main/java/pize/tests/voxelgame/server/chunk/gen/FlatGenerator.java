package pize.tests.voxelgame.server.chunk.gen;

import pize.math.Maths;
import pize.math.function.FastNoiseLite;
import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.base.chunk.storage.HeightmapType;
import pize.tests.voxelgame.server.chunk.ServerChunk;

import static pize.tests.voxelgame.base.chunk.ChunkUtils.HEIGHT;
import static pize.tests.voxelgame.base.chunk.ChunkUtils.SIZE;

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
                    chunk.setBlockFast(i, k, j, Block.STONE.getDefaultState());
        
        chunk.getHeightMap(HeightmapType.SURFACE).update();
        for(int i = 0; i < SIZE; i++)
            for(int j = 0; j < SIZE; j++){
                int y = chunk.getHeightMap(HeightmapType.SURFACE).getHeight(i, j);
                chunk.setBlockFast(i, y, j, Block.GRASS_BLOCK.getDefaultState());
            }
        // System.out.println("Gen: " + timer.getMillis());
        
        for(int lx = 0; lx < SIZE; lx++){
            final int x = lx + baseX;
            
            for(int lz = 0; lz < SIZE; lz++){
                final int z = lz + baseZ;
                
                for(int y = 0; y < HEIGHT; y++)
                    chunk.setLight(lx, y, lz, Maths.round((noiseLight.getNoise(x, y, z) + 1) / 2 * 15));
            }
        }
    }
    
    @Override
    public void generateDecorations(ServerChunk chunk){ }
    
    
    private static FlatGenerator instance;
    
    public static FlatGenerator getInstance(){
        if(instance == null)
            instance = new FlatGenerator();
        
        return instance;
    }
    
}