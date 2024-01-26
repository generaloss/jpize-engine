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
    private float scrollFactor;
    private float imaginaryScrollFactor;
    private float scrollComponentHeight;
    private float scrollSpeedPxSec;

    public ScrollView(Constraint width, Constraint height){
        super.size.set(width, height);
        this.handle = new Rect(Constr.px(10), Constr.relh(1));
        this.handle.padding().right = Constr.px(1);
        this.handle.input().setClickable(true);
        super.add(handle);
    }

    public ScrollView(Constraint size){
        this(size, size);
    }

    public ScrollView(){
        this(Constr.win_width, Constr.win_height);
    }


    @Override
    public float calcPosition(UIComponent component, boolean forY){
        final UIComponentCache cache = component.cache();
        if(forY){
            if(component == getScrollComponent()){
                scrollComponentHeight = cache.height + cache.paddingTop * 2 + super.cache.marginBottom - super.cache.height;
                return cache.y + scrollFactor * scrollComponentHeight;
            }
            return cache.y;
        }
        return cache.x;
    }

    @Override
    public float calcWrapContent(UIComponent component, boolean forY, boolean forSize){
        return 0;
    }

    @Override
    public void update(){
        super.cache.calculate();
        System.out.println(input.isHovered() + " " + context.getHoveredComponent());

        // scroll
        final float maxFactor = getMaxScrollFactor();
        final int scroll = Jpize.input().getScroll();
        if(scroll != 0 && Jpize.input().isInBounds(cache.x, cache.y, cache.width, cache.height))
            imaginaryScrollFactor -= scroll * maxFactor * 0.3F;

        // clamp scroll
        imaginaryScrollFactor = Maths.clamp(imaginaryScrollFactor, 0, 1);
        final float difference = imaginaryScrollFactor - scrollFactor;
        scrollSpeedPxSec =  1000;
        final float deltaFactor = scrollSpeedPxSec / scrollComponentHeight * Jpize.getDt();
        if(Math.abs(difference) > deltaFactor){
            scrollFactor += Math.signum(difference) * deltaFactor;
            scrollFactor = Maths.clamp(scrollFactor, 0, 1);
        }else
            scrollFactor = imaginaryScrollFactor;

        // pos & size
        handle.size().y        = Constr.relh(maxFactor);
        handle.padding().top   = Constr.relh(scrollFactor * (1 - maxFactor));
        handle.padding().right = Constr.px(1 + handle.cache().width + (parent != null ? parent.cache().marginRight : 0));
    }

    private float getMaxScrollFactor(){
        final UIComponent component = getScrollComponent();
        if(component == null)
            return 0;

        final UIComponentCache compCache = component.cache();
        final float contentHeight = compCache.height;
        if(contentHeight == 0)
            return 0;

        final float height = super.cache.height;
        return Math.max(0, height / contentHeight);
    }

    public UIComponent getScrollComponent(){
        return super.children.get(1);
    }

    public Rect getHandle(){
        return handle;
    }

}
