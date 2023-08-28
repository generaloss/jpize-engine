package pize.graphics.util;

import pize.gl.type.GlType;
import pize.graphics.mesh.IndexedMesh;
import pize.gl.vertex.GlVertexAttr;

public class ScreenQuad{
    
    private static ScreenQuad instance;
    private final IndexedMesh mesh;

    private ScreenQuad(){
        mesh = new IndexedMesh(new GlVertexAttr(2, GlType.FLOAT), new GlVertexAttr(2, GlType.FLOAT)); // pos, uv
        mesh.getBuffer().setData(new float[]{
            -1, +1,  0, 1, // 0
            -1, -1,  0, 0, // 1
            +1, -1,  1, 0, // 2
            +1, +1,  1, 1, // 3
        });

        mesh.getIndexBuffer().setData(new int[]{
            0, 1, 2,
            2, 3, 0,
        });
    }


    public static void render(){
        if(instance == null)
            instance = new ScreenQuad();
        
        instance.mesh.render();
    }

    private static void dispose(){ // Calls from ContextManager
        if(instance != null)
            instance.mesh.dispose();
    }

}
