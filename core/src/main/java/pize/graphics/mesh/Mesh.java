package pize.graphics.mesh;

import pize.app.Disposable;
import pize.lib.gl.vertex.GlVertexAttr;
import pize.lib.gl.tesselation.GlPrimitive;
import pize.lib.gl.vertex.GlVertexArray;

public class Mesh implements Disposable{

    private final GlVertexArray vertexArray;
    private final VertexBuffer vertexBuffer;
    private GlPrimitive mode;

    public Mesh(GlVertexAttr... attributes){
        this.vertexArray = new GlVertexArray();
        this.vertexBuffer = new VertexBuffer();
        this.vertexBuffer.enableAttributes(attributes);
        this.mode = GlPrimitive.TRIANGLES;
    }


    public void render(){
        vertexArray.drawArrays(vertexBuffer.getVertexCount(), mode);
    }

    public void setMode(GlPrimitive mode){
        this.mode = mode;
    }


    public VertexBuffer getBuffer(){
        return vertexBuffer;
    }

    @Override
    public void dispose(){
        vertexBuffer.dispose();
        vertexArray.dispose();
    }

}
