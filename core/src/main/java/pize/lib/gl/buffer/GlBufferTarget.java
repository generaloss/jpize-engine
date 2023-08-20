package pize.lib.gl.buffer;

import static org.lwjgl.opengl.GL46.*;

public enum GlBufferTarget{

    ARRAY_BUFFER              (GL_ARRAY_BUFFER             ),
    ELEMENT_BUFFER            (GL_ELEMENT_ARRAY_BUFFER     ),
    QUERY_BUFFER              (GL_QUERY_BUFFER             ),
    TEXTURE_BUFFER            (GL_TEXTURE_BUFFER           ),
    UNIFORM_BUFFER            (GL_UNIFORM_BUFFER           ),

    DRAW_INDIRECT_BUFFER      (GL_DRAW_INDIRECT_BUFFER     ),
    DISPATCH_INDIRECT_BUFFER  (GL_DISPATCH_INDIRECT_BUFFER ),
    TRANSFORM_FEEDBACK_BUFFER (GL_TRANSFORM_FEEDBACK_BUFFER),
    ATOMIC_COUNTER_BUFFER     (GL_ATOMIC_COUNTER_BUFFER    ),
    SHADER_STORAGE_BUFFER     (GL_SHADER_STORAGE_BUFFER    ),

    PIXEL_UNPACK_BUFFER       (GL_PIXEL_UNPACK_BUFFER      ),
    PIXEL_PACK_BUFFER         (GL_PIXEL_PACK_BUFFER        ),

    COPY_READ_BUFFER          (GL_COPY_READ_BUFFER         ),
    COPY_WRITE_BUFFER         (GL_COPY_WRITE_BUFFER        );


    public final int GL;

    GlBufferTarget(int GL){
        this.GL = GL;
    }

}