package pize.lib.gl.glenum;

import static org.lwjgl.opengl.GL33.*;

public enum GlDepthFunc{

    NEVER    (GL_NEVER   ),
    LESS     (GL_LESS    ),
    EQUAL    (GL_EQUAL   ),
    LEQUAL   (GL_LEQUAL  ),
    GREATER  (GL_GREATER ),
    NOTEQUAL (GL_NOTEQUAL),
    GEQUAL   (GL_GEQUAL  ),
    ALWAYS   (GL_ALWAYS  );


    public final int GL;

    GlDepthFunc(int GL){
        this.GL = GL;
    }

}
