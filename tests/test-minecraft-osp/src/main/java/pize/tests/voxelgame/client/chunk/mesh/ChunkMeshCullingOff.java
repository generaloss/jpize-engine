package pize.tests.voxelgame.client.chunk.mesh;

import pize.Pize;
import pize.graphics.gl.BufferUsage;
import pize.graphics.gl.Type;
import pize.graphics.vertex.VertexAttr;

import java.util.ArrayList;
import java.util.List;

public class ChunkMeshCullingOff extends ChunkMesh{

    private final List<Float> verticesList;

    public ChunkMeshCullingOff(){
        super(
            new VertexAttr(3, Type.FLOAT), // pos3
            new VertexAttr(4, Type.FLOAT), // col4
            new VertexAttr(2, Type.FLOAT)  // tex2
        );

        verticesList = new ArrayList<>();
    }

    @Override
    public int updateVertices(){
        if(verticesList.size() == 0)
            return 0;

        final float[] verticesArray = new float[verticesList.size()];
        for(int i = 0; i < verticesList.size(); i++)
            verticesArray[i] = verticesList.get(i);

        Pize.execSync(() -> vbo.setData(verticesArray, BufferUsage.STATIC_DRAW));
        verticesList.clear();

        return verticesArray.length;
    }

    public void vertex(float x, float y, float z, float r, float g, float b, float a, float u, float v){
        put(x); put(y); put(z);
        put(r); put(g); put(b); put(a);
        put(u); put(v);
    }

    public void put(float v){
        verticesList.add(v);
    }

    public List<Float> getVerticesList(){
        return verticesList;
    }

    @Override
    public final ChunkMeshType getType(){
        return ChunkMeshType.CUSTOM;
    }
    
}