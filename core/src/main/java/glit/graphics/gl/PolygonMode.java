package glit.graphics.gl;

import static org.lwjgl.opengl.GL33.*;

public enum PolygonMode{

    POINT(GL_POINT),
    LINE(GL_LINE),
    FILL(GL_FILL);


    public final int gl;

    PolygonMode(int gl){
        this.gl = gl;
    }

}
