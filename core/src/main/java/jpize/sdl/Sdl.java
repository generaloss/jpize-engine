package jpize.sdl;

import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.IntByReference;
import io.github.libsdl4j.api.SdlSubSystemConst;
import io.github.libsdl4j.api.timer.SdlTimer;
import io.github.libsdl4j.api.video.SdlVideo;
import jpize.Jpize;
import jpize.sdl.gl.SdlGlAttr;
import jpize.sdl.window.SdlWindow;

import static io.github.libsdl4j.api.Sdl.SDL_Init;
import static io.github.libsdl4j.api.Sdl.SDL_Quit;
import static io.github.libsdl4j.api.error.SdlError.SDL_GetError;

public class Sdl{

    private static final IntByReference tmp_int_1 = new IntByReference();
    private static final FloatByReference tmp_float_1 = new FloatByReference();


    public static void init(){
        final int sdlInitResult = SDL_Init(SdlSubSystemConst.SDL_INIT_VIDEO);
        if(sdlInitResult != 0)
            throw new IllegalStateException("Unable to init SDL lib (Code " + sdlInitResult + "): " + SDL_GetError());
    }


    public SdlWindow getCurrentWindow(){
        return new SdlWindow(SdlVideo.SDL_GL_GetCurrentWindow());
    }

    public SdlWindow getGrabbedWindow(){
        return new SdlWindow(SdlVideo.SDL_GetGrabbedWindow());
    }

    public String getCurrentVideoDriver(){
        return SdlVideo.SDL_GetCurrentVideoDriver();
    }


    public static int getGlAttribute(SdlGlAttr attribute){
        SdlVideo.SDL_GL_GetAttribute(attribute.SDL, tmp_int_1);
        return tmp_int_1.getValue();
    }

    public static void setGlAttribute(SdlGlAttr attribute, int value){
        SdlVideo.SDL_GL_SetAttribute(attribute.SDL, value);
    }

    public static void resetGlAttributes(){
        SdlVideo.SDL_GL_ResetAttributes();
    }


    public static int getSwapInterval(){
        return SdlVideo.SDL_GL_GetSwapInterval();
    }

    public static int setSwapInterval(int interval){
        return SdlVideo.SDL_GL_SetSwapInterval(interval);
    }


    public static boolean isVsyncEnabled(){
        return Math.abs(getSwapInterval()) == 1;
    }

    public static void enableVsync(boolean vsync){
        setSwapInterval(vsync ? 1 : 0);
    }


    public static int getTicks(){
        return SdlTimer.SDL_GetTicks();
    }

    public static void delay(int ticks){
        SdlTimer.SDL_Delay(ticks);
    }


    public static float getDisplayDDPI(int displayIndex){
        SdlVideo.SDL_GetDisplayDPI(0, tmp_float_1, null, null);
        return tmp_float_1.getValue();
    }

    public static float getDisplayHDPI(int displayIndex){
        SdlVideo.SDL_GetDisplayDPI(0, null, tmp_float_1, null);
        return tmp_float_1.getValue();
    }

    public static float getDisplayVDPI(int displayIndex){
        SdlVideo.SDL_GetDisplayDPI(0, null, null, tmp_float_1);
        return tmp_float_1.getValue();
    }


    public static float getDisplayDDPI(){
        return getDisplayDDPI(Jpize.window().getDisplayIndex());
    }

    public static float getDisplayHDPI(){
        return getDisplayHDPI(Jpize.window().getDisplayIndex());
    }

    public static float getDisplayVDPI(){
        return getDisplayVDPI(Jpize.window().getDisplayIndex());
    }


    public static void quit(){
        SDL_Quit();
    }

}
