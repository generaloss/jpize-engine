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
    private GlPrimitive mode;

    public QuadMesh(int size, GlVertexAttr... attributes){
        this.vertexArray = new GlVertexArray();
        this.vertexBuffer = new VertexBuffer();
        this.vertexBuffer.enableAttributes(attributes);
        this.indexBuffer = new QuadIndexBuffer(size);
        this.mode = GlPrimitive.TRIANGLES;
    }

    public void setMode(GlPrimitive mode){
        this.mode = mode;
    }


    public void render(int indices){
        vertexArray.drawElements(indices, mode);
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
