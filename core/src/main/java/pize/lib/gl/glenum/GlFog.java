package pize.lib.gl.glenum;

import static org.lwjgl.opengl.GL46.*;

public enum GlFog{
    
    MODE      (GL_FOG_MODE),
    DENSITY   (GL_FOG_DENSITY),
    START     (GL_FOG_START),
    END       (GL_FOG_END),
    INDEX     (GL_FOG_INDEX),
    COORD_SRC (GL_FOG_COORD_SRC),
    COLOR     (GL_FOG_COLOR);
    
    
    public final int GL;
    
    GlFog(int GL){
        this.GL = GL;
    }
    
}
