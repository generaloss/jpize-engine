package glit.graphics.gl;

import static org.lwjgl.opengl.GL33.*;

public enum DepthFunc{

    NEVER   (GL_NEVER   ),
    LESS    (GL_LESS    ),
    EQUAL   (GL_EQUAL   ),
    LEQUAL  (GL_LEQUAL  ),
    GREATER (GL_GREATER ),
    NOTEQUA (GL_NOTEQUAL),
    GEQUAL  (GL_GEQUAL  ),
    ALWAYS  (GL_ALWAYS  );


    public final int gl;

    DepthFunc(int gl){
        this.gl = gl;
    }

}
