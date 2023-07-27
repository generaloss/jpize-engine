package pize.tests.voxelgame.client.chunk.mesh;

import pize.Pize;
import pize.app.Disposable;
import pize.graphics.vertex.VertexArray;
import pize.graphics.vertex.VertexAttr;
import pize.graphics.vertex.VertexBuffer;

public abstract class ChunkMesh implements Disposable{

    private VertexArray vao;
    protected VertexBuffer vbo;
    
    public ChunkMesh(VertexAttr... attributes){
        Pize.execSync(()->{
            vao = new VertexArray();
            vbo = new VertexBuffer();
            vbo.enableAttributes(attributes);
        });
    }

    
    public void render(){
        if(vbo == null)
            return;

        vao.drawArrays(vbo.getVerticesNum());
    }
    
    @Override
    public void dispose(){
        if(vbo != null) vbo.dispose();
        if(vao != null) vao.dispose();
    }
    
    public abstract int updateVertices();
    
    public abstract ChunkMeshType getType();
    
}
