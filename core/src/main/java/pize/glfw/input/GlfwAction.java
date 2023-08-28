package pize.glfw.input;

import static org.lwjgl.glfw.GLFW.*;

public enum GlfwAction{
    
    RELEASE (GLFW_RELEASE),
    PRESS   (GLFW_PRESS  ),
    REPEAT  (GLFW_REPEAT );


    public final int GLFW;

    GlfwAction(int GLFW){
        this.GLFW = GLFW;
    }

    public static GlfwAction fromGLFW(int GLFW){
        return values()[GLFW];
    }
    
}
