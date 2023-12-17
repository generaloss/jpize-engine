package jpize.ui.palette;

import jpize.ui.component.AbstractLayout;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class ConstraintLayout extends AbstractLayout{

    public ConstraintLayout(Constraint size){
        super.minSize.set(size);
    }

    public ConstraintLayout(Constraint width, Constraint height){
        super.minSize.set(width, height);
    }

    public ConstraintLayout(){
        this(Constr.win_width, Constr.win_height);
    }


    @Override
    public float calcPosition(UIComponent component, boolean forY){
        final UIComponentCache cache = component.cache();
        if(forY)
            return cache.y;
        return cache.x;
    }

    @Override
    public float calcWrapContent(UIComponent component, boolean forY, boolean forSize){
        return 0;
    }

    @Override
    public void render(){
        super.render();
        cache.calculate();
        super.renderBackground();
    }

}