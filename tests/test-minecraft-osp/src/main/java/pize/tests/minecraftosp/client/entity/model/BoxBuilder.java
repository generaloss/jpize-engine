package pize.tests.minecraftosp.client.entity.model;

import pize.gl.type.GlType;
import pize.graphics.mesh.IndexedMesh;
import pize.gl.vertex.GlVertexAttr;

public class BoxBuilder{
    
    private final IndexedMesh mesh;
    private float[] vertices;
    private int vertexPointer;
    private final float x1, y1, z1, x2, y2, z2;
    
    public BoxBuilder(float x1, float y1, float z1, float x2, float y2, float z2){
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
        
        mesh = new IndexedMesh(
                new GlVertexAttr(3, GlType.FLOAT),
                new GlVertexAttr(4, GlType.FLOAT),
                new GlVertexAttr(2, GlType.FLOAT)
        );
        vertices = new float[4 * 6 * mesh.getBuffer().getVertexSize()];
    }
    
    public BoxBuilder nx(float r, float g, float b, float a, float u1, float v1, float u2, float v2){
        addVertex(x1, y2, z2, r, g, b, a, u1, v1);
        addVertex(x1, y1, z2, r, g, b, a, u1, v2);
        addVertex(x1, y1, z1, r, g, b, a, u2, v2);
        addVertex(x1, y2, z1, r, g, b, a, u2, v1);
        
        return this;
    }
    
    public BoxBuilder px(float r, float g, float b, float a, float u1, float v1, float u2, float v2){
        addVertex(x2, y2, z1, r, g, b, a, u1, v1);
        addVertex(x2, y1, z1, r, g, b, a, u1, v2);
        addVertex(x2, y1, z2, r, g, b, a, u2, v2);
        addVertex(x2, y2, z2, r, g, b, a, u2, v1);
        
        return this;
    }
    
    public BoxBuilder ny(float r, float g, float b, float a, float u1, float v1, float u2, float v2){
        addVertex(x1, y1, z2, r, g, b, a, u1, v1);
        addVertex(x2, y1, z2, r, g, b, a, u1, v2);
        addVertex(x2, y1, z1, r, g, b, a, u2, v2);
        addVertex(x1, y1, z1, r, g, b, a, u2, v1);
        
        return this;
    }
    
    public BoxBuilder py(float r, float g, float b, float a, float u1, float v1, float u2, float v2){
        addVertex(x1, y2, z1, r, g, b, a, u1, v1);
        addVertex(x2, y2, z1, r, g, b, a, u1, v2);
        addVertex(x2, y2, z2, r, g, b, a, u2, v2);
        addVertex(x1, y2, z2, r, g, b, a, u2, v1);
        
        return this;
    }
    
    public BoxBuilder nz(float r, float g, float b, float a, float u1, float v1, float u2, float v2){
        addVertex(x1, y2, z1, r, g, b, a, u1, v1);
        addVertex(x1, y1, z1, r, g, b, a, u1, v2);
        addVertex(x2, y1, z1, r, g, b, a, u2, v2);
        addVertex(x2, y2, z1, r, g, b, a, u2, v1);
        
        return this;
    }
    
    public BoxBuilder pz(float r, float g, float b, float a, float u1, float v1, float u2, float v2){
        addVertex(x2, y2, z2, r, g, b, a, u1, v1);
        addVertex(x2, y1, z2, r, g, b, a, u1, v2);
        addVertex(x1, y1, z2, r, g, b, a, u2, v2);
        addVertex(x1, y2, z2, r, g, b, a, u2, v1);
        
        return this;
    }
    
    
    private void addVertex(float x, float y, float z, float r, float g, float b, float a, float u, float v){
        /*
        float[] cube = new float[]{
            x1, y2, z2,  x1, y1, z2,  x1, y1, z1,  repeat3  x1, y2, z1,  repeat0,  // -x
            x2, y2, z1,  x2, y1, z1,  x2, y1, z2,  repeat3  x2, y2, z2,  repeat0,  // +x
            x1, y1, z2,  x2, y1, z2,  x2, y1, z1,  repeat3  x1, y1, z1,  repeat0,  // -y
            x1, y2, z1,  x2, y2, z1,  x2, y2, z2,  repeat3  x1, y2, z2,  repeat0,  // +y
            x1, y2, z1,  x1, y1, z1,  x2, y1, z1,  repeat3, x2, y2, z1,  repeat0,  // -z
            x2, y2, z2,  x2, y1, z2,  x1, y1, z2,  repeat3, x1, y2, z2,  repeat0,  // +z
        };
        */

        int p = vertexPointer * mesh.getBuffer().getVertexSize();
        
        vertices[p    ] = x;
        vertices[p + 1] = y;
        vertices[p + 2] = z;
        
        vertices[p + 3] = r;
        vertices[p + 4] = g;
        vertices[p + 5] = b;
        vertices[p + 6] = a;
        
        vertices[p + 7] = u;
        vertices[p + 8] = v;
        
        vertexPointer++;
    }
    
    public IndexedMesh end(){
        mesh.getBuffer().setData(vertices);
        mesh.getIndexBuffer().setData(new int[]{
            0 , 1 , 2 ,  2 , 3 , 0 , // -x
            4 , 5 , 6 ,  6 , 7 , 4 , // +x
            8 , 9 , 10,  10, 11, 8 , // -y
            12, 13, 14,  14, 15, 12, // +y
            16, 17, 18,  18, 19, 16, // -z
            20, 21, 22,  22, 23, 20, // +z
        });
        
        return mesh;
    }
    
}
