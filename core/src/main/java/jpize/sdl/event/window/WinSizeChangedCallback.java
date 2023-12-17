package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinSizeChangedCallback{

    void invoke(Window window, int width, int height);

}
