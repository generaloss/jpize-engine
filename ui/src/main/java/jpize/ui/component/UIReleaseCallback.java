package jpize.ui.component;

import jpize.sdl.input.Btn;

@FunctionalInterface
public interface UIReleaseCallback{

    void invoke(UIComponent view, Btn button);

}
