package glit.graphics.gl;

import static org.lwjgl.opengl.GL33.*;

public enum Filter{

    NEAREST_MIPMAP_NEAREST (GL_NEAREST_MIPMAP_NEAREST),
    LINEAR_MIPMAP_NEAREST  (GL_LINEAR_MIPMAP_NEAREST),
    NEAREST_MIPMAP_LINEAR  (GL_NEAREST_MIPMAP_LINEAR),
    LINEAR_MIPMAP_LINEAR   (GL_LINEAR_MIPMAP_LINEAR),

    NEAREST                (GL_NEAREST),
    LINEAR                 (GL_LINEAR);


    public final int gl;

    Filter(int gl){
        this.gl = gl;
    }

}
