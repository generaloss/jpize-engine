package jpize.gl.glenum;

import static org.lwjgl.opengl.GL11.*;

public enum GlMode{
    
    FASTEST   (GL_FASTEST),
    NICEST    (GL_NICEST),
    DONT_CARE (GL_DONT_CARE);
    
    
    public final int GL;
    
    GlMode(int GL){
        this.GL = GL;
    }
    
}
