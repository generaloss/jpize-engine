package jpize.sdl.event.window;

import jpize.io.Window;

public interface WinResizedCallback{

    void invoke(Window window, int width, int height);

}
