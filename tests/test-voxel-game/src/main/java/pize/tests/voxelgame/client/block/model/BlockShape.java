package pize.tests.voxelgame.client.block.model;

import pize.physic.BoundingBox;

public class BlockShape{
    
    final BoundingBox[] boxes;
    
    public BlockShape(BoundingBox... boxes){
        this.boxes = boxes;
    }
    
    public BoundingBox[] getBoxes(){
        return boxes;
    }
    
}
