package pize.tests.voxelgame.client.block.vanilla;

import pize.graphics.util.BufferBuilder;
import pize.tests.voxelgame.client.block.BlockProperties;
import pize.tests.voxelgame.client.block.model.BlockCustomModel;
import pize.tests.voxelgame.client.block.shape.BlockCursorShape;

public class Grass extends BlockProperties{
    
    public Grass(int id){
        super(id);
        
        solid = false;
        lightLevel = 0;
        opacity = 4;
        collideShape = null;
        cursorShape = BlockCursorShape.GRASS;
        
        // Model
        final BlockCustomModel model = new BlockCustomModel();
        final BufferBuilder builder = new BufferBuilder();
        builder.quad(0, 1, 0,  0, 0, 0,  1, 0, 1,  1, 1, 1,  5 / 8F, 2 / 8F,  6 / 8F, 3 / 8F);
        builder.quad(1, 1, 0,  1, 0, 0,  0, 0, 1,  0, 1, 1,  5 / 8F, 2 / 8F,  6 / 8F, 3 / 8F);
        builder.end(model.getVertices());
        this.model = model;
    }
    
}