package pize.tests.voxelgame.client.chunk.mesh;

import pize.graphics.gl.Type;
import pize.graphics.vertex.VertexAttr;

public class ChunkCustomMesh extends ChunkMesh{
    
    public ChunkCustomMesh(){
        vbo.enableAttributes(
            new VertexAttr(3, Type.FLOAT), // pos3
            new VertexAttr(4, Type.FLOAT), // col4
            new VertexAttr(2, Type.FLOAT)  // tex2
        );
    }
    
    @Override
    public final ChunkMeshType getType(){
        return ChunkMeshType.CUSTOM;
    }
    
}