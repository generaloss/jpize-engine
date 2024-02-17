package jpize.ui.component;

import jpize.graphics.texture.Texture;
import jpize.sdl.input.Btn;
import jpize.ui.constraint.Constraint;
import jpize.util.color.Color;
import jpize.ui.constraint.Constr;
import jpize.ui.constraint.Dimension;
import jpize.ui.constraint.Insets;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class UIComponent{

    // general
    protected UIContext context;
    protected UIComponent parent;
    protected final List<UIComponent> children;
    protected final UIComponentCache cache;
    // properties
    protected String ID;
    protected int order;
    protected final Insets margin, padding;
    protected final Dimension size, minSize, maxSize;
    protected boolean hidden;
    // style
    private final UIBackground background;
    private final Color border_color;
    private Constraint border_size;
    private Constraint corner_radius;
    // input
    private boolean clickable;
    private final List<UIPressCallback> pressCallbacks;
    private final List<UIReleaseCallback> releaseCallbacks;
    private final List<UIFocusCallback> focusCallbacks;

    public UIComponent(){
        this.children = new CopyOnWriteArrayList<>();
        this.order = -1;
        this.cache = new UIComponentCache(this);
        this.margin = new Insets();
        this.padding = new Insets();
        this.size = new Dimension();
        this.minSize = new Dimension(Constr.zero);
        this.maxSize = new Dimension();
        this.background = new UIBackground();
        this.border_color = new Color();
        this.pressCallbacks = new CopyOnWriteArrayList<>();
        this.releaseCallbacks = new CopyOnWriteArrayList<>();
        this.focusCallbacks = new CopyOnWriteArrayList<>();
    }

    // Override

    public void update(){ }

    public void render(){ }

    // Protected

    protected void renderBackground(){
        final UIRenderer renderer = context.renderer();
        renderer.beginRect(cache.x, cache.y, cache.width, cache.height, cache.cornerRadius, cache.borderSize, border_color);
        final Color color = background.color();
        final Texture image = background.getImage();
        renderer.batch().draw(image, cache.x, cache.y, cache.width, cache.height, color.r(), color.g(), color.b(), color.a());
        renderer.endRect();
    }

    private void sortChildren(){
        children.sort(Comparator.comparingInt(c -> c.order));
    }

    protected UIComponent getChildWithID(String ID){
        for(UIComponent child: children)
            if(ID.equals(child.ID))
                return child;

        throw new RuntimeException("Component with ID " + ID + " not found");
    }

    // Children (Add / Remove / Get)

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


    @SuppressWarnings("unchecked")
    public <C extends UIComponent> C getByOrder(int order){
        for(UIComponent child: children)
            if(order == child.order)
                return (C) child;
        return null;
    }

    @SuppressWarnings("unchecked")
    public <C extends UIComponent> C getByID(String ID){
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
    public <C extends UIComponent> C findByID(String ID){
        for(UIComponent child: children){
            if(ID.equals(child.ID))
                return (C) child;

            final UIComponent component = child.findByID(ID);
            if(component != null)
                return (C) component;
        }
        return null;
    }

    // General

    public UIContext context(){
        return context;
    }

    public void setContext(UIContext context){
        this.context = context;
    }


    public UIComponent parent(){
        return parent;
    }

    public void setParent(UIComponent parent){
        this.parent = parent;
        if(parent == null) return;
        context = parent.context;

        // Recursive set parent for children
        for(UIComponent child: children)
            child.setParent(this);
    }


    public List<UIComponent> children(){
        return children;
    }

    public UIComponentCache cache(){
        return cache;
    }

    // Properties

    public String getID(){
        return ID;
    }

    public void setID(String ID){
        this.ID = ID;
    }


    public int getOrder(){
        return order;
    }

    public void setOrder(int order){
        this.order = order;
        if(parent != null)
            parent.sortChildren();
    }


    public Insets margin(){
        return margin;
    }

    public Insets padding(){
        return padding;
    }

    public Dimension size(){
        return size;
    }

    public Dimension minSize(){
        return minSize;
    }

    public Dimension maxSize(){
        return maxSize;
    }


    public boolean isHidden(){
        return hidden;
    }

    public void setHidden(boolean hidden){
        this.hidden = hidden;
    }

    // Style

    public UIBackground background(){
        return background;
    }


    public Constraint getCornerRadius(){
        return corner_radius;
    }

    public void setCornerRadius(Constraint corner_radius){
        this.corner_radius = corner_radius;
    }


    public Constraint getBorderSize(){
        return border_size;
    }

    public void setBorderSize(Constraint border_size){
        this.border_size = border_size;
    }


    public Color borderColor(){
        return border_color;
    }

    // Input

    public boolean isClickable(){
        return clickable;
    }

    public void setClickable(boolean clickable){
        this.clickable = clickable;
    }


    public boolean isHovered(){
        return context.isHovered(this);
    }

    public boolean isPressed(){
        return context.isPressed(this);
    }

    public boolean isFocused(){
        return context.isFocused(this);
    }

    public void focus(){
        context.setFocused(this);
    }


    public void addPressCallback(UIPressCallback callback){
        pressCallbacks.add(callback);
    }

    public void removePressCallback(UIPressCallback callback){
        pressCallbacks.remove(callback);
    }

    protected void invokePressCallbacks(Btn button){
        for(UIPressCallback callback: pressCallbacks)
            callback.invoke(this, button);
    }


    public void addReleaseCallback(UIReleaseCallback callback){
        releaseCallbacks.add(callback);
    }

    public void removeReleaseCallback(UIReleaseCallback callback){
        releaseCallbacks.remove(callback);
    }

    protected void invokeReleaseCallbacks(Btn button){
        for(UIReleaseCallback callback: releaseCallbacks)
            callback.invoke(this, button);
    }


    public void addFocusCallback(UIFocusCallback callback){
        focusCallbacks.add(callback);
    }

    public void removeFocusCallback(UIFocusCallback callback){
        focusCallbacks.remove(callback);
    }

    protected void invokeFocusCallbacks(boolean focus){
        for(UIFocusCallback callback: focusCallbacks)
            callback.invoke(this, focus);
    }

}
