package glit.graphics.gl;

import static org.lwjgl.opengl.GL33.*;

public enum CullFaceMode{

    FRONT          (GL_FRONT),
    BACK           (GL_BACK),
    FRONT_AND_BACK (GL_FRONT_AND_BACK);


    public final int gl;

    CullFaceMode(int gl){
        this.gl = gl;
    }

}
