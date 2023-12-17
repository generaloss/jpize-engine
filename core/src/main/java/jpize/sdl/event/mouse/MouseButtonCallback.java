package jpize.sdl.event.mouse;

import jpize.sdl.input.Btn;

@FunctionalInterface
public interface MouseButtonCallback{

    void invoke(Btn button, MouseButtonAction action);

}
