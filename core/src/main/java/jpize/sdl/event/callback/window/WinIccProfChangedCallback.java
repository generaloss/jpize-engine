package jpize.sdl.event.callback.window;

import jpize.sdl.window.SdlWindow;

@FunctionalInterface
public interface WinIccProfChangedCallback{

    void invoke(SdlWindow window);

}
