package pize.glfw.glfw;

import static org.lwjgl.glfw.GLFW.*;

public enum GlfwPlatform{

    WIN32   (GLFW_PLATFORM_WIN32  ),
    COCOA   (GLFW_PLATFORM_COCOA  ),
    WAYLAND (GLFW_PLATFORM_WAYLAND),
    X11     (GLFW_PLATFORM_X11    ),
    NULL    (GLFW_PLATFORM_NULL   );

    public final int GLFW;

    GlfwPlatform(int GLFW){
        this.GLFW = GLFW;
    }

    public static GlfwPlatform fromGLFW(int GLFW){
        return values()[GLFW - GLFW_PLATFORM_WIN32];
    }

}
