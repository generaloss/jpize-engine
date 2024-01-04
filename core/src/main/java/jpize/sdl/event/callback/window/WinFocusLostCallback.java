package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinFocusLostCallback{

    void invoke(Window window);

}
