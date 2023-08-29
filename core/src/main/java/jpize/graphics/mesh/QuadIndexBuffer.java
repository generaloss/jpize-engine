package jpize.graphics.mesh;

public class QuadIndexBuffer extends IndexBuffer{

    public static int QUAD_INDICES = 6;
    public static int QUAD_VERTICES = 4;

    public QuadIndexBuffer(int size){
        generate(size);
    }

    private void generate(int size){
        final int[] indices = new int[QUAD_INDICES * size];

        for(int i = 0; i < size; i++){
            int indexQuadOffset = QUAD_INDICES * i;
            int vertexQuadOffset = QUAD_VERTICES * i;

            indices[indexQuadOffset    ] = vertexQuadOffset;
            indices[indexQuadOffset + 1] = vertexQuadOffset + 1;
            indices[indexQuadOffset + 2] = vertexQuadOffset + 2;

            indices[indexQuadOffset + 3] = vertexQuadOffset + 2;
            indices[indexQuadOffset + 4] = vertexQuadOffset + 3;
            indices[indexQuadOffset + 5] = vertexQuadOffset;
        }

        super.setData(indices);
    }

}
