package jpize.sdl.event.callback.window;

import jpize.sdl.window.SdlWindow;

@FunctionalInterface
public interface WinTakeFocusCallback{

    void invoke(SdlWindow window);

}
