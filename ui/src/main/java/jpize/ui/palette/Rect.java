package jpize.ui.palette;

import jpize.graphics.util.batch.TextureBatch;
import jpize.graphics.util.color.Color;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Constraint;

public class Rect extends UIComponent{

    public Rect(){
        super.minSize().set(Constr.px(100));
        super.background().set(1, 1, 1, 1);
    }

    public Rect(Constraint size){
        this();
        super.size().set(size);
    }

    public Rect(Constraint width, Constraint height){
        super.minSize().set(width, height);
    }

    @Override
    public void render(){
        cache().calculate();

        renderContext.rect(cache.x - cache.paddingLeft, cache.y - cache.paddingBottom, cache.width + cache.paddingLeft + cache.paddingRight, cache.height + cache.paddingBottom + cache.paddingTop, 0);
        renderContext.batch().drawRect(
                background.r() * 0.4, background.g() * 0.4, background().b() * 0.4, 0.2,
                cache.x - cache.paddingLeft, cache.y - cache.paddingBottom, cache.width + cache.paddingLeft + cache.paddingRight, cache.height + cache.paddingBottom + cache.paddingTop);
        renderContext.batch().end();

        super.renderBackground();
    }

}
