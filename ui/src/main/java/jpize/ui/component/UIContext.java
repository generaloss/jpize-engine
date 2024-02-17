package jpize.ui.component;

import jpize.Jpize;
import jpize.app.Disposable;
import jpize.sdl.event.callback.mouse.MouseButtonAction;
import jpize.sdl.event.callback.mouse.MouseButtonCallback;
import jpize.sdl.event.callback.window.WinSizeChangedCallback;
import jpize.sdl.input.Btn;
import jpize.util.math.Intersector;

public class UIContext implements Disposable{

    private UIComponent root;
    private UIComponent focused;
    private UIComponent pressed;
    private final UIRenderer renderer;
    private final MouseButtonCallback mouseButtonCallback;
    private final WinSizeChangedCallback winResizeCallback;
    private volatile boolean enableTouchDelayed;

    public UIContext(UIComponent root){
        this();
        setRoot(root);
    }

    public UIContext(){
        this.renderer = new UIRenderer();
        this.mouseButtonCallback = this::buttonCallback;
        this.winResizeCallback = (window, width, height) -> renderer.resize(width, height);
    }


    public void enable(){
        Jpize.context().callbacks().addWinSizeChangedCallback(winResizeCallback);
        winResizeCallback.invoke(null, Jpize.getWidth(), Jpize.getHeight());
        enableTouchDelayed = true;
    }

    public void disable(){
        Jpize.context().callbacks().removeMouseButtonCallback(mouseButtonCallback);
        Jpize.context().callbacks().removeWinSizeChangedCallback(winResizeCallback);
        enableTouchDelayed = false;
    }


    public UIRenderer renderer(){
        return renderer;
    }


    public UIComponent getRoot(){
        return root;
    }

    public void setRoot(UIComponent root){
        this.root = root;
        setThisContext(root);
    }


    public UIComponent getHovered(){
        return getHovered(root, Jpize.getX(), Jpize.getY());
    }

    public boolean isHovered(UIComponent component){
        return component == getHovered();
    }


    public UIComponent getFocused(){
        return focused;
    }

    public boolean isFocused(UIComponent component){
        return component == getFocused();
    }

    public void setFocused(UIComponent component){
        if(focused == component) return;
        if(focused != null) focused.invokeFocusCallbacks(false);
        focused = component;
        if(focused != null) focused.invokeFocusCallbacks(true);
    }


    public UIComponent getPressed(){
        return pressed;
    }

    public boolean isPressed(UIComponent component){
        return component == getPressed();
    }


    public void render(){
        renderer.begin();
        render(root);
        renderer.end();

        if(enableTouchDelayed){
            Jpize.context().callbacks().addMouseButtonCallback(mouseButtonCallback);
            enableTouchDelayed = false;
        }
    }

    @Override
    public void dispose(){
        disable();
        dispose(root);
        renderer.dispose();
    }

    // Aliases

    public <T extends UIComponent> T getByID(String ID){
        return root.getByID(ID);
    }

    public <T extends UIComponent> T findByID(String ID){
        return root.findByID(ID);
    }


    public void setCornerSoftness(float softness){
        renderer.setCornerSoftness(softness);
    }

    public void setBorderSoftness(float softness){
        renderer.setBorderSoftness(softness);
    }

    // Private

    private void render(UIComponent component){
        if(component == null || component.isHidden())
            return;

        component.cache().updateParent();
        component.update();
        component.renderBackground();

        renderer.beginScissor(component);
        component.render();
        for(UIComponent child: component.children())
            render(child);
        renderer.endScissor(component);
    }

    private void dispose(UIComponent component){
        for(UIComponent child: component.children()){
            child.invokeFocusCallbacks(false);
            dispose(child);
        }
    }


    private UIComponent getHovered(UIComponent component, int x, int y){
        final UIComponentCache cache = component.cache();
        if(!Intersector.isPointOnRect(x, y, cache.x, cache.y, cache.width, cache.height))
            return null;

        for(UIComponent child: component.children()){
            final UIComponent hovered = getHovered(child, x, y);
            if(hovered != null)
                return hovered;
        }

        if(component.isClickable())
            return component;
        return null;
    }


    private void setThisContext(UIComponent component){
        component.setContext(this);
        for(UIComponent child: component.children())
            setThisContext(child);
    }

    private void buttonCallback(Btn button, MouseButtonAction action){
        if(button == Btn.X1 || button == Btn.X2)
            return;

        if(action == MouseButtonAction.DOWN){
            final UIComponent hovered = getHovered();
            setFocused(hovered);

            if(pressed != null || hovered == null)
                return;
            pressed = hovered;
            pressed.invokePressCallbacks(button);
        }else{
            if(pressed == null)
                return;
            pressed.invokeReleaseCallbacks(button);
            pressed = null;
        }
    }

}
