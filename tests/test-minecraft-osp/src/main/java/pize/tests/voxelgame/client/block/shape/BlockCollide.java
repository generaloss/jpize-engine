package pize.tests.voxelgame.client.block.shape;

import pize.physic.BoundingBox3;

public class BlockCollide{
    
    public static final BlockCollide SOLID = new BlockCollide(new BoundingBox3(0, 0, 0, 1, 1, 1));
    
    
    final BoundingBox3[] boxes;
    
    public BlockCollide(BoundingBox3... boxes){
        this.boxes = boxes;
    }
    
    public BoundingBox3[] getBoxes(){
        return boxes;
    }
    
}
