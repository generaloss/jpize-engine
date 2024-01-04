package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinShownCallback{

    void invoke(Window window);

}
