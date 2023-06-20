package pize.tests.voxelgame.clientserver.chunk.storage;

import pize.tests.voxelgame.client.block.blocks.Block;

import java.util.function.Predicate;

public enum HeightmapType{
    
    SURFACE(blockState->blockState == Block.AIR.ID);
    
    
    public final Predicate<Integer> isOpaque;
    
    HeightmapType(Predicate<Integer> isOpaque){
        this.isOpaque = isOpaque;
    }
    
}
