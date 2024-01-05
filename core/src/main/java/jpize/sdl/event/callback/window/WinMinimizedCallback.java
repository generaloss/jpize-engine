package jpize.sdl.event.callback.window;

import jpize.sdl.window.SdlWindow;

@FunctionalInterface
public interface WinMinimizedCallback{

    void invoke(SdlWindow window);

}
