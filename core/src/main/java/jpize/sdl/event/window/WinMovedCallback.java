package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinMovedCallback{

    void invoke(Window window, int x, int y);

}
