package jpize.graphics.mesh;

import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.vertex.GlVertexArray;
import jpize.gl.vertex.GlVertAttr;
import jpize.graphics.buffer.IndexBuffer;
import jpize.graphics.buffer.VertexBuffer;

public class IndexedMesh implements IMesh{

    private GlPrimitive mode;
    private final GlVertexArray vertexArray;
    private final VertexBuffer vertexBuffer;
    private final IndexBuffer indexBuffer;

    public IndexedMesh(GlVertAttr... attributes){
        this.mode = GlPrimitive.TRIANGLES;
        this.vertexArray = new GlVertexArray();

        this.vertexBuffer = new VertexBuffer();
        this.vertexBuffer.enableAttributes(attributes);

        this.indexBuffer = new IndexBuffer();
    }

    public void setMode(GlPrimitive mode){
        this.mode = mode;
    }


    public void render(int indices){
        vertexArray.drawElements(indices, mode);
    }

    @Override
    public void render(){
        render(indexBuffer.getIndexCount());
    }

    @Override
    public VertexBuffer getBuffer(){
        return vertexBuffer;
    }

    public IndexBuffer getIndexBuffer(){
        return indexBuffer;
    }

    @Override
    public void dispose(){
        vertexArray.dispose();
        vertexBuffer.dispose();
        indexBuffer.dispose();
    }

}
