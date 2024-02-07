package jpize.ui.palette.layout;

import jpize.ui.component.AbstractLayout;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class HBox extends AbstractLayout{

    private float offsetX;

    public HBox(Constraint size){
        super.size.set(size);
    }

    public HBox(Constraint width, Constraint height){
        super.size.set(width, height);
    }

    public HBox(){
        this(Constr.win_width, Constr.win_height);
    }

    @Override
    public void update(){
        cache.calculate();
        offsetX = cache.x + cache.marginLeft;
    }

    @Override
    public float getPositionForComponent(UIComponent component, boolean forY){
        final UIComponentCache cache = component.cache();
        if(forY)
            return cache.y;

        if(component.cache().hasPaddingRight)
            return cache.x;

        final float x = offsetX + cache.paddingLeft;
        offsetX += cache.width + cache.paddingLeft + cache.paddingRight;
        return x;
    }

    @Override
    public float getSizeForWrapContent(UIComponent component, boolean forY){
        if(forY)
            return super.cache.containerHeight;

        float remaining = cache.containerWidth - component.cache().paddingLeft;
        int wrapCompNum = 0;

        for(UIComponent child: children){
            if(child.size().x.isFlagWrapContent())
                wrapCompNum++;
            else
                remaining -= child.cache().width + child.cache().paddingLeft;
        }

        if(remaining <= 0)
            return 0;
        return remaining / wrapCompNum;
    }

    @Override
    public float getRemainingAreaForComponent(UIComponent component, boolean forY){
        if(forY)
            return super.cache.containerHeight;

        float remaining = cache.containerHeight;
        for(UIComponent child: children){
            if(child == component)
                break;
            remaining -= child.cache().width + child.cache().paddingLeft;
        }

        return remaining;
    }

}
