package jpize.graphics.mesh;

import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.vertex.GlVertexArray;
import jpize.gl.vertex.GlVertexAttr;
import jpize.graphics.buffer.QuadIndexBuffer;
import jpize.graphics.buffer.VertexBuffer;

public class QuadMesh implements IMesh{

    private final GlVertexArray vertexArray;
    private final VertexBuffer vertexBuffer;
    private final QuadIndexBuffer indexBuffer;

    public QuadMesh(int size, GlVertexAttr... attributes){
        this.vertexArray = new GlVertexArray();
        this.vertexBuffer = new VertexBuffer();
        this.vertexBuffer.enableAttributes(attributes);
        this.indexBuffer = new QuadIndexBuffer(size);
    }


    public void render(int indices, GlPrimitive primitive){
        vertexArray.drawElements(indices, primitive);
    }

    public void render(int indices){
        vertexArray.drawElements(indices);
    }

    public void render(GlPrimitive primitive){
        this.render(indexBuffer.getIndexCount(), primitive);
    }

    @Override
    public void render(){
        final int indicesCount = (int) (vertexBuffer.getSize() / vertexBuffer.getVertexBytes() * 1.5);
        this.render(indicesCount);
    }


    @Override
    public VertexBuffer getBuffer(){
        return vertexBuffer;
    }

    @Override
    public void dispose(){
        vertexArray.dispose();
        vertexBuffer.dispose();
        indexBuffer.dispose();
    }

}
