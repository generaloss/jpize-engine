package jpize.ui.palette.layout;

import jpize.Jpize;
import jpize.sdl.input.Key;
import jpize.ui.component.AbstractLayout;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;
import jpize.ui.palette.Rect;
import jpize.util.math.Maths;

public class ScrollView extends AbstractLayout{

    private float scrollFactor;
    private final Rect handle;

    public ScrollView(Constraint width, Constraint height){
        super.size.set(width, height);
        this.handle = new Rect(Constr.px(10), Constr.relh(1));
        this.handle.padding().right = Constr.px(1);
        super.input.setClickable(true);
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
            if(component == getScrollComponent())
                return cache.y + scrollFactor * (cache.height - super.cache.height);
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
        final int scroll = Jpize.input().getScroll();
        if(scroll != 0 && Jpize.input().isInBounds(cache.x, cache.y, cache.width, cache.height))
            scrollFactor -= scroll / 10F;

        final float maxFactor = getMaxScrollFactor();
        System.out.println(maxFactor);
        if(!Key.S.isPressed())
            scrollFactor = Maths.clamp(scrollFactor, 0, 1);

        handle.size().y        = Constr.relh(maxFactor);
        handle.padding().top   = Constr.relh(scrollFactor * (1 - maxFactor));
        handle.padding().right = Constr.px(1 + (parent != null ? parent.cache().marginRight : 0));
    }

    private float getMaxScrollFactor(){
        final UIComponent component = getScrollComponent();
        if(component == null)
            return 0;

        final UIComponentCache compCache = component.cache();
        final float contentHeight = compCache.height;
        if(contentHeight == 0)
            return 0;

        return Math.max(0, super.cache.height / contentHeight);
    }

    public UIComponent getScrollComponent(){
        return super.children.get(1);
    }

    public Rect getHandle(){
        return handle;
    }

}
