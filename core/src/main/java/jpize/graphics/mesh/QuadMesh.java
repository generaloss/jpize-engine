package jpize.graphics.mesh;

import jpize.gl.buffer.GlBufUsage;
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

    public QuadMesh(int maxSize, GlBufUsage bufferUsage, GlVertexAttr... attributes){
        this.vertexArray = new GlVertexArray();
        this.vertexBuffer = new VertexBuffer();
        this.vertexBuffer.setDefaultUsage(bufferUsage);
        this.vertexBuffer.enableAttributes(attributes);
        this.indexBuffer = new QuadIndexBuffer(maxSize, bufferUsage);
        this.mode = GlPrimitive.TRIANGLES;
    }

    public QuadMesh(int maxSize, GlVertexAttr... attributes){
        this(maxSize, GlBufUsage.STATIC_DRAW, attributes);
    }

    public void setMode(GlPrimitive mode){
        this.mode = mode;
    }


    public void setMaxQuads(int maxQuads, GlBufUsage bufferUsage){
        indexBuffer.setMaxQuads(maxQuads, bufferUsage);
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
