package jpize.ui.context;

import jpize.Jpize;
import jpize.sdl.event.mouse.MouseButtonAction;
import jpize.sdl.event.mouse.MouseButtonCallback;
import jpize.sdl.event.window.WinResizedCallback;
import jpize.sdl.input.Btn;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.component.render.UIRenderer;
import jpize.util.Disposable;

public class UIContext implements Disposable{

    private final UIRenderer renderer;
    private UIComponent root;
    private final MouseButtonCallback mouseButtonCallback;
    private final WinResizedCallback winResizeCallback;
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

            final UIComponent component = getHoveredComponent();
            if(component == null)
                return;

            component.cache().press();
            component.input().invokePressCallbacks(button);
        });

        this.winResizeCallback = (window, width, height) -> renderer.resize(width, height);
    }

    public UIContext(UIComponent root){
        this();
        setRootComponent(root);
    }


    public void setCornerSoftness(float softness){
        renderer.setCornerSoftness(softness);
    }

    public void setBorderSoftness(float softness){
        renderer.setBorderSoftness(softness);
    }


    public void enable(){
        Jpize.context().callbacks().addWinResizedCallback(winResizeCallback);
        winResizeCallback.invoke(null, Jpize.getWidth(), Jpize.getHeight());
        enableTouchingDelayed = true;
    }

    public void disable(){
        Jpize.context().callbacks().removeMouseButtonCallback(mouseButtonCallback);
        Jpize.context().callbacks().removeWinResizedCallback(winResizeCallback);
        enableTouchingDelayed = false;
    }


    public UIComponent getHoveredComponent(){
        return renderer.stencil().get(Jpize.getX(), Jpize.getY());
    }

    public boolean isComponentHovered(UIComponent component){
        return component == getHoveredComponent();
    }

    private void release(UIComponent component, Btn button, MouseButtonAction action){
        if(component.cache().release())
            component.input().invokeReleaseCallbacks(button);
        for(UIComponent child: component.children())
            if(child.input().isClickable())
                release(child, button, action);
    }


    public UIComponent getRootComponent(){
        return root;
    }

    public <T extends UIComponent> T getByID(String ID){
        return root.getByID(ID);
    }

    public void setRootComponent(UIComponent root){
        this.root = root;
        this.root.setRenderer(renderer);
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
        if(component == null)
            return;

        component.update();
        component.render();

        if(component.input().isClickable()){
            final UIComponentCache cache = component.cache();
            renderer.stencil().fill(cache.x, cache.y, cache.width, cache.height, component);
        }

        for(UIComponent child: component.children())
            render(child);
    }


    @Override
    public void dispose(){
        disable();
        renderer.dispose();
    }

}
