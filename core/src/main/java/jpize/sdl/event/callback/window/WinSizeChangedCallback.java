package jpize.sdl.event.callback.window;

import jpize.sdl.window.SdlWindow;

@FunctionalInterface
public interface WinSizeChangedCallback{

    void invoke(SdlWindow window, int width, int height);

}
