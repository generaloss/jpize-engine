package pize.graphics.mesh;

import pize.app.Disposable;
import pize.graphics.gl.Primitive;

public class QuadMesh implements Disposable{

    private final GlVao vertexArray;
    private final GlVbo vertexBuffer;
    private final QuadIndexBuffer indexBuffer;

    public QuadMesh(int size, VertexAttr... attributes){
        this.vertexArray = new GlVao();
        this.vertexBuffer = new GlVbo();
        this.vertexBuffer.enableAttributes(attributes);
        this.indexBuffer = new QuadIndexBuffer(size);
    }


    public void render(int indices, Primitive primitive){
        vertexArray.drawElements(indices, primitive, indexBuffer.getIndicesType());
    }

    public void render(int indices){
        vertexArray.drawElements(indices);
    }

    public void render(Primitive primitive){
        this.render(indexBuffer.getIndicesNum(), primitive);
    }

    public void render(){
        this.render(indexBuffer.getIndicesNum());
    }


    public GlVbo getBuffer(){
        return vertexBuffer;
    }

    @Override
    public void dispose(){
        vertexBuffer.dispose();
        vertexArray.dispose();
        indexBuffer.dispose();
    }

}
