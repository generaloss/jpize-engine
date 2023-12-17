package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinLeaveCallback{

    void invoke(Window window);

}
