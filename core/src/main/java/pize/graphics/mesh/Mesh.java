package pize.graphics.mesh;

import pize.app.Disposable;
import pize.graphics.gl.Primitive;

public class Mesh implements Disposable{

    private final GlVao vertexArray;
    private final GlVbo vertexBuffer;
    private Primitive mode;

    public Mesh(VertexAttr... attributes){
        this.vertexArray = new GlVao();
        this.vertexBuffer = new GlVbo();
        this.vertexBuffer.enableAttributes(attributes);
        this.mode = Primitive.TRIANGLES;
    }


    public void render(){
        vertexArray.drawArrays(vertexBuffer.getVerticesNum(), mode);
    }

    public void setMode(Primitive mode){
        this.mode = mode;
    }


    public GlVbo getBuffer(){
        return vertexBuffer;
    }

    @Override
    public void dispose(){
        vertexBuffer.dispose();
        vertexArray.dispose();
    }

}
