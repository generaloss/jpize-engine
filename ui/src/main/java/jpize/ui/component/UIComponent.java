package jpize.ui.component;

import jpize.graphics.texture.Texture;
import jpize.graphics.util.color.Color;
import jpize.ui.component.input.UIInput;
import jpize.ui.component.render.UIRenderer;
import jpize.ui.component.style.UIBackground;
import jpize.ui.component.style.UIStyle;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Dimension;
import jpize.ui.gravity.Gravity;
import jpize.ui.constraint.Insets;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class UIComponent{

    protected @Nullable UIComponent parent;
    protected final List<UIComponent> children;
    protected String ID;
    protected UIRenderer renderer;
    protected final UIComponentCache cache;
    protected final Insets margin, padding;
    protected final Dimension size, minSize, maxSize;
    protected boolean paddingFixH, paddingFixW;
    protected final Gravity gravity;
    protected final UIStyle style;
    protected final UIInput input;

    public UIComponent(){
        this.children = new ArrayList<>();
        this.ID = null;
        this.cache = new UIComponentCache(this);
        this.margin = new Insets();
        this.padding = new Insets();
        this.size = new Dimension();
        this.minSize = new Dimension(Constr.zero);
        this.maxSize = new Dimension();
        this.gravity = new Gravity();
        this.style = new UIStyle();
        this.input = new UIInput(this);
    }


    public void update(){ }

    public void render(){
        findRenderer();
    }

    private void findRenderer(){
        if(renderer != null)
            return;

        UIComponent parent = this.parent;
        while(true){
            if(parent == null)
                return;
            if(parent.renderer == null)
                parent = parent.parent;
            else
                break;
        }
        renderer = parent.renderer;
    }


    protected void renderBackground(){
        renderer.rect(cache.x, cache.y, cache.width, cache.height, cache.cornerRadius, cache.borderSize, style.borderColor());
        final UIBackground background = style.background();
        final Color color = background.color();
        final Texture image = background.getImage();
        renderer.batch().draw(image, cache.x, cache.y, cache.width, cache.height, color.r(), color.g(), color.b(), color.a());
        renderer.batch().end();
    }


    public final UIComponent parent(){
        return parent;
    }

    public final void setParent(@Nullable UIComponent parent){
        if(parent != null)
            renderer = parent.renderer;
        this.parent = parent;
    }


    public final List<UIComponent> children(){
        return children;
    }

    @SuppressWarnings("unchecked")
    public final <T extends UIComponent> T findByID(String ID){
        for(UIComponent child: children)
            if(child.ID.equals(ID))
                return (T) child;
        throw new RuntimeException("Component with ID " + ID + " not found");
    }

    public final void add(UIComponent child){
        child.setParent(this);
        children.add(child);
    }

    public final void remove(UIComponent child){
        children.remove(child);
    }

    public final void remove(String childID){
        children.remove(findByID(childID));
    }


    public final String getID(){
        return ID;
    }

    public final void setID(String ID){
        this.ID = ID;
    }


    public final UIRenderer renderContext(){
        return renderer;
    }

    public final void setRenderer(UIRenderer renderer){
        this.renderer = renderer;
    }


    public final UIComponentCache cache(){
        return cache;
    }

    public final UIInput input(){
        return input;
    }


    public final Insets margin(){
        return margin;
    }

    public final Insets padding(){
        return padding;
    }

    public final Dimension size(){
        return size;
    }

    public final Dimension minSize(){
        return minSize;
    }

    public final Dimension maxSize(){
        return maxSize;
    }


    public final Gravity gravity(){
        return gravity;
    }


    public final UIStyle style(){
        return style;
    }

}
