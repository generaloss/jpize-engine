package jpize.ui.palette;

import jpize.ui.component.LayoutComponent;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.constraint.Constraint;

public class HBox extends LayoutComponent{

    public HBox(Constraint size){
        super.minSize().set(size);
    }

    public HBox(Constraint width, Constraint height){
        super.minSize().set(width, height);
    }


    private float offsetH;

    private void update(UIComponent component){
        for(UIComponent child: component.children()){
            child.update();
            update(child);
        }
    }

    @Override
    public float calcPosition(UIComponent component, boolean forY){
        final UIComponentCache cache = component.cache();
        if(forY)
            return cache.y;

        final float x = offsetH + cache.paddingLeft;
        offsetH += cache.width + cache.paddingLeft + cache.paddingRight;
        return x;
    }

    @Override
    public float calcWrapContent(UIComponent component, boolean forY, boolean forSize){
        return 0;
    }

    @Override
    public void render(){
        offsetH = 0;
        cache().calculate();

        super.renderBackground();
        render(this);
    }

    private void render(UIComponent component){
        for(UIComponent child: component.children()){
            child.render();
            render(child);
        }
    }

}
