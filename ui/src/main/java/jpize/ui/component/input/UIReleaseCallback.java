package jpize.ui.component.input;

import jpize.sdl.input.Btn;
import jpize.ui.component.UIComponent;

@FunctionalInterface
public interface UIReleaseCallback{

    void invoke(UIComponent component, Btn button);

}
