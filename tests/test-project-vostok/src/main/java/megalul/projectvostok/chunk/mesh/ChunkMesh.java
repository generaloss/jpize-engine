package megalul.projectvostok.chunk.mesh;

import pize.context.Disposable;
import pize.graphics.gl.BufferUsage;
import pize.graphics.gl.Type;
import pize.graphics.vertex.VertexArray;
import pize.graphics.vertex.VertexAttr;
import pize.graphics.vertex.VertexBuffer;

public class ChunkMesh implements Disposable{

    private final VertexArray vao;
    private final VertexBuffer vbo;

    public ChunkMesh(){
        vao = new VertexArray();
        vbo = new VertexBuffer();
        vbo.enableAttributes(new VertexAttr(3, Type.FLOAT), new VertexAttr(4, Type.FLOAT), new VertexAttr(2, Type.FLOAT)); // pos3, col4, tex2
    }

    public void render(){
        vao.drawArrays(vbo.getVertexCount());
    }


    public void setVertices(float[] vertices){
        vbo.setData(vertices, BufferUsage.DYNAMIC_DRAW);
    }


    @Override
    public void dispose(){
        vbo.dispose();
        vao.dispose();
    }



}
