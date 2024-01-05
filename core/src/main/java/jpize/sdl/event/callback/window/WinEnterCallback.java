package jpize.sdl.event.callback.window;

import jpize.sdl.window.SdlWindow;

@FunctionalInterface
public interface WinEnterCallback{

    void invoke(SdlWindow window);

}
