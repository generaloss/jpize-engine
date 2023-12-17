package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinMinimizedCallback{

    void invoke(Window window);

}
