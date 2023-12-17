package jpize.sdl.event.window;

import jpize.io.Window;

@FunctionalInterface
public interface WinDisplayChangeCallback{

    void invoke(Window window, int data1);

}
