package jpize.glfw.glfw;

import static org.lwjgl.glfw.GLFW.*;

public enum GlfwHint{

    FOCUSED                  (GLFW_FOCUSED                 ),
    ICONIFIED                (GLFW_ICONIFIED               ),
    RESIZABLE                (GLFW_RESIZABLE               ),
    VISIBLE                  (GLFW_VISIBLE                 ),
    DECORATED                (GLFW_DECORATED               ),
    AUTO_ICONIFY             (GLFW_AUTO_ICONIFY            ),
    FLOATING                 (GLFW_FLOATING                ),
    MAXIMIZED                (GLFW_MAXIMIZED               ),
    CENTER_CURSOR            (GLFW_CENTER_CURSOR           ),
    TRANSPARENT_FRAMEBUFFER  (GLFW_TRANSPARENT_FRAMEBUFFER ),
    HOVERED                  (GLFW_HOVERED                 ),
    FOCUS_ON_SHOW            (GLFW_FOCUS_ON_SHOW           ),
    RED_BITS                 (GLFW_RED_BITS                ),
    GREEN_BITS               (GLFW_GREEN_BITS              ),
    BLUE_BITS                (GLFW_BLUE_BITS               ),
    ALPHA_BITS               (GLFW_ALPHA_BITS              ),
    DEPTH_BITS               (GLFW_DEPTH_BITS              ),
    STENCIL_BITS             (GLFW_STENCIL_BITS            ),
    ACCUM_RED_BITS           (GLFW_ACCUM_RED_BITS          ),
    ACCUM_GREEN_BITS         (GLFW_ACCUM_GREEN_BITS        ),
    ACCUM_BLUE_BITS          (GLFW_ACCUM_BLUE_BITS         ),
    ACCUM_ALPHA_BITS         (GLFW_ACCUM_ALPHA_BITS        ),
    AUX_BUFFERS              (GLFW_AUX_BUFFERS             ),
    STEREO                   (GLFW_STEREO                  ),
    SAMPLES                  (GLFW_SAMPLES                 ),
    SRGB_CAPABLE             (GLFW_SRGB_CAPABLE            ),
    REFRESH_RATE             (GLFW_REFRESH_RATE            ),
    DOUBLEBUFFER             (GLFW_DOUBLEBUFFER            ),
    CLIENT_API               (GLFW_CLIENT_API              ),
    CONTEXT_VERSION_MAJOR    (GLFW_CONTEXT_VERSION_MAJOR   ),
    CONTEXT_VERSION_MINOR    (GLFW_CONTEXT_VERSION_MINOR   ),
    CONTEXT_REVISION         (GLFW_CONTEXT_REVISION        ),
    CONTEXT_ROBUSTNESS       (GLFW_CONTEXT_ROBUSTNESS      ),
    OPENGL_FORWARD_COMPAT    (GLFW_OPENGL_FORWARD_COMPAT   ),
    OPENGL_DEBUG_CONTEXT     (GLFW_OPENGL_DEBUG_CONTEXT    ),
    OPENGL_PROFILE           (GLFW_OPENGL_PROFILE          ),
    CONTEXT_RELEASE_BEHAVIOR (GLFW_CONTEXT_RELEASE_BEHAVIOR),
    CONTEXT_NO_ERROR         (GLFW_CONTEXT_NO_ERROR        ),
    CONTEXT_CREATION_API     (GLFW_CONTEXT_CREATION_API    ),
    SCALE_TO_MONITOR         (GLFW_SCALE_TO_MONITOR        ),
    COCOA_RETINA_FRAMEBUFFER (GLFW_COCOA_RETINA_FRAMEBUFFER),
    COCOA_FRAME_NAME         (GLFW_COCOA_FRAME_NAME        ),
    COCOA_GRAPHICS_SWITCHING (GLFW_COCOA_GRAPHICS_SWITCHING),
    X11_CLASS_NAME           (GLFW_X11_CLASS_NAME          ),
    X11_INSTANCE_NAME        (GLFW_X11_INSTANCE_NAME       );


    public final int GLFW;

    GlfwHint(int GLFW){
        this.GLFW = GLFW;
    }

}
