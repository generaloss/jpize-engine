package jpize.sdl.event.callback.window;

import jpize.sdl.window.SdlWindow;

@FunctionalInterface
public interface WinCloseCallback{

    void invoke(SdlWindow window);

}
