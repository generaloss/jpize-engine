package jpize.gl.tesselation;

import static org.lwjgl.opengl.GL33.*;

public enum GlPolygonMode{

    POINT(GL_POINT),
    LINE(GL_LINE),
    FILL(GL_FILL);

    public final int GL;

    GlPolygonMode(int GL){
        this.GL = GL;
    }

}
