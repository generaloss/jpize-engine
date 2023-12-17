package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinRestoredCallback{

    void invoke(Window window);

}
