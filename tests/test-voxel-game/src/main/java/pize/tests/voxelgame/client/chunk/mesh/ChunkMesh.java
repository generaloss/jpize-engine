package pize.tests.voxelgame.client.chunk.mesh;

import pize.app.Disposable;
import pize.graphics.vertex.VertexArray;
import pize.graphics.vertex.VertexBuffer;

public abstract class ChunkMesh implements Disposable{

    private final VertexArray vao;
    protected final VertexBuffer vbo;
    
    public ChunkMesh(){
        vao = new VertexArray();
        vbo = new VertexBuffer();
    }

    
    public void render(){
        vao.drawArrays(vbo.getVerticesNum());
    }
    
    public VertexBuffer getBuffer(){
        return vbo;
    }
    
    @Override
    public void dispose(){
        vbo.dispose();
        vao.dispose();
    }
    
    
    public abstract ChunkMeshType getType();
    
}
