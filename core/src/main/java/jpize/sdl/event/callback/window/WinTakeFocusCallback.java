package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinTakeFocusCallback{

    void invoke(Window window);

}
