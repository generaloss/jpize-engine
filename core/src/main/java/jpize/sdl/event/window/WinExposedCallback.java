package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinExposedCallback{

    void invoke(Window window);

}
