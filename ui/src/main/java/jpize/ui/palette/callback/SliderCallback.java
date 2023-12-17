package jpize.ui.palette.callback;

import jpize.ui.component.UIComponent;

@FunctionalInterface
public interface SliderCallback{

    void invoke(UIComponent component, float value);

}
