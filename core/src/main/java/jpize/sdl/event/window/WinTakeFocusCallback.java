package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinTakeFocusCallback{

    void invoke(Window window);

}
