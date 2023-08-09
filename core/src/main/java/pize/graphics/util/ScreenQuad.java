package pize.graphics.util;

import pize.graphics.gl.Type;
import pize.graphics.mesh.IndexedMesh;
import pize.graphics.mesh.VertexAttr;

public class ScreenQuad{
    
    private static ScreenQuad instance;
    private final IndexedMesh mesh;

    private ScreenQuad(){
        mesh = new IndexedMesh(new VertexAttr(2, Type.FLOAT), new VertexAttr(2, Type.FLOAT)); // pos, uv
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

    private static void dispose(){ // Invoked from Context
        if(instance != null)
            instance.mesh.dispose();
    }

}
