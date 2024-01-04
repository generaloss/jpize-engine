package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinRestoredCallback{

    void invoke(Window window);

}
