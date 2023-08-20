package pize.lib.gl.tesselation;

import static org.lwjgl.opengl.GL33.*;

public enum GlPrimitive{

    POINTS         (GL_POINTS),
    LINES          (GL_LINES),
    LINE_STRIP     (GL_LINE_STRIP),
    LINE_LOOP      (GL_LINE_LOOP),
    POLYGON        (GL_POLYGON),
    QUADS          (GL_QUADS),
    QUAD_STRIP     (GL_QUAD_STRIP),
    TRIANGLES      (GL_TRIANGLES),
    TRIANGLE_STRIP (GL_TRIANGLE_STRIP),
    TRIANGLE_FAN   (GL_TRIANGLE_FAN);


    public final int GL;

    GlPrimitive(int GL){
        this.GL = GL;
    }

}
