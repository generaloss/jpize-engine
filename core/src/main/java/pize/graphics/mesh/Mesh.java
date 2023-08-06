package pize.graphics.mesh;

import pize.app.Disposable;
import pize.graphics.gl.BufferUsage;
import pize.graphics.gl.Primitive;

import java.util.List;

public class Mesh implements Disposable{

    private Primitive mode;
    private float[] vertices;
    private int[] indices;
    private final VertexAttr[] attributes;
    private final VertexArray vao;
    private final VertexBuffer vbo;
    private final ElementBuffer ebo;


    public Mesh(VertexAttr... attributes){
        mode = Primitive.TRIANGLES;
        this.attributes = attributes;
        vertices = new float[0];
        indices = new int[0];

        vao = new VertexArray();
        vbo = new VertexBuffer();
        vbo.enableAttributes(attributes);
        ebo = new ElementBuffer();
    }

    public Mesh(Mesh mesh){
        attributes = mesh.attributes;
        vertices = mesh.vertices.clone();
        indices = mesh.indices.clone();
        mode = mesh.mode;

        vao = new VertexArray();
        vbo = new VertexBuffer();
        vbo.enableAttributes(attributes);
        vbo.setData(vertices, BufferUsage.DYNAMIC_DRAW);
        ebo = new ElementBuffer();
        ebo.setData(indices, BufferUsage.DYNAMIC_DRAW);
    }


    public void render(){
        // vao.drawArrays(vbo.getVerticesNum(), mode);
        vao.drawElements(ebo.getIndicesNum(), mode);
    }

    public void setRenderMode(Primitive mode){
        this.mode = mode;
    }
    

    public boolean setVertices(List<Float> verticesList){
        this.vertices = new float[verticesList.size()];
        for(int i = 0; i < vertices.length; i++)
            vertices[i] = verticesList.get(i);
        
        vbo.setData(vertices, BufferUsage.DYNAMIC_DRAW);
        return true;
    }
    
    public void setVertices(float[] vertices, BufferUsage usage){
        this.vertices = vertices;
        vbo.setData(vertices, usage);
    }
    
    public void setVertices(float[] vertices){
        this.vertices = vertices;
        vbo.setData(vertices, BufferUsage.STATIC_DRAW);
    }
    
    public void setIndices(int[] indices, BufferUsage usage){
        this.indices = indices;
        ebo.setData(indices, usage);
    }
    
    public void setIndices(int[] indices){
        this.indices = indices;
        ebo.setData(indices, BufferUsage.STATIC_DRAW);
    }
    
    
    public float[] getIndexedVertices(){
        final float[] indexedVertices = new float[indices.length * 3];
        for(int i = 0; i < indices.length; i++){
            final int index = indices[i];
            indexedVertices[i * 3    ] = vertices[index * 3    ];
            indexedVertices[i * 3 + 1] = vertices[index * 3 + 1];
            indexedVertices[i * 3 + 2] = vertices[index * 3 + 2];
        }
        
        return indexedVertices;
    }
    
    public float[] getVertices(){
        return vertices;
    }
    
    public int[] getIndices(){
        return indices;
    }
    
    
    public VertexArray getVAO(){
        return vao;
    }
    
    public VertexBuffer getVBO(){
        return vbo;
    }
    
    public ElementBuffer getEBO(){
        return ebo;
    }


    @Override
    public void dispose(){
        vbo.dispose();
        vao.dispose();
        ebo.dispose();
    }
    
    public Mesh copy(){
        return new Mesh(this);
    }

}
