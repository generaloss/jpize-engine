package pize.tests.voxelgame.client.block.shape;

public class GrassBlockCursor extends BlockCursor{
    
    static final float min = 0.2F;
    static final float max = 0.8F;
    static final float minY = 0F;
    static final float maxY = 1F;
    
    public GrassBlockCursor(){
        super(
            new float[]{
                max, maxY, max, //0
                min, maxY, max, //1
                max, minY, max, //2
                min, minY, max, //3
                max, maxY, min, //4
                min, maxY, min, //5
                max, minY, min, //6
                min, minY, min, //7
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