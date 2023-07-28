package pize.tests.minecraftosp.client.block.shape;

import pize.physic.BoundingBox3f;

public class BlockCollide{
    
    public static final BlockCollide SOLID = new BlockCollide(new BoundingBox3f(0, 0, 0, 1, 1, 1));
    
    
    final BoundingBox3f[] boxes;
    
    public BlockCollide(BoundingBox3f... boxes){
        this.boxes = boxes;
    }
    
    public BoundingBox3f[] getBoxes(){
        return boxes;
    }
    
}
