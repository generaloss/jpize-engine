package jpize.ui.component.input;

import jpize.sdl.input.Btn;
import jpize.ui.component.UIComponent;

public interface UIPressCallback{

    void invoke(UIComponent comp, Btn btn);

}
