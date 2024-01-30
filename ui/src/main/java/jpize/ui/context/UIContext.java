package jpize.ui.context;

import jpize.Jpize;
import jpize.app.Disposable;
import jpize.sdl.event.callback.mouse.MouseButtonAction;
import jpize.sdl.event.callback.mouse.MouseButtonCallback;
import jpize.sdl.event.callback.window.WinSizeChangedCallback;
import jpize.sdl.input.Btn;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.component.render.UIRenderer;



public class UIContext implements Disposable{

    private final UIRenderer renderer;
    private UIComponent root;
    private UIComponent focused;
    private final MouseButtonCallback mouseButtonCallback;
    private final WinSizeChangedCallback winResizeCallback;
    private volatile boolean enableTouchingDelayed;

    public UIContext(){
        this.renderer = new UIRenderer();

        this.mouseButtonCallback = ((button, action) -> {
            if(button == Btn.X1 || button == Btn.X2)
                return;

            if(action == MouseButtonAction.UP){
                release(root, button, action);
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

            hovered.cache().press();
            hovered.input().invokePressCallbacks(button);
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


    public UIComponent getHovered(){
        return renderer.stencil().get(Jpize.getX(), Jpize.getY());
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


    private void release(UIComponent component, Btn button, MouseButtonAction action){
        if(component.input().isClickable() && component.cache().release())
            component.input().invokeReleaseCallbacks(button);

        for(UIComponent child: component.children())
            release(child, button, action);
    }


    public UIComponent getRoot(){
        return root;
    }

    public void setRoot(UIComponent root){
        this.root = root;
        this.root.setContext(this);
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

        component.update();

        if(component.input().isClickable()){
            final UIComponentCache cache = component.cache();
            renderer.stencil().fill(cache.x, cache.y, cache.width, cache.height, component);
        }

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
