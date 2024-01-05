package jpize.sdl.event.callback.window;

import jpize.sdl.window.SdlWindow;

@FunctionalInterface
public interface WinRestoredCallback{

    void invoke(SdlWindow window);

}
