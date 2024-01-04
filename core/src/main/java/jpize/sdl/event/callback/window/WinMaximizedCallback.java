package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinMaximizedCallback{

    void invoke(Window window);

}
