package pize.graphics.gl;

import static org.lwjgl.opengl.GL33.*;

public enum PolygonMode{

    POINT(GL_POINT),
    LINE(GL_LINE),
    FILL(GL_FILL);


    public final int GL;

    PolygonMode(int GL){
        this.GL = GL;
    }

}
