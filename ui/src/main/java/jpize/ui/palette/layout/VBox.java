package jpize.ui.palette.layout;

import jpize.ui.component.AbstractLayout;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class VBox extends AbstractLayout{

    private float offsetY;

    public VBox(Constraint size){
        super.size.set(size);
    }

    public VBox(Constraint width, Constraint height){
        super.size.set(width, height);
    }

    public VBox(){
        this(Constr.win_width, Constr.win_height);
    }


    @Override
    public void update(){
        cache.calculate();
        offsetY = cache.y + cache.marginBottom + cache.containerHeight;
    }

    @Override
    public float getPositionForComponent(UIComponent component, boolean forY){
        final UIComponentCache cache = component.cache();
        if(!forY)
            return cache.x;

        final boolean hasPaddingTopBottom = component.cache().hasPaddingTopBottom;
        if(component.cache().hasPaddingBottom && !hasPaddingTopBottom)
            return cache.y;

        offsetY -= cache.height;
        final float y = offsetY - cache.paddingTop;
        offsetY -= cache.paddingTop;
        return y;
    }

    @Override
    public float getSizeForWrapContent(UIComponent component, boolean forY){
        if(!forY)
            return super.cache.containerWidth;

        float remaining = cache.containerHeight - component.cache().paddingTop;
        int wrapCompNum = 0;

        for(UIComponent child: children){
            if(child.size().y.isFlagWrapContent())
                wrapCompNum++;
            else
                remaining -= child.cache().height + child.cache().paddingTop;
        }

        if(remaining <= 0)
            return 0;
        return remaining / wrapCompNum;
    }

    @Override
    public float getRemainingAreaForComponent(UIComponent component, boolean forY){
        if(!forY)
            return super.cache.containerWidth;

        float remaining = cache.containerHeight;
        for(UIComponent child: children){
            if(child == component)
                break;
            remaining -= child.cache().height + child.cache().paddingTop;
        }

        return remaining;
    }

}