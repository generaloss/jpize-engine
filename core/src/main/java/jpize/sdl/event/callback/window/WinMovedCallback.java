package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinMovedCallback{

    void invoke(Window window, int x, int y);

}
