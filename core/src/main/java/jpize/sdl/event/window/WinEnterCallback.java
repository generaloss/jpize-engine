package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinEnterCallback{

    void invoke(Window window);

}
