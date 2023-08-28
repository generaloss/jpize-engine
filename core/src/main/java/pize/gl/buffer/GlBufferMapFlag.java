package pize.gl.buffer;


import static org.lwjgl.opengl.GL44.*;

public enum GlBufferMapFlag{

    READ              (GL_MAP_READ_BIT             ),
    WRITE             (GL_MAP_WRITE_BIT            ),

    INVALIDATE_BUFFER (GL_MAP_INVALIDATE_BUFFER_BIT),
    INVALIDATE_RANGE  (GL_MAP_INVALIDATE_RANGE_BIT ),

    PERSISTENT        (GL_MAP_PERSISTENT_BIT       ),
    EXPLICIT_FLUSH    (GL_MAP_FLUSH_EXPLICIT_BIT   ),
    COHERENT          (GL_MAP_COHERENT_BIT         ),
    UNSYNCHRONIZED    (GL_MAP_UNSYNCHRONIZED_BIT   );

    public final int GL;

    GlBufferMapFlag(int GL){
        this.GL = GL;
    }

}
