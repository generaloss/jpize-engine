package jpize.ui.palette;

import jpize.Jpize;
import jpize.graphics.font.BitmapFont;
import jpize.math.Maths;
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

    public Slider(Constraint width, Constraint height, String text, BitmapFont font){
        super(width, height);
        super.input().addPressCallback(((comp, btn) -> grabHandle = true));
        super.input().addReleaseCallback(((comp, btn) -> grabHandle = false));
        this.callbacks = new CopyOnWriteArrayList<>();

        this.handle = new Rect(Constr.relh(0.5), height);
        this.handle.input().setClickable(false);
        super.add(handle);

        this.textview = new TextView(text, font);
        textview.padding().set(Constr.zero);
        textview.color().set(0.1, 0.1, 0.1, 1);
        super.add(textview);
    }

    public Slider(Constraint size, String text, BitmapFont font){
        this(size, size, text, font);
    }


    public Rect handle(){
        return handle;
    }

    public TextView textview(){
        return textview;
    }


    @Override
    public void render(){
        super.render();
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
