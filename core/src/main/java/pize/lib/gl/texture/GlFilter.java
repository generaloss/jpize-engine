package pize.lib.gl.texture;

import static org.lwjgl.opengl.GL33.*;

public enum GlFilter{

    NEAREST_MIPMAP_NEAREST (GL_NEAREST_MIPMAP_NEAREST),
    LINEAR_MIPMAP_NEAREST  (GL_LINEAR_MIPMAP_NEAREST),
    NEAREST_MIPMAP_LINEAR  (GL_NEAREST_MIPMAP_LINEAR),
    LINEAR_MIPMAP_LINEAR   (GL_LINEAR_MIPMAP_LINEAR),

    NEAREST                (GL_NEAREST),
    LINEAR                 (GL_LINEAR);


    public final int GL;

    GlFilter(int GL){
        this.GL = GL;
    }

}
