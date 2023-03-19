package glit.graphics.gl;

import static org.lwjgl.opengl.GL33.*;

public enum Face{

    FRONT          (GL_FRONT),
    BACK           (GL_BACK),
    FRONT_AND_BACK (GL_FRONT_AND_BACK);


    public final int gl;

    Face(int gl){
        this.gl = gl;
    }

}
