package pize.graphics.mesh;

import pize.util.Disposable;
import pize.gl.vertex.GlVertexAttr;
import pize.gl.tesselation.GlPrimitive;
import pize.gl.vertex.GlVertexArray;

public class QuadMesh implements Disposable{

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
        this.render(indexBuffer.getSize(), primitive);
    }

    public void render(){
        this.render(indexBuffer.getSize());
    }


    public VertexBuffer getBuffer(){
        return vertexBuffer;
    }

    @Override
    public void dispose(){
        vertexBuffer.dispose();
        vertexArray.dispose();
        indexBuffer.dispose();
    }

}
