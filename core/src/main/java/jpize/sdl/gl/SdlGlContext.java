package jpize.sdl.gl;

import com.sun.jna.ptr.IntByReference;
import io.github.libsdl4j.api.error.SdlError;
import io.github.libsdl4j.api.video.SDL_GLContext;
import io.github.libsdl4j.api.video.SDL_Window;
import io.github.libsdl4j.api.video.SdlVideo;
import jpize.sdl.window.SdlWindow;
import jpize.util.Disposable;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLCapabilities;

public class SdlGlContext implements Disposable{

    private static final IntByReference tmp_int_1 = new IntByReference();


    private final SDL_Window windowSDL;
    private final SDL_GLContext glContextSDL;
    private final GLCapabilities capabilities;

    public SdlGlContext(SdlWindow window){
        this.windowSDL = window.getSDL();

        this.glContextSDL = SdlVideo.SDL_GL_CreateContext(windowSDL);
        if(this.glContextSDL == null)
            throw new IllegalStateException("Unable to create SDL GL Context: " + SdlError.SDL_GetError());

        this.capabilities = GL.createCapabilities();
    }

    public SDL_GLContext getSDL(){
        return glContextSDL;
    }

    public GLCapabilities getCapabilities(){
        return capabilities;
    }


    public void makeCurrent(){
        SdlVideo.SDL_GL_MakeCurrent(windowSDL, glContextSDL);
        GL.setCapabilities(capabilities);
    }


    @Override
    public void dispose(){
        SdlVideo.SDL_GL_DeleteContext(glContextSDL);
    }

}
