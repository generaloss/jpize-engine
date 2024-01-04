package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinCloseCallback{

    void invoke(Window window);

}
