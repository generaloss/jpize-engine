package pize.graphics.gl;

import static org.lwjgl.opengl.GL11.*;

public enum Mode{
    
    FASTEST   (GL_FASTEST),
    NICEST    (GL_NICEST),
    DONT_CARE (GL_DONT_CARE);
    
    
    public final int GL;
    
    Mode(int GL){
        this.GL = GL;
    }
    
}
