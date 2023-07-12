package pize.tests.voxelgame.client.block.shape;

import pize.physic.BoundingBox3;

public class BlockCollideShape{
    
    public static final BlockCollideShape SOLID = new BlockCollideShape(new BoundingBox3(0, 0, 0, 1, 1, 1));
    
    
    final BoundingBox3[] boxes;
    
    public BlockCollideShape(BoundingBox3... boxes){
        this.boxes = boxes;
    }
    
    public BoundingBox3[] getBoxes(){
        return boxes;
    }
    
}
