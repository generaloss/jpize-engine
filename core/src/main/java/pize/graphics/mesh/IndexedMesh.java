package pize.graphics.mesh;

import pize.app.Disposable;
import pize.graphics.gl.Primitive;

public class IndexedMesh implements Disposable{

    private final GlVao vertexArray;
    private final GlVbo vertexBuffer;
    private final IndexBuffer indexBuffer;
    private Primitive mode;

    public IndexedMesh(VertexAttr... attributes){
        this.vertexArray = new GlVao();
        this.vertexBuffer = new GlVbo();
        this.vertexBuffer.enableAttributes(attributes);
        this.indexBuffer = new IndexBuffer();
        this.mode = Primitive.TRIANGLES;
    }


    public void render(int indices){
        vertexArray.drawElements(indices, mode);
    }

    public void render(){
        this.render(indexBuffer.getIndicesNum());
    }

    public void setMode(Primitive mode){
        this.mode = mode;
    }


    public GlVbo getBuffer(){
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
