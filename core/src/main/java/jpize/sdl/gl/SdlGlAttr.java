package jpize.sdl.gl;

import static io.github.libsdl4j.api.video.SDL_GLattr.*;

public enum SdlGlAttr{

    GL_RED_SIZE                  (SDL_GL_RED_SIZE                  ),
    GL_GREEN_SIZE                (SDL_GL_GREEN_SIZE                ),
    GL_BLUE_SIZE                 (SDL_GL_BLUE_SIZE                 ),
    GL_ALPHA_SIZE                (SDL_GL_ALPHA_SIZE                ),
    GL_BUFFER_SIZE               (SDL_GL_BUFFER_SIZE               ),
    GL_DOUBLEBUFFER              (SDL_GL_DOUBLEBUFFER              ),
    GL_DEPTH_SIZE                (SDL_GL_DEPTH_SIZE                ),
    GL_STENCIL_SIZE              (SDL_GL_STENCIL_SIZE              ),
    GL_ACCUM_RED_SIZE            (SDL_GL_ACCUM_RED_SIZE            ),
    GL_ACCUM_GREEN_SIZE          (SDL_GL_ACCUM_GREEN_SIZE          ),
    GL_ACCUM_BLUE_SIZE           (SDL_GL_ACCUM_BLUE_SIZE           ),
    GL_ACCUM_ALPHA_SIZE          (SDL_GL_ACCUM_ALPHA_SIZE          ),
    GL_STEREO                    (SDL_GL_STEREO                    ),
    GL_MULTISAMPLEBUFFERS        (SDL_GL_MULTISAMPLEBUFFERS        ),
    GL_MULTISAMPLESAMPLES        (SDL_GL_MULTISAMPLESAMPLES        ),
    GL_ACCELERATED_VISUAL        (SDL_GL_ACCELERATED_VISUAL        ),
    GL_RETAINED_BACKING          (SDL_GL_RETAINED_BACKING          ),
    GL_CONTEXT_MAJOR_VERSION     (SDL_GL_CONTEXT_MAJOR_VERSION     ),
    GL_CONTEXT_MINOR_VERSION     (SDL_GL_CONTEXT_MINOR_VERSION     ),
    GL_CONTEXT_EGL               (SDL_GL_CONTEXT_EGL               ),
    GL_CONTEXT_FLAGS             (SDL_GL_CONTEXT_FLAGS             ),
    GL_CONTEXT_PROFILE_MASK      (SDL_GL_CONTEXT_PROFILE_MASK      ),
    GL_SHARE_WITH_CURRENT_CONTEXT(SDL_GL_SHARE_WITH_CURRENT_CONTEXT),
    GL_FRAMEBUFFER_SRGB_CAPABLE  (SDL_GL_FRAMEBUFFER_SRGB_CAPABLE  ),
    GL_CONTEXT_RELEASE_BEHAVIOR  (SDL_GL_CONTEXT_RELEASE_BEHAVIOR  ),
    GL_CONTEXT_RESET_NOTIFICATION(SDL_GL_CONTEXT_RESET_NOTIFICATION),
    GL_CONTEXT_NO_ERROR          (SDL_GL_CONTEXT_NO_ERROR          ),
    GL_FLOATBUFFERS              (SDL_GL_FLOATBUFFERS              );

    public final int SDL;

    SdlGlAttr(int SDL){
        this.SDL = SDL;
    }

}
