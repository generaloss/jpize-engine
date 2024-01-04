package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinSizeChangedCallback{

    void invoke(Window window, int width, int height);

}
