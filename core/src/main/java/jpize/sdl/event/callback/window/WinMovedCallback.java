package jpize.sdl.event.callback.window;

import jpize.sdl.window.SdlWindow;

@FunctionalInterface
public interface WinMovedCallback{

    void invoke(SdlWindow window, int x, int y);

}
