package jpize.sdl.event.callback.window;

import jpize.sdl.window.SdlWindow;

@FunctionalInterface
public interface WinDisplayChangeCallback{

    void invoke(SdlWindow window, int data1);

}
