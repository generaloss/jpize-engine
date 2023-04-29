package megalul.projectvostok.chunk.storage;

import pize.math.Mathc;
import megalul.projectvostok.block.blocks.Block;

import static megalul.projectvostok.chunk.ChunkUtils.HEIGHT_IDX;
import static megalul.projectvostok.chunk.ChunkUtils.SIZE;

public class ChunkHeightUtils{
    
    public static void updateHeight(ChunkStorage storage, int x, int y, int z, boolean placed){
        int height = storage.getHeight(x, z);
        
        if(y == height && !placed)
            for(height--; storage.getBlockID(x, height, z) == Block.AIR.ID && height > 0; height--);
        else if(y > height && placed)
            height = y;
        
        storage.setHeight(x, z, height);
        updateMaxY(storage);
    }
    
    public static void updateHeightMap(ChunkStorage storage){
        for(int x = 0; x < SIZE; x++)
            for(int z = 0; z < SIZE; z++)
                for(int y = HEIGHT_IDX; y >= 0; y--)
                    if(storage.getBlockID(x, y, z) != Block.AIR.ID){
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
