package jpize.ui.palette;

import jpize.ui.component.AbstractLayout;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class HBox extends AbstractLayout{

    public HBox(Constraint size){
        super.size.set(size);
    }

    public HBox(Constraint width, Constraint height){
        super.size.set(width, height);
    }

    public HBox(){
        this(Constr.win_width, Constr.win_height);
    }


    private float offsetX;

    @Override
    public float calcPosition(UIComponent component, boolean forY){
        final UIComponentCache cache = component.cache();
        if(forY)
            return cache.y;

        final float x = offsetX + cache.paddingLeft;
        offsetX += cache.width + cache.paddingLeft + cache.paddingRight;
        return x;
    }

    @Override
    public float calcWrapContent(UIComponent component, boolean forY, boolean forSize){
        return 0;
    }

    @Override
    public void render(){
        super.render();
        cache.calculate();
        offsetX = cache.x;
        super.renderBackground();
    }

}
