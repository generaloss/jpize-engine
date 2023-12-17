package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinIccProfChangedCallback{

    void invoke(Window window);

}
