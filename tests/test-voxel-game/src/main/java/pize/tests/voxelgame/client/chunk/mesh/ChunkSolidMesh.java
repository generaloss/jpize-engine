package pize.tests.voxelgame.client.chunk.mesh;

import pize.graphics.gl.Type;
import pize.graphics.vertex.VertexAttr;

public class ChunkSolidMesh extends ChunkMesh{
    
    public ChunkSolidMesh(){
        vbo.enableAttributes(
            new VertexAttr(1, Type.FLOAT), // packed1 (int)
            new VertexAttr(1, Type.FLOAT)  // packed2 (int)
        );
    }
    
    @Override
    public final ChunkMeshType getType(){
        return ChunkMeshType.SOLID;
    }
    
}
