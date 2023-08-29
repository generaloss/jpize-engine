package jpize.glfw.mouse;

import static org.lwjgl.glfw.GLFW.*;

public enum GlfwCursorMode{

    NORMAL   (GLFW_CURSOR_NORMAL  ),
    HIDDEN   (GLFW_CURSOR_HIDDEN  ),
    DISABLED (GLFW_CURSOR_DISABLED);

    public final int GLFW;

    GlfwCursorMode(int GLFW){
        this.GLFW = GLFW;
    }

    public static GlfwCursorMode fromGLFW(int GLFW){
        return values()[GLFW - GLFW_CURSOR_NORMAL];
    }

}
