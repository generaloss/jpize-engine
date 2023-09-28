package jpize.tests.mcose.client.chunk.mesh;

import jpize.Jpize;
import jpize.gl.buffer.GlBufUsage;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertexArray;
import jpize.gl.vertex.GlVertexAttr;
import jpize.graphics.buffer.VertexBuffer;
import jpize.util.Disposable;

import java.util.ArrayList;
import java.util.List;

public class ChunkMesh implements Disposable{

    private final ChunkMeshType type;
    private final List<Float> verticesList;
    private final List<Integer> verticesPackedList;
    private GlVertexArray vao, pvao;
    private VertexBuffer vbo, pvbo;
    private final transient Object lock = new Object();
    private final transient Object lockPacked = new Object();
    private boolean hasPacked;

    public ChunkMesh(ChunkMeshType type){
        this.type = type;
        this.verticesList = new ArrayList<>();
        this.verticesPackedList = new ArrayList<>();

        Jpize.execSync(() -> {
            this.vao = new GlVertexArray();
            this.vbo = new VertexBuffer();
            this.vbo.enableAttributes(
                    new GlVertexAttr(3, GlType.FLOAT), // position
                    new GlVertexAttr(2, GlType.FLOAT), // uv
                    new GlVertexAttr(4, GlType.FLOAT), // color
                    new GlVertexAttr(3, GlType.FLOAT)  // ao, skyLight, blockLight
            );

            this.pvao = new GlVertexArray();
            this.pvbo = new VertexBuffer();
            this.pvbo.enableAttributes(
                    new GlVertexAttr(1, GlType.FLOAT), // packed_1
                    new GlVertexAttr(1, GlType.FLOAT), // packed_2
                    new GlVertexAttr(1, GlType.FLOAT)  // packed_3
            );
        });
    }

    
    public void render(){
        if(vao != null)
            vao.drawArrays(vbo.getVertexCount());
    }

    public void renderPacked(){
        if(pvao != null)
            pvao.drawArrays(pvbo.getVertexCount());
    }
    
    @Override
    public void dispose(){
        if(vbo != null){
            vbo.dispose();
            vao.dispose();
            pvbo.dispose();
            pvao.dispose();
        }
    }


    public int updateVertices(){
        // # Exceptions:
        // #     verticesList.get(i) = null
        // #     verticesList.size() <= i

        final float[] verticesArray = new float[verticesList.size()];
        for(int i = 0; i < verticesList.size(); i++)
            verticesArray[i] = verticesList.get(i);

        Jpize.execSync(() -> vbo.setData(verticesArray, GlBufUsage.DYNAMIC_DRAW));
        verticesList.clear();


        final int[] verticesPackedArray = new int[verticesPackedList.size()];
        for(int i = 0; i < verticesPackedList.size(); i++)
            verticesPackedArray[i] = verticesPackedList.get(i);

        Jpize.execSync(() -> pvbo.setData(verticesPackedArray, GlBufUsage.DYNAMIC_DRAW));
        verticesPackedList.clear();


        hasPacked = (verticesPackedArray.length != 0);
        return verticesArray.length + verticesPackedArray.length;
    }

    public boolean isHasPacked(){
        return hasPacked;
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

    public void vertexPacked(int x, int y, int z,
                             float u, float v,
                             float r, float g, float b, float a,
                             float ao, float skyLight, float blockLight){

        final int UV_TILES_X = 32;
        final int UV_TILES_Y = 32;

        // Position, UV
        final int packed_1 = ( // used 32 / 32 bit
                (x      ) | // 5 bit
                (y << 5 ) | // 9 bit
                (z << 14) | // 5 bit

                ((int) (u * UV_TILES_X) << 19) | // 6 bit
                ((int) (v * UV_TILES_Y) << 25)   // 6 bit
        );
        putPacked(packed_1); // x, y, z,  u, v

        // Color
        final int packed_2 = ( // used 32 / 32 bit
                ((int) (r * 255)      ) | // 8 bit
                ((int) (g * 255) << 8 ) | // 8 bit
                ((int) (b * 255) << 16) | // 8 bit
                ((int) (a * 255) << 24)   // 8 bit
        );
        putPacked(packed_2); // r, g, b, a

        // AmbientOcclusion, SkyLight, BlockLight
        final int packed_3 = ( // used 12 / 32 bit
                ((int) (ao         * 15)     ) | // 4 bit
                ((int) (skyLight   * 15) << 4) | // 4 bit
                ((int) (blockLight * 15) << 8)   // 4 bit
        );
        putPacked(packed_3); // ao, sl, bl
    }

    public void put(float v){
        synchronized (lock){
            verticesList.add(v);
        }
    }

    public void putPacked(int v){
        synchronized (lockPacked){
            verticesPackedList.add(v);
        }
    }

    
    public ChunkMeshType getType(){
        return type;
    }
    
}
