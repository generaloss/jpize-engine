package pize.tests.minecraftosp.main.chunk.storage;

import pize.tests.minecraftosp.client.block.BlockProps;
import pize.tests.minecraftosp.client.block.Blocks;

import java.util.function.Predicate;

public enum HeightmapType{

    SURFACE(blockProps -> blockProps.getBlock() == Blocks.AIR),
    UNDERWATER_SURFACE(blockProps -> blockProps.getBlock() == Blocks.AIR || blockProps.getBlock() == Blocks.WATER),
    LIGHT_SURFACE(BlockProps::isLightTransparent);

    
    public final Predicate<BlockProps> isOpaque;
    
    HeightmapType(Predicate<BlockProps> isOpaque){
        this.isOpaque = isOpaque;
    }
    
}
