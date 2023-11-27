package jpize.ui.component;

import jpize.graphics.util.batch.TextureBatch;
import jpize.graphics.util.color.Color;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Dimension;
import jpize.ui.gravity.Gravity;
import jpize.ui.constraint.Insets;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class UIComponent{

    protected @Nullable UIComponent parent;
    protected final List<UIComponent> children;
    protected String ID;
    protected UIRenderContext renderContext;
    protected final UIComponentCache cache;
    protected final Insets margin, padding;
    protected final Dimension size, sizeMin, sizeMax;
    protected boolean paddingFixH, paddingFixW;
    protected final Gravity gravity;
    protected final Color background;
    protected float cornerRadius;

    public UIComponent(){
        this.children = new ArrayList<>();
        this.ID = null;
        this.cache = new UIComponentCache(this);
        this.margin = new Insets();
        this.padding = new Insets();
        this.size = new Dimension();
        this.sizeMin = new Dimension(Constr.zero);
        this.sizeMax = new Dimension();
        this.gravity = new Gravity();
        this.background = new Color(1, 1, 1, 0);
    }


    public void update(){ }

    public void render(){ }


    protected void renderBackground(){
        renderContext.rect(cache.x, cache.y, cache.width, cache.height, cornerRadius);
        renderContext.batch().drawRect(
                background.r(), background.g(), background.b(), background.a(),
                cache.x, cache.y, cache.width, cache.height);
        renderContext.batch().end();
    }


    public final UIComponent parent(){
        return parent;
    }

    public final void setParent(@Nullable UIComponent parent){
        if(parent != null)
            renderContext = parent.renderContext;
        this.parent = parent;
    }


    public final Collection<UIComponent> children(){
        return children;
    }

    public final UIComponent findByID(String ID){
        for(UIComponent child: children)
            if(child.ID.equals(ID))
                return child;
        return null;
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


    public final String ID(){
        return ID;
    }

    public final void setID(String ID){
        this.ID = ID;
    }


    public final UIRenderContext renderContext(){
        return renderContext;
    }

    public final void setRenderContext(UIRenderContext renderContext){
        this.renderContext = renderContext;
    }


    public final UIComponentCache cache(){
        return cache;
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
        return sizeMin;
    }

    public final Dimension maxSize(){
        return sizeMax;
    }


    public final Gravity gravity(){
        return gravity;
    }


    public final Color background(){
        return background;
    }

    public final float cornerRadius(){
        return cornerRadius;
    }

    public final void setCornerRadius(float cornerRadius){
        this.cornerRadius = cornerRadius;
    }

}
