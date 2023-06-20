package pize.tests.voxelgame.server.chunk.gen;

import pize.tests.voxelgame.client.block.blocks.Block;
import pize.tests.voxelgame.clientserver.chunk.storage.HeightmapType;
import pize.tests.voxelgame.server.chunk.ServerChunk;

import static pize.tests.voxelgame.clientserver.chunk.ChunkUtils.SIZE;

public class FlatGenerator implements ChunkGenerator{
    
    @Override
    public void generate(ServerChunk chunk){
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
    }
    
    
    private static FlatGenerator instance;
    
    public static FlatGenerator getInstance(){
        if(instance == null)
            instance = new FlatGenerator();
        
        return instance;
    }
    
}