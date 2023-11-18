package jpize.gl.buffer;

import static org.lwjgl.opengl.GL44.*;

public enum GlBufStorageFlag{

    READ           (GL_MAP_READ_BIT      ),
    WRITE          (GL_MAP_WRITE_BIT     ),

    PERSISTENT     (GL_MAP_PERSISTENT_BIT),
    CLIENT_STORAGE (GL_CLIENT_STORAGE_BIT),
    COHERENT       (GL_MAP_COHERENT_BIT  );

    public final int GL;

    GlBufStorageFlag(int GL){
        this.GL = GL;
    }

}
