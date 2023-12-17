package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinCloseCallback{

    void invoke(Window window);

}
