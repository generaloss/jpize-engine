package jpize.sdl.event.callback.window;

import jpize.sdl.window.SdlWindow;

@FunctionalInterface
public interface WinShownCallback{

    void invoke(SdlWindow window);

}
