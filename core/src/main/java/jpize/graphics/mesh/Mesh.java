package jpize.graphics.mesh;

import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.vertex.GlVertexArray;
import jpize.gl.vertex.GlVertAttr;
import jpize.graphics.buffer.VertexBuffer;

public class Mesh implements IMesh{

    private final GlVertexArray vertexArray;
    private final VertexBuffer vertexBuffer;
    private GlPrimitive mode;

    public Mesh(GlVertAttr... attributes){
        this.vertexArray = new GlVertexArray();
        this.vertexBuffer = new VertexBuffer();
        this.vertexBuffer.enableAttributes(attributes);
        this.mode = GlPrimitive.TRIANGLES;
    }

    public void setMode(GlPrimitive mode){
        this.mode = mode;
    }

    public void render(int vertexCount){
        vertexArray.drawArrays(vertexCount, mode);
    }

    @Override
    public void render(){
        render(vertexBuffer.getVertexCount());
    }

    @Override
    public VertexBuffer getBuffer(){
        return vertexBuffer;
    }

    @Override
    public void dispose(){
        vertexBuffer.dispose();
        vertexArray.dispose();
    }

}
