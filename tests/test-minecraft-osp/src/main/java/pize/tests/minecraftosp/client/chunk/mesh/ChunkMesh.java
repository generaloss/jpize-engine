package pize.tests.minecraftosp.client.chunk.mesh;

import pize.Jize;
import pize.util.Disposable;
import pize.gl.buffer.GlBufUsage;
import pize.gl.type.GlType;
import pize.gl.vertex.GlVertexArray;
import pize.gl.vertex.GlVertexAttr;
import pize.graphics.mesh.VertexBuffer;

import java.util.ArrayList;
import java.util.List;

public class ChunkMesh implements Disposable{

    private final ChunkMeshType type;
    private final List<Float> verticesList;
    private GlVertexArray vao;
    private VertexBuffer vbo;

    public ChunkMesh(ChunkMeshType type){
        this.type = type;
        this.verticesList = new ArrayList<>();

        Jize.execSync(()->{
            vao = new GlVertexArray();
            vbo = new VertexBuffer();
            vbo.enableAttributes(
                new GlVertexAttr(3, GlType.FLOAT), // position
                new GlVertexAttr(2, GlType.FLOAT), // uv
                new GlVertexAttr(4, GlType.FLOAT), // color
                new GlVertexAttr(3, GlType.FLOAT)  // ao, skyLight, blockLight
            );
        });
    }

    
    public void render(){
        if(vbo == null)
            return;

        vao.drawArrays(vbo.getVertexCount());
    }
    
    @Override
    public void dispose(){
        if(vbo != null) vbo.dispose();
        if(vao != null) vao.dispose();
    }


    public int updateVertices(){
        final float[] verticesArray = new float[verticesList.size()];
        for(int i = 0; i < verticesList.size(); i++)
            verticesArray[i] = verticesList.get(i);

        Jize.execSync(() -> vbo.setData(verticesArray, GlBufUsage.DYNAMIC_DRAW));
        verticesList.clear();

        return verticesArray.length;
    }

    public void vertex(float x, float y, float z,
                       float u, float v,
                       float r, float g, float b, float a,
                       float ao, float skyLight, float blockLight){
        put(x); put(y); put(z);
        put(u); put(v);
        put(r); put(g); put(b); put(a);
        put(ao); put(skyLight); put(blockLight);
    }

    public void put(float v){
        verticesList.add(v);
    }

    public List<Float> getVerticesList(){
        return verticesList;
    }

    
    public ChunkMeshType getType(){
        return type;
    }
    
}
