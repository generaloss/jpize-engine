package jpize.ui.palette.callback;

import jpize.ui.palette.Slider;

@FunctionalInterface
public interface SliderCallback{

    void invoke(Slider view, float value);

}
