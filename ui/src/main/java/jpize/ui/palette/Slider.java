package jpize.ui.palette;

import jpize.Jpize;
import jpize.graphics.font.Font;
import jpize.util.math.Maths;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;
import jpize.ui.palette.callback.SliderCallback;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Slider extends Rect{

    private final Rect handle;
    private final TextView textview;
    private boolean grabHandle;
    private float value;
    private final List<SliderCallback> callbacks;

    public Slider(Constraint width, Constraint height, String text, Font font, Constraint text_size){
        super(width, height);
        super.input().addPressCallback(((comp, btn) -> grabHandle = true));
        super.input().addReleaseCallback(((comp, btn) -> grabHandle = false));
        this.callbacks = new CopyOnWriteArrayList<>();

        this.handle = new Rect(Constr.relh(0.5), Constr.relh(1));
        this.handle.setID("handle");
        this.handle.padding().set(Constr.zero, Constr.zero, Constr.zero, Constr.auto);
        this.handle.input().setClickable(true);
        super.add(handle);

        this.textview = new TextView(text, font, text_size);
        this.textview.setID("text");
        this.textview.padding().set(Constr.zero);
        this.textview.color().set(0.1);
        super.add(textview);
    }

    public Slider(Constraint width, Constraint height, String text, Font font){
        this(width, height, text, font, Constr.match_parent);
    }

    public Slider(Constraint size, String text, Font font, Constraint text_size){
        this(size, size, text, font, text_size);
    }

    public Slider(Constraint size, String text, Font font){
        this(size, text, font, Constr.match_parent);
    }


    public Rect handle(){
        return handle;
    }

    public TextView textview(){
        return textview;
    }


    @Override
    public void render(){
        if(grabHandle){
            final float handleWidth = handle.cache().width;
            final float grabX = Jpize.getX() - cache.x - handleWidth / 2;
            final float sliderWidth = cache.width - handleWidth;
            final float value = grabX / sliderWidth;
            if(value != this.value){
                setValue(value);
                invokeCallbacks();
            }
        }
    }


    public float getValue(){
        return value;
    }

    public void setValue(float value){
        if(value == this.value)
            return;

        value = Maths.clamp(value, 0, 1);
        this.value = value;

        final float handleWidth = handle.cache().width;
        final float sliderWidth = cache.width - handleWidth;
        handle.padding().setLeft(Constr.relw(value * (sliderWidth / cache.width)));
    }


    public void addSliderCallback(SliderCallback callback){
        callbacks.add(callback);
    }

    public void removeCallback(SliderCallback callback){
        callbacks.remove(callback);
    }

    private void invokeCallbacks(){
        for(SliderCallback callback: callbacks)
            callback.invoke(this, value);
    }

}
