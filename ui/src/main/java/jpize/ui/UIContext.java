package jpize.ui;

import jpize.Jpize;
import jpize.app.Disposable;
import jpize.sdl.event.callback.mouse.MouseButtonAction;
import jpize.sdl.event.callback.mouse.MouseButtonCallback;
import jpize.sdl.event.callback.window.WinSizeChangedCallback;
import jpize.sdl.input.Btn;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.component.UIRenderer;
import jpize.util.math.Intersector;


public class UIContext implements Disposable{

    private final UIRenderer renderer;
    private UIComponent root;
    private UIComponent focused;
    private UIComponent pressed;
    private final MouseButtonCallback mouseButtonCallback;
    private final WinSizeChangedCallback winResizeCallback;
    private volatile boolean enableTouchingDelayed;

    public UIContext(){
        this.renderer = new UIRenderer();

        this.mouseButtonCallback = ((button, action) -> {
            if(button == Btn.X1 || button == Btn.X2)
                return;

            if(action == MouseButtonAction.UP){
                if(pressed != null){
                    pressed.input().invokeReleaseCallbacks(button);
                    pressed = null;
                }
                return;
            }

            final UIComponent hovered = getHovered();

            final UIComponent oldFocused = focused;
            if(oldFocused != null && oldFocused != hovered)
                oldFocused.onUnfocus();

            focused = hovered;
            if(hovered == null)
                return;

            if(focused != oldFocused)
                focused.onFocus();

            if(pressed != null)
                return;

            pressed = hovered;
            pressed.input().invokePressCallbacks(button);
        });

        this.winResizeCallback = (window, width, height) -> renderer.resize(width, height);
    }

    public UIContext(UIComponent root){
        this();
        setRoot(root);
    }


    public void setCornerSoftness(float softness){
        renderer.setCornerSoftness(softness);
    }

    public void setBorderSoftness(float softness){
        renderer.setBorderSoftness(softness);
    }


    public void enable(){
        Jpize.context().callbacks().addWinSizeChangedCallback(winResizeCallback);
        winResizeCallback.invoke(null, Jpize.getWidth(), Jpize.getHeight());
        enableTouchingDelayed = true;
    }

    public void disable(){
        Jpize.context().callbacks().removeMouseButtonCallback(mouseButtonCallback);
        Jpize.context().callbacks().removeWinSizeChangedCallback(winResizeCallback);
        enableTouchingDelayed = false;
    }


    public UIRenderer renderer(){
        return renderer;
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

        if(component.input().isClickable())
            return component;
        return null;
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


    public UIComponent getPressed(){
        return pressed;
    }

    public boolean isPressed(UIComponent component){
        return component == getPressed();
    }


    public UIComponent getRoot(){
        return root;
    }

    public void setRoot(UIComponent root){
        this.root = root;
        setThisContext(root);
    }

    private void setThisContext(UIComponent component){
        component.setContext(this);
        for(UIComponent child: component.children())
            setThisContext(child);
    }

    public <T extends UIComponent> T getByID(String ID){
        return root.getByID(ID);
    }

    public <T extends UIComponent> T findByID(String ID){
        return root.findByID(ID);
    }


    public void render(){
        renderer.begin();
        render(root);
        renderer.end();

        if(enableTouchingDelayed){
            Jpize.context().callbacks().addMouseButtonCallback(mouseButtonCallback);
            enableTouchingDelayed = false;
        }
    }

    private void render(UIComponent component){
        if(component == null || component.isHidden())
            return;

        component.cache().updateParent();
        component.update();

        component.renderBackground();
        component.render();

        renderer.beginScissor(component);
        for(UIComponent child: component.children())
            render(child);
        renderer.endScissor(component);
    }


    private void dispose(UIComponent component){
        for(UIComponent child: component.children()){
            child.onUnfocus();
            dispose(child);
        }
    }

    @Override
    public void dispose(){
        disable();
        dispose(root);
        renderer.dispose();
    }

}
