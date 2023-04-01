package glit.graphics.util;

import glit.graphics.gl.Type;
import glit.graphics.gl.BufferUsage;
import glit.graphics.vertex.VertexArray;
import glit.graphics.vertex.VertexAttr;
import glit.graphics.vertex.VertexBuffer;

public class ScreenQuad{

    private static final float[] vertices = {
        -1,+1, 0,1,
        -1,-1, 0,0,
        +1,-1, 1,0,

        +1,-1, 1,0,
        +1,+1, 1,1,
        -1,+1, 0,1,
    };


    private static ScreenQuad instance;
    private final VertexArray vao;
    private final VertexBuffer vbo;

    private ScreenQuad(){
        vao = new VertexArray();
        vbo = new VertexBuffer();
        vbo.enableAttributes(new VertexAttr(2, Type.FLOAT), new VertexAttr(2, Type.FLOAT)); // pos, tex
        vbo.setData(vertices, BufferUsage.STATIC_DRAW);
    }


    public static void render(){
        if(instance == null)
            instance = new ScreenQuad();
        
        instance.vao.drawArrays(6);
    }

    private static void dispose(){
        if(instance != null){
            instance.vao.dispose();
            instance.vbo.dispose();
        }
    }

}
