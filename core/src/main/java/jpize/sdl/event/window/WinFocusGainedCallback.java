package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinFocusGainedCallback{

    void invoke(Window window);

}
