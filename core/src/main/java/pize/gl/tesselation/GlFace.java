package pize.gl.tesselation;

import static org.lwjgl.opengl.GL33.*;

public enum GlFace{

    FRONT          (GL_FRONT),
    BACK           (GL_BACK),
    FRONT_AND_BACK (GL_FRONT_AND_BACK);


    public final int GL;

    GlFace(int GL){
        this.GL = GL;
    }

}
