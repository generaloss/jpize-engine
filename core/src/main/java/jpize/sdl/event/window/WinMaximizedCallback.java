package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinMaximizedCallback{

    void invoke(Window window);

}
