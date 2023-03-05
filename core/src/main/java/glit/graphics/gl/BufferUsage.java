package glit.graphics.gl;

import static org.lwjgl.opengl.GL33.*;

public enum BufferUsage{

    STATIC_DRAW  (GL_STATIC_DRAW ),
    DYNAMIC_DRAW (GL_DYNAMIC_DRAW),
    STREAM_DRAW  (GL_STREAM_DRAW ),

    STATIC_READ  (GL_STATIC_READ ),
    DYNAMIC_READ (GL_DYNAMIC_READ),
    STREAM_READ  (GL_STREAM_READ ),

    STATIC_COPY  (GL_STATIC_COPY ),
    DYNAMIC_COPY (GL_DYNAMIC_COPY),
    STREAM_COPY  (GL_STREAM_COPY );

    public final int gl;

    BufferUsage(int gl){
        this.gl = gl;
    }

}
