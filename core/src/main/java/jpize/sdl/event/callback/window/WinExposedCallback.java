package jpize.sdl.event.callback.window;

import jpize.sdl.window.SdlWindow;

@FunctionalInterface
public interface WinExposedCallback{

    void invoke(SdlWindow window);

}
