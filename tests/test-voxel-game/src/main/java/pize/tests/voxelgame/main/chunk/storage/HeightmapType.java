package pize.tests.voxelgame.main.chunk.storage;

import pize.tests.voxelgame.client.block.Blocks;

import java.util.function.Predicate;

public enum HeightmapType{
    
    SURFACE(blockState->blockState == Blocks.AIR.getID());
    
    
    public final Predicate<Integer> isOpaque;
    
    HeightmapType(Predicate<Integer> isOpaque){
        this.isOpaque = isOpaque;
    }
    
}
