package pize.tests.voxelgame.client.block.vanilla;

import pize.graphics.texture.Region;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.model.BlockSolidModel;
import pize.tests.voxelgame.client.block.shape.BlockCollideShape;
import pize.tests.voxelgame.client.block.shape.BlockCursorShape;

public class Glass extends BlockProperties{
    
    public Glass(int id){
        super(id);
        
        solid = true;
        lightLevel = 0;
        opacity = 4;
        collideShape = BlockCollideShape.SOLID;
        cursorShape = BlockCursorShape.SOLID;
        
        model = new BlockSolidModel(
            new Region(0 / 8F, 2 / 8F, 1 / 8F, 3 / 8F)
        );
    }
    
}