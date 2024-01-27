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

    private float maxFactor;
    private float scrollFactor;
    private float imaginaryScrollFactor;
    private float scrollComponentHeight;

    private final Rect handle;
    private boolean handleGrabbed;
    private float handleGrabY;

    public ScrollView(Constraint width, Constraint height){
        super.size.set(width, height);
        super.input.setClickable(true);
        // handle
        this.handle = new Rect(Constr.px(10), Constr.relh(1));
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
        return super.children.get(1);
    }

    public Rect getHandle(){
        return handle;
    }


    @Override
    public float calcPosition(UIComponent component, boolean forY){
        final UIComponentCache cache = component.cache();
        if(forY){
            if(component == getScrollComponent()){
                scrollComponentHeight = cache.height + cache.paddingTop * 2 + super.cache.marginBottom - super.cache.height;
                return cache.y + (1 - scrollFactor) * scrollComponentHeight;
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
        cache.calculate();
    }

    @Override
    public void render(){
        processScroll();
    }

    private void processScroll(){
        // max scroll bound
        maxFactor = getMaxScrollFactor();

        // size
        handle.size().y         = Constr.relh(maxFactor);
        handle.padding().right  = Constr.px(1 + handle.cache().width + (parent != null ? parent.cache().marginRight : 0));

        // mouse wheel scroll
        final int scroll = Jpize.input().getScroll();
        System.out.println(super.input.isHovered() + " : " + context.getHoveredComponent());
        if(scroll != 0 && (super.input.isHovered() || handle.input().isHovered()))
            imaginaryScrollFactor += scroll * maxFactor * 0.3F;

        // handle scroll
        if(handleGrabbed){
            imaginaryScrollFactor = (Jpize.getY() - handleGrabY - super.cache.y) / scrollComponentHeight / maxFactor;
            scrollFactor = imaginaryScrollFactor;
        }

        // clamp scroll
        imaginaryScrollFactor = Maths.clamp(imaginaryScrollFactor, 0, 1);
        final float difference = imaginaryScrollFactor - scrollFactor;
        final float scrollSpeedPxSec =  1500;
        final float deltaFactor = scrollSpeedPxSec / scrollComponentHeight * Jpize.getDt();
        if(Math.abs(difference) > deltaFactor){
            scrollFactor += Math.signum(difference) * deltaFactor;
            scrollFactor = Maths.clamp(scrollFactor, 0, 1);
        }else
            scrollFactor = imaginaryScrollFactor;

        // pos
        handle.padding().bottom = Constr.relh(scrollFactor * (1 - maxFactor));
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

    private void setupHandle(){
        handle.padding().right = Constr.px(1);
        handle.input().setClickable(true);
        handle.input().addPressCallback((view, btn) -> {
            handleGrabbed = true;
            handleGrabY = Jpize.getY() - view.cache().y;
        });
        handle.input().addReleaseCallback((view, btn) -> handleGrabbed = false);
    }

}
