package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinLeaveCallback{

    void invoke(Window window);

}
