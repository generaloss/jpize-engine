package jpize.ui.component;

import jpize.sdl.input.Btn;

@FunctionalInterface
public interface UIPressCallback{

    void invoke(UIComponent comp, Btn btn);

}
