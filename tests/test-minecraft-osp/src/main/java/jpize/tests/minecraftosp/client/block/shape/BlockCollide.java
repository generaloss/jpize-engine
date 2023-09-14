package jpize.tests.minecraftosp.client.block.shape;

import jpize.physic.AxisAlignedBox;

public class BlockCollide{

    private static final float CACTUS_SIDE_OFFSET = 1F / 16;
    
    public static final BlockCollide SOLID = new BlockCollide(new AxisAlignedBox(0, 0, 0, 1, 1, 1));
    public static final BlockCollide CACTUS = new BlockCollide(new AxisAlignedBox(CACTUS_SIDE_OFFSET, 0, CACTUS_SIDE_OFFSET, 1 - CACTUS_SIDE_OFFSET, 1, 1 - CACTUS_SIDE_OFFSET));

    
    final AxisAlignedBox[] boxes;
    
    public BlockCollide(AxisAlignedBox... boxes){
        this.boxes = boxes;
    }
    
    public AxisAlignedBox[] getBoxes(){
        return boxes;
    }
    
}
