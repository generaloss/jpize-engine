package jpize.ui.palette.layout;

import jpize.ui.component.AbstractLayout;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class ConstraintLayout extends AbstractLayout{

    public ConstraintLayout(Constraint size){
        super.size.set(size);
    }

    public ConstraintLayout(Constraint width, Constraint height){
        super.size.set(width, height);
    }

    public ConstraintLayout(){
        this(Constr.win_width, Constr.win_height);
    }


    @Override
    public float getPositionForComponent(UIComponent component, boolean forY){
        final UIComponentCache cache = component.cache();
        return forY ? cache.y : cache.x;
    }

    @Override
    public float getSizeForWrapContent(UIComponent component, boolean forY){
        return forY ? super.cache.height : super.cache.width;
    }

    public float getRemainingAreaForComponent(UIComponent component, boolean forY){
        return forY ? super.cache.containerHeight : super.cache.containerWidth;
    }

    @Override
    public void update(){
        cache.calculate();
    }

}