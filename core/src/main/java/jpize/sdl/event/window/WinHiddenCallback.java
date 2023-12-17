package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinHiddenCallback{

    void invoke(Window window);

}
