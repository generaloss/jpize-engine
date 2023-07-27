package pize.tests.voxelgame.main.chunk.storage;

import pize.tests.voxelgame.main.block.BlockData;
import pize.tests.voxelgame.client.block.Blocks;

import java.util.function.Predicate;

public enum HeightmapType{

    HIGHEST(blockID -> blockID == Blocks.AIR.getID()),
    OPAQUE(blockID -> BlockData.getProps(blockID).isLightTranslucent()),
    SURFACE(blockID -> blockID == Blocks.AIR.getID() || blockID == Blocks.WATER.getID());
    
    
    public final Predicate<Byte> isOpaque;
    
    HeightmapType(Predicate<Byte> isOpaque){
        this.isOpaque = isOpaque;
    }
    
}
