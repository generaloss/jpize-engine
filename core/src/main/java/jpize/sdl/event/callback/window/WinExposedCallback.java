package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinExposedCallback{

    void invoke(Window window);

}
