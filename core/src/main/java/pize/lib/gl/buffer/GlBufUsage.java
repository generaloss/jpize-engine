package pize.lib.gl.buffer;

import static org.lwjgl.opengl.GL33.*;

public enum GlBufUsage{

    STATIC_DRAW  (GL_STATIC_DRAW ),
    DYNAMIC_DRAW (GL_DYNAMIC_DRAW),
    STREAM_DRAW  (GL_STREAM_DRAW ),

    STATIC_READ  (GL_STATIC_READ ),
    DYNAMIC_READ (GL_DYNAMIC_READ),
    STREAM_READ  (GL_STREAM_READ ),

    STATIC_COPY  (GL_STATIC_COPY ),
    DYNAMIC_COPY (GL_DYNAMIC_COPY),
    STREAM_COPY  (GL_STREAM_COPY );

    public final int GL;

    GlBufUsage(int GL){
        this.GL = GL;
    }

}
