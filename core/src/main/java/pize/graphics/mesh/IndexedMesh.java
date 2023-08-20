package pize.graphics.mesh;

import pize.app.Disposable;
import pize.lib.gl.vertex.GlVertexAttr;
import pize.lib.gl.tesselation.GlPrimitive;
import pize.lib.gl.vertex.GlVertexArray;

public class IndexedMesh implements Disposable{

    private final GlVertexArray vertexArray;
    private final VertexBuffer vertexBuffer;
    private final IndexBuffer indexBuffer;
    private GlPrimitive mode;

    public IndexedMesh(GlVertexAttr... attributes){
        this.vertexArray = new GlVertexArray();
        this.vertexBuffer = new VertexBuffer();
        this.vertexBuffer.enableAttributes(attributes);
        this.indexBuffer = new IndexBuffer();
        this.mode = GlPrimitive.TRIANGLES;
    }


    public void render(int indices){
        vertexArray.drawElements(indices, mode);
    }

    public void render(){
        this.render(indexBuffer.getSize());
    }

    public void setMode(GlPrimitive mode){
        this.mode = mode;
    }


    public VertexBuffer getBuffer(){
        return vertexBuffer;
    }

    public IndexBuffer getIndexBuffer(){
        return indexBuffer;
    }

    @Override
    public void dispose(){
        vertexBuffer.dispose();
        vertexArray.dispose();
        indexBuffer.dispose();
    }

}
