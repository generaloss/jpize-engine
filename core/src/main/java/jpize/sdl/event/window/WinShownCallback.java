package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinShownCallback{

    void invoke(Window window);

}
