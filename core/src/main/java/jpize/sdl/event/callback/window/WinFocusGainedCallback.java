package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinFocusGainedCallback{

    void invoke(Window window);

}
