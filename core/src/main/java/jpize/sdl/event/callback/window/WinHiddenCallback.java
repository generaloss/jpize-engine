package jpize.sdl.event.callback.window;

import jpize.sdl.window.SdlWindow;

@FunctionalInterface
public interface WinHiddenCallback{

    void invoke(SdlWindow window);

}
