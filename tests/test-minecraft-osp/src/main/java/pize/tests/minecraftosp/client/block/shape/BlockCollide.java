package pize.tests.minecraftosp.client.block.shape;

import pize.physic.BoundingBox3f;

public class BlockCollide{

    private static final float CACTUS_SIDE_OFFSET = 1F / 16;
    
    public static final BlockCollide SOLID = new BlockCollide(new BoundingBox3f(0, 0, 0, 1, 1, 1));
    public static final BlockCollide CACTUS = new BlockCollide(new BoundingBox3f(CACTUS_SIDE_OFFSET, 0, CACTUS_SIDE_OFFSET, 1 - CACTUS_SIDE_OFFSET, 1, 1 - CACTUS_SIDE_OFFSET));

    
    final BoundingBox3f[] boxes;
    
    public BlockCollide(BoundingBox3f... boxes){
        this.boxes = boxes;
    }
    
    public BoundingBox3f[] getBoxes(){
        return boxes;
    }
    
}
