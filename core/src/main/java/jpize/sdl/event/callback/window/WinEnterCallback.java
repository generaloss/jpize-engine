package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinEnterCallback{

    void invoke(Window window);

}
