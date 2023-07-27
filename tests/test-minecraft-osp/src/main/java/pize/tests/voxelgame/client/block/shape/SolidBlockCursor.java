package pize.tests.voxelgame.client.block.shape;

public class SolidBlockCursor extends BlockCursor{
    
    static final float min = 0F;
    static final float max = 1F;
    
    public SolidBlockCursor(){
        super(
            new float[]{
                max, max, max, //0
                min, max, max, //1
                max, min, max, //2
                min, min, max, //3
                max, max, min, //4
                min, max, min, //5
                max, min, min, //6
                min, min, min, //7
            },
            new int[]{
                7, 6, 6, 2, 2, 3, 3, 7, //Top
                0, 4, 4, 5, 5, 1, 1, 0, //Bottom
                0, 2, 2, 6, 6, 4, 4, 0, //Left
                7, 3, 3, 1, 1, 5, 5, 7, //Right
                3, 2, 2, 0, 0, 1, 1, 3, //Front
                4, 6, 6, 7, 7, 5, 5, 4, //Back
            }
        );
    }
    
}
