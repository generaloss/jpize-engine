package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinFocusLostCallback{

    void invoke(Window window);

}
