package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinHitTestCallback{

    void invoke(Window window);

}
