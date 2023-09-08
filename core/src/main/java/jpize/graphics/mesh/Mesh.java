package jpize.graphics.mesh;

import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.vertex.GlVertexArray;
import jpize.gl.vertex.GlVertexAttr;
import jpize.graphics.buffer.VertexBuffer;

public class Mesh implements IMesh{

    private final GlVertexArray vertexArray;
    private final VertexBuffer vertexBuffer;
    private GlPrimitive mode;

    public Mesh(GlVertexAttr... attributes){
        this.vertexArray = new GlVertexArray();
        this.vertexBuffer = new VertexBuffer();
        this.vertexBuffer.enableAttributes(attributes);
        this.mode = GlPrimitive.TRIANGLES;
    }

    public void setMode(GlPrimitive mode){
        this.mode = mode;
    }

    @Override
    public void render(){
        vertexArray.drawArrays(vertexBuffer.getVertexCount(), mode);
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
