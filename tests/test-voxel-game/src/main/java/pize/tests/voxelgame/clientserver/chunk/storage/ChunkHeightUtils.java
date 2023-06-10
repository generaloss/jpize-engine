package pize.tests.voxelgame.clientserver.chunk.storage;

import pize.tests.voxelgame.client.block.BlockState;
import pize.tests.voxelgame.clientserver.chunk.ChunkUtils;
import pize.math.Mathc;
import pize.tests.voxelgame.client.block.blocks.Block;

public class ChunkHeightUtils{
    
    public static void updateHeight(ChunkStorage storage, int x, int y, int z, boolean placed){
        int height = storage.getHeight(x, z);
        
        if(y == height && !placed)
            for(height--; BlockState.getID(storage.getBlock(x, height, z)) == Block.AIR.ID && height > 0; height--);
        else if(y > height && placed)
            height = y;
        
        storage.setHeight(x, z, height);
        updateMaxY(storage);
    }
    
    public static void updateHeightMap(ChunkStorage storage){
        for(int x = 0; x < ChunkUtils.SIZE; x++)
            for(int z = 0; z < ChunkUtils.SIZE; z++)
                for(int y = ChunkUtils.HEIGHT_IDX; y >= 0; y--)
                    if(BlockState.getID(storage.getBlock(x, y, z)) != Block.AIR.ID){
                        storage.setHeight(x, z, y);
                        break;
                    }
        
        updateMaxY(storage);
    }
    
    public static void updateMaxY(ChunkStorage storage){
        storage.maxY = 0;
        for(short height: storage.heightMap)
            storage.maxY = Mathc.max(height, storage.maxY);
    }
    
}
