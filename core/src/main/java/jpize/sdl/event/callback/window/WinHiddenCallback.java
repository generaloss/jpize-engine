package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinHiddenCallback{

    void invoke(Window window);

}
