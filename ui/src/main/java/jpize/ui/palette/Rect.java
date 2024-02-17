package jpize.ui.palette;

import jpize.ui.component.UIComponent;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class Rect extends UIComponent{

    public Rect(Constraint width, Constraint height){
        super.size.set(width, height);
        super.minSize.set(Constr.px(1));
        super.background().color().set(1);
    }

    public Rect(Constraint size){
        this(size, size);
    }

    @Override
    public void update(){
        cache.calculate();
    }

}
