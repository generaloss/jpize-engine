package jpize.gl.buffer;

import static org.lwjgl.opengl.GL44.*;

public enum GlBufferParameter{

    BUFFER_ACCESS            (GL_BUFFER_ACCESS           ),
    BUFFER_ACCESS_FLAGS      (GL_BUFFER_ACCESS_FLAGS     ),
    BUFFER_IMMUTABLE_STORAGE (GL_BUFFER_IMMUTABLE_STORAGE),
    BUFFER_MAPPED            (GL_BUFFER_MAPPED           ),
    BUFFER_MAP_LENGTH        (GL_BUFFER_MAP_LENGTH       ),
    BUFFER_MAP_OFFSET        (GL_BUFFER_MAP_OFFSET       ),
    BUFFER_SIZE              (GL_BUFFER_SIZE             ),
    BUFFER_STORAGE_FLAGS     (GL_BUFFER_STORAGE_FLAGS    ),
    BUFFER_USAGE             (GL_BUFFER_USAGE            );

    public final int GL;

    GlBufferParameter(int GL){
        this.GL = GL;
    }

}
