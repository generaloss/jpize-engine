package jpize.sdl.event.callback.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinHitTestCallback{

    void invoke(Window window);

}
