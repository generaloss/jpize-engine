package glit.graphics.vertex;

import glit.context.Disposable;
import glit.graphics.gl.BufferUsage;
import glit.graphics.gl.Primitive;
import glit.util.FastArrayList;

public class Mesh implements Disposable{

    private Primitive mode;
    private float[] vertices, indices;
    private final VertexAttr[] attributes;
    private final VertexArray vao;
    private final VertexBuffer vbo;
    // private final ElementBuffer ebo;


    public Mesh(VertexAttr... attributes){
        mode = Primitive.TRIANGLES;
        this.attributes = attributes;
        vertices = new float[0];
        indices = new float[0];

        vao = new VertexArray();
        vbo = new VertexBuffer();
        vbo.enableAttributes(attributes);
        // ebo = new ElementBuffer();
    }

    public Mesh(Mesh mesh){
        attributes = mesh.attributes;
        vertices = mesh.vertices.clone();
        mode = mesh.mode;

        vao = new VertexArray();
        vbo = new VertexBuffer();
        vbo.enableAttributes(attributes);
        vbo.setData(vertices, BufferUsage.DYNAMIC_DRAW);
        // ebo = new ElementBuffer();
        // ebo.setData(indices, BufferUsage.DYNAMIC_DRAW);
    }


    public void render(){
        vao.drawArrays(vbo.getVertexCount(), mode);
        // vao.drawElements(indices.length, mode);
    }

    public void setRenderMode(Primitive mode){
        this.mode = mode;
    }

    public void setVertices(float[] vertices){
        this.vertices = vertices;
        vbo.setData(vertices, BufferUsage.DYNAMIC_DRAW);
    }

    public boolean setVertices(FastArrayList<Float> verticesList){
        vertices = new float[verticesList.size()];
        for(int i = 0; i < vertices.length; i++){
            Float v = verticesList.get(i);
            if(v == null)
                return false;
            vertices[i] = v;
        }
        
        vbo.setData(vertices, BufferUsage.DYNAMIC_DRAW);
        return true;
    }

    // public void setIndices(float[] indices){
    //     this.indices = indices;
    //     ebo.setData(indices, BufferUsage.DYNAMIC_DRAW);
    // }


    @Override
    public void dispose(){
        vbo.dispose();
        vao.dispose();
        // ebo.dispose();
    }
    
    @Override
    public Mesh clone(){
        return new Mesh(this);
    }

}
