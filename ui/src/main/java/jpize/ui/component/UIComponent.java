package jpize.ui.component;

import jpize.graphics.texture.Texture;
import jpize.ui.context.UIContext;
import jpize.util.color.Color;
import jpize.ui.component.input.UIInput;
import jpize.ui.component.render.UIRenderer;
import jpize.ui.component.style.UIBackground;
import jpize.ui.component.style.UIStyle;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Dimension;
import jpize.ui.constraint.Insets;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class UIComponent{

    protected UIContext context;
    protected final UIComponentCache cache;
    protected final List<UIComponent> children;
    protected UIComponent parent;
    protected String ID;
    protected int order;

    protected final Insets margin, padding;
    protected final Dimension size, minSize, maxSize;
    protected boolean paddingFixH, paddingFixW;
    protected final UIStyle style;
    protected final UIInput input;
    protected boolean hidden;

    public UIComponent(){
        this.children = new CopyOnWriteArrayList<>();
        this.ID = "null";
        this.order = -1;
        this.cache = new UIComponentCache(this);
        this.margin = new Insets();
        this.padding = new Insets();
        this.size = new Dimension();
        this.minSize = new Dimension(Constr.zero);
        this.maxSize = new Dimension();
        this.style = new UIStyle();
        this.input = new UIInput(this);
    }


    public void update(){ }

    public void render(){ }

    public void onFocus(){ }

    public void onUnfocus(){ }


    public void renderBackground(){
        final UIRenderer renderer = context.renderer();
        renderer.beginRect(cache.x, cache.y, cache.width, cache.height, cache.cornerRadius, cache.borderSize, style.borderColor());
        final UIBackground background = style.background();
        final Color color = background.color();
        final Texture image = background.getImage();
        renderer.batch().draw(image, cache.x, cache.y, cache.width, cache.height, color.r(), color.g(), color.b(), color.a());
        renderer.endRect();
    }


    public final UIComponent parent(){
        return parent;
    }

    public final void setParent(UIComponent parent){
        this.parent = parent;
        if(parent == null) return;
        context = parent.context;

        // Recursive set parent for children
        for(UIComponent child: children)
            child.setParent(this);
    }


    private void sortChildren(){
        children.sort(Comparator.comparingInt(c -> c.order));
    }

    public final List<UIComponent> children(){
        return children;
    }

    protected final UIComponent getChildWithID(String ID){
        for(UIComponent child: children)
            if(ID.equals(child.ID))
                return child;

        throw new RuntimeException("Component with ID " + ID + " not found");
    }

    @SuppressWarnings("unchecked")
    public final <C extends UIComponent> C getByID(String ID){
        if(ID.contains(".")){
            final String[] links = ID.split("\\.");

            C component = (C) this;
            for(String link: links)
                component = (C) component.getChildWithID(link);
            return component;
        }
        return (C) getChildWithID(ID);
    }

    @SuppressWarnings("unchecked")
    public final <C extends UIComponent> C findByID(String ID){
        for(UIComponent child: children){
            if(ID.equals(child.ID))
                return (C) child;

            final UIComponent component = child.findByID(ID);
            if(component != null)
                return (C) component;
        }
        return null;
    }

    public void add(UIComponent child){
        child.setParent(this);
        children.add(child);
        sortChildren();
    }

    public void remove(UIComponent child){
        children.remove(child);
    }

    public void remove(String ID){
        children.remove(findByID(ID));
    }


    public final String getID(){
        return ID;
    }

    public final void setID(String ID){
        this.ID = ID;
    }


    public final int getOrder(){
        return order;
    }

    public final void setOrder(int order){
        this.order = order;
        if(parent != null)
            parent.sortChildren();
    }


    public final UIContext context(){
        return context;
    }

    public final void setContext(UIContext context){
        this.context = context;
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


    public final UIStyle style(){
        return style;
    }


    public final boolean isHidden(){
        return hidden;
    }

    public final void setHidden(boolean hidden){
        this.hidden = hidden;
    }

}
