package pize.lib.gl.glenum;

import static org.lwjgl.opengl.GL33.*;

public enum GlHint{
    
    FOG_HINT                    (GL_FOG_HINT),
    GENERATE_MIPMAP_HINT        (GL_GENERATE_MIPMAP_HINT),
    LINE_SMOOTH_HINT            (GL_LINE_SMOOTH_HINT),
    PERSPECTIVE_CORRECTION_HINT (GL_PERSPECTIVE_CORRECTION_HINT),
    POINT_SMOOTH_HINT           (GL_POINT_SMOOTH_HINT),
    POLYGON_SMOOTH_HINT         (GL_POLYGON_SMOOTH_HINT),
    TEXTURE_COMPRESSION_HINT    (GL_TEXTURE_COMPRESSION_HINT);
    
    
    public final int GL;
    
    GlHint(int GL){
        this.GL = GL;
    }
    
}