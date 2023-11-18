package jpize.gl.buffer;

import static org.lwjgl.opengl.GL15.*;

public enum GlBufAccess{

    READ_ONLY  (GL_READ_ONLY ),
    WRITE_ONLY (GL_WRITE_ONLY),
    READ_WRITE (GL_READ_WRITE);

    public final int GL;

    GlBufAccess(int GL){
        this.GL = GL;
    }

}
