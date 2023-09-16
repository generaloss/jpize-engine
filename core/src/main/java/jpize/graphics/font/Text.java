package jpize.graphics.font;

import jpize.gl.buffer.GlBufUsage;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertexAttr;
import jpize.graphics.mesh.QuadMesh;

public class Text{

    private QuadMesh mesh;

    public Text(){
        this.mesh = new QuadMesh(1, GlBufUsage.DYNAMIC_DRAW,
                new GlVertexAttr(2, GlType.FLOAT), // position
                new GlVertexAttr(2, GlType.FLOAT), // uv
                new GlVertexAttr(4, GlType.FLOAT)  // color
        );
    }

}
