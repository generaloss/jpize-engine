package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinIccProfChangedCallback{

    void invoke(Window window);

}
