package jpize.ui.palette;

import jpize.Jpize;
import jpize.ui.component.UIPressCallback;
import jpize.ui.component.UIReleaseCallback;
import jpize.util.math.Maths;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Slider extends Rect{

    private final Rect lineBg;
    private final Rect line;
    private final Rect handle;

    private float value;
    private boolean _grabHandle;
    private final List<SliderCallback> _callbacks;
    private final Constraint _lineWidth;

    public Slider(Constraint width, Constraint height){
        super(width, height);
        super.background().color().setA(0);
        this._callbacks = new CopyOnWriteArrayList<>();

        this.handle = new Rect(Constr.relh(1), Constr.aspect(1));
        this.handle.setOrder(1);
        super.add(handle);

        final Constraint lineBgHeight = Constr.px(() -> super.cache.height * 0.35F);
        final Constraint lineBgWidth  = Constr.px(() -> super.cache.width - handle.cache().height + lineBgHeight.numValue());
        final Constraint lineHeight   = Constr.px(() -> super.cache.height * 0.42F);
        this._lineWidth = Constr.px(() -> super.cache.width - handle.cache().height + lineHeight.numValue());

        this.lineBg = new Rect(lineBgWidth, lineBgHeight);
        super.add(lineBg);
        this.line = new Rect(_lineWidth, lineHeight);
        super.add(line);

        setupSubcomponent();
    }

    public Slider(Constraint size){
        this(size, size);
    }


    public Rect lineBg(){
        return lineBg;
    }

    public Rect line(){
        return line;
    }

    public Rect handle(){
        return handle;
    }


    @Override
    public void render(){
        if(_grabHandle){
            final float handleWidth = handle.cache().width;
            final float grabX = Jpize.getX() - cache.x - handleWidth / 2;
            final float sliderWidth = cache.width - handleWidth;
            final float value = Maths.clamp01(grabX / sliderWidth);
            if(value != this.value){
                setValue(value);
                invokeCallbacks();
            }
        }

        final float maxValue = (1 - handle.cache().width / super.cache.width);
        handle.padding().setLeft(Constr.relw(maxValue * value));
        line.size().setX(Constr.px(() -> line.cache().height + (_lineWidth.numValue() - line.cache().height) * value));
    }


    public float getValue(){
        return value;
    }

    public void setValue(float value){
        value = Maths.clamp01(value);
        if(this.value != value)
            this.value = value;
    }


    public void addSliderCallback(SliderCallback callback){
        _callbacks.add(callback);
    }

    public void removeCallback(SliderCallback callback){
        _callbacks.remove(callback);
    }

    private void invokeCallbacks(){
        for(SliderCallback callback: _callbacks)
            callback.invoke(this, value);
    }

    private void setupSubcomponent(){
        final UIPressCallback pressCallback = (comp, btn) -> _grabHandle = true;
        final UIReleaseCallback releaseCallback = (comp, btn) -> _grabHandle = false;

        this.lineBg.padding().set(Constr.zero, Constr.px(() -> (handle.cache().height - lineBg.cache().height) * 0.5F), Constr.zero, Constr.auto);
        this.lineBg.background().color().set(0.85);
        this.lineBg.setCornerRadius(Constr.relh(0.5));
        this.lineBg.setClickable(true);
        this.lineBg.addPressCallback(pressCallback);
        this.lineBg.addReleaseCallback(releaseCallback);

        this.line.padding().set(Constr.zero, Constr.px(() -> (handle.cache().height - line.cache().height) * 0.5F), Constr.zero, Constr.auto);
        this.line.background().color().set(0.25, 0.0, 0.7);
        this.line.setCornerRadius(Constr.relh(0.5));
        this.line.setClickable(true);
        this.line.addPressCallback(pressCallback);
        this.line.addReleaseCallback(releaseCallback);

        this.handle.padding().set(Constr.zero, Constr.zero, Constr.zero, Constr.auto);
        this.handle.background().color().set(0.35, 0.1, 0.9);
        this.handle.setCornerRadius(Constr.relh(0.5));
        this.handle.setClickable(true);
        this.handle.addPressCallback(pressCallback);
        this.handle.addReleaseCallback(releaseCallback);
    }

}
