package jpize.ui.palette.layout;

import jpize.Jpize;
import jpize.ui.component.AbstractLayout;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;
import jpize.ui.palette.Rect;
import jpize.util.math.Maths;

public class ScrollView extends AbstractLayout{

    private final Rect handle;

    private boolean _handleGrabbed;
    private float _handleGrabY;

    private float _maxFactor;
    private float _scrollFactor;
    private float _imaginaryScrollFactor;
    private float _scrollComponentHeight;

    public ScrollView(Constraint width, Constraint height){
        super.size.set(width, height);
        super.input.setClickable(true);

        this.handle = new Rect(Constr.relh(0.03), Constr.relh(1));

        // final Constraint contentWidth = Constr.px(() -> super.cache.containerWidth - handle.cache().width);

        setupHandle();
        super.add(handle);
    }

    public ScrollView(Constraint size){
        this(size, size);
    }

    public ScrollView(){
        this(Constr.win_width, Constr.win_height);
    }


    public UIComponent getScrollComponent(){
        if(children.size() == 2)
            return super.children.get(0);
        return null;
    }

    public Rect getHandle(){
        return handle;
    }


    @Override
    public void add(UIComponent child){
        child.padding().top = Constr.zero;
        child.setOrder(0);
        super.add(child);
    }


    @Override
    public float getPositionForComponent(UIComponent component, boolean forY){
        final UIComponentCache cache = component.cache();
        if(forY){
            if(component == getScrollComponent()){
                _scrollComponentHeight = cache.height + cache.paddingTop * 2 - super.cache.containerHeight;
                return cache.y + Math.max(0, (1 - _scrollFactor) * _scrollComponentHeight);
            }
            return cache.y;
        }
        return cache.x;
    }

    @Override
    public void update(){
        cache.calculate();
    }

    @Override
    public void render(){
        // max scroll bound
        _maxFactor = getMaxScrollFactor();

        // size
        handle.size().y = Constr.relh(_maxFactor);

        // mouse wheel scroll
        final int scroll = Jpize.input().getScroll();
        if(scroll != 0 && (super.input.isHovered() || handle.input().isHovered()))
            _imaginaryScrollFactor += scroll * _maxFactor * 0.3F;

        // handle scroll
        if(_handleGrabbed){
            _imaginaryScrollFactor = (Jpize.getY() - _handleGrabY - super.cache.y) / _scrollComponentHeight / _maxFactor;
            _scrollFactor = _imaginaryScrollFactor;
        }

        // clamp scroll
        _imaginaryScrollFactor = Maths.clamp(_imaginaryScrollFactor, 0, 1);
        final float difference = _imaginaryScrollFactor - _scrollFactor;
        final float scrollSpeedPxSec = 1500;
        final float deltaFactor = scrollSpeedPxSec / _scrollComponentHeight * Jpize.getDt();
        if(Math.abs(difference) > deltaFactor){
            _scrollFactor += Math.signum(difference) * deltaFactor;
            _scrollFactor = Maths.clamp(_scrollFactor, 0, 1);
        }else
            _scrollFactor = _imaginaryScrollFactor;

        // pos
        handle.padding().bottom = Constr.relh(_scrollFactor * (1 - _maxFactor));
    }

    private float getMaxScrollFactor(){
        final float height = super.cache.containerHeight;

        final UIComponent component = getScrollComponent();
        if(component == null)
            return 1;

        final float contentHeight = component.cache().height;
        if(contentHeight <= height)
            return 1;

        return Math.max(0, height / contentHeight);
    }

    private void setupHandle(){
        handle.padding().right = Constr.zero;
        handle.setOrder(Integer.MAX_VALUE);
        handle.style().background().color().set(0.35, 0.1, 0.9);
        handle.style().setCornerRadius(Constr.relw(0.5));
        handle.input().setClickable(true);
        handle.input().addPressCallback((view, btn) -> {
            _handleGrabbed = true;
            _handleGrabY = Jpize.getY() - view.cache().y;
        });
        handle.input().addReleaseCallback((view, btn) -> _handleGrabbed = false);
    }

}
