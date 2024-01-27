package jpize.ui.palette.layout;

import jpize.ui.component.AbstractLayout;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class VBox extends AbstractLayout{

    public VBox(Constraint size){
        super.size.set(size);
    }

    public VBox(Constraint width, Constraint height){
        super.size.set(width, height);
    }

    public VBox(){
        this(Constr.win_width, Constr.win_height);
    }


    private float offsetY;

    @Override
    public float calcPosition(UIComponent component, boolean forY){
        final UIComponentCache cache = component.cache();
        if(!forY)
            return cache.x;

        if(component.cache().hasPaddingBottom)
            return cache.y;

        offsetY -= cache.height;
        final float y = offsetY - cache.paddingTop;
        offsetY -= cache.paddingTop + cache.paddingBottom;
        return y;
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
        offsetY = cache.y + cache.height - cache.marginTop;
    }

}