package pize.graphics.gl;

import static org.lwjgl.opengl.GL33.*;

public enum Face{

    FRONT          (GL_FRONT),
    BACK           (GL_BACK),
    FRONT_AND_BACK (GL_FRONT_AND_BACK);


    public final int GL;

    Face(int GL){
        this.GL = GL;
    }

}
