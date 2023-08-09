package pize.tests.minecraftosp.client.chunk.mesh;

import pize.Pize;
import pize.app.Disposable;
import pize.graphics.mesh.GlVao;
import pize.graphics.mesh.VertexAttr;
import pize.graphics.mesh.GlVbo;

public abstract class ChunkMesh implements Disposable{

    private GlVao vao;
    protected GlVbo vbo;
    
    public ChunkMesh(VertexAttr... attributes){
        Pize.execSync(()->{
            vao = new GlVao();
            vbo = new GlVbo();
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
