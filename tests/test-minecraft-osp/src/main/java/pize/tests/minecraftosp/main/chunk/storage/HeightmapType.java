package pize.tests.minecraftosp.main.chunk.storage;

import pize.tests.minecraftosp.client.block.Blocks;
import pize.tests.minecraftosp.main.block.BlockData;

import java.util.function.Predicate;

public enum HeightmapType{

    HIGHEST(blockID -> blockID == Blocks.AIR.getID()),
    OPAQUE(blockID -> BlockData.getProps(blockID).isLightTranslucent() && blockID != Blocks.WATER.getID() && blockID != Blocks.ICE.getID()),
    SURFACE(blockID -> blockID == Blocks.AIR.getID() || blockID == Blocks.WATER.getID());
    
    
    public final Predicate<Byte> isOpaque;
    
    HeightmapType(Predicate<Byte> isOpaque){
        this.isOpaque = isOpaque;
    }
    
}
