package pize.tests.voxelgame.client.chunk.mesh;

import pize.Pize;
import pize.graphics.gl.BufferUsage;
import pize.graphics.gl.Type;
import pize.graphics.vertex.VertexAttr;

import java.util.ArrayList;
import java.util.List;

public class ChunkMeshPackedCullingOn extends ChunkMesh{
    
    private final List<Integer> verticesList;

    public ChunkMeshPackedCullingOn(){
        super(
            new VertexAttr(1, Type.FLOAT), // packed1 (int) - (Type.FLOAT потому что похоже баг какой-то хз шиза тут должен быть Type.INT)
            new VertexAttr(1, Type.FLOAT)  // packed2 (int)
        );
        
        verticesList = new ArrayList<>();
    }


    public void vertex(int x, int y, int z, float u, float v, float light, float r, float g, float b, float a){
        final int atlasTilesX = 32;
        final int atlasTilesY = 32;

        // Packed position
        final int positionPacked = (
            (x      ) | // 5 bit
            (y << 5 ) | // 9 bit
            (z << 14) | // 5 bit

            ((int) (u * atlasTilesX) << 19) | // 4 bit
            ((int) (v * atlasTilesY) << 23)   // 4 bit

            // 4 bit remaining
        );
        put(positionPacked); // x, y, z, u, v

        // Packed color
        final int colorPacked = (
            ((int) (r * light * 255)      ) | // 8 bit
            ((int) (g * light * 255) << 8 ) | // 8 bit
            ((int) (b * light * 255) << 16) | // 8 bit
            ((int) (a * 255        ) << 24)   // 8 bit
        );
        put(colorPacked); // r, g, b, a
    }
    
    public void vertex(int packed1, int packed2){
        put(packed1);
        put(packed2);
    }

    public void put(int value){
        verticesList.add(value);
    }


    @Override
    public int updateVertices(){
        if(verticesList.size() == 0)
            return 0;

        final int[] verticesArray = new int[verticesList.size()];
        for(int i = 0; i < verticesList.size(); i++)
            verticesArray[i] = verticesList.get(i);

        Pize.execSync(() -> vbo.setData(verticesArray, BufferUsage.STATIC_DRAW));
        verticesList.clear();

        return verticesArray.length;
    }

    public List<Integer> getVerticesList(){
        return verticesList;
    }


    @Override
    public ChunkMeshType getType(){
        return ChunkMeshType.SOLID;
    }
    
}
