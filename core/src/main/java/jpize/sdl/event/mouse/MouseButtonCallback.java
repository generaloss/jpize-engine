package jpize.sdl.event.mouse;

import jpize.sdl.input.Btn;

public interface MouseButtonCallback{

    void invoke(Btn button, MouseButtonAction action);

}
