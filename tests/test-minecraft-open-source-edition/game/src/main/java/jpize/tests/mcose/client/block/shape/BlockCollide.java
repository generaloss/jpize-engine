package jpize.tests.mcose.client.block.shape;

import jpize.physic.axisaligned.box.AABox;

public class BlockCollide{

    private static final float CACTUS_SIDE_OFFSET = 1F / 16;
    
    public static final BlockCollide SOLID = new BlockCollide(new AABox(0, 0, 0, 1, 1, 1));
    public static final BlockCollide CACTUS = new BlockCollide(new AABox(CACTUS_SIDE_OFFSET, 0, CACTUS_SIDE_OFFSET, 1 - CACTUS_SIDE_OFFSET, 1, 1 - CACTUS_SIDE_OFFSET));
    //public static final BlockCollide STAIRS = new BlockCollide(new AABox(0, 0, 0, 1, 0.5, 1), new AABox(0, 0.5, 0.5, 1, 1, 1));


    final AABox[] boxes;
    
    public BlockCollide(AABox... boxes){
        this.boxes = boxes;
    }
    
    public AABox[] getBoxes(){
        return boxes;
    }
    
}
