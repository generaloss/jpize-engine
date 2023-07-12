package pize.tests.voxelgame.client.block.vanilla;

import pize.graphics.texture.Region;
import pize.tests.voxelgame.client.block.Blocks;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.shape.BlockCollideShape;
import pize.tests.voxelgame.client.block.model.BlockSolidModel;
import pize.tests.voxelgame.client.block.shape.BlockCursorShape;

import static pize.tests.voxelgame.main.chunk.ChunkUtils.MAX_LIGHT_LEVEL;

public class GrassBlock extends BlockProperties{
    
    public GrassBlock(int id){
        super(id);
        
        solid = true;
        lightLevel = 0;
        opacity = MAX_LIGHT_LEVEL;
        collideShape = BlockCollideShape.SOLID;
        cursorShape = BlockCursorShape.SOLID;
        
        model = new BlockSolidModel(
            new Region(1 / 8F, 3 / 8F, 2 / 8F, 4 / 8F),
            Blocks.DIRT.getModel().asSolid().getSidePxRegion(),
            new Region(1 / 8F, 2 / 8F, 2 / 8F, 3 / 8F)
        );
    }
    
}
