package jpize.ui.context;

import jpize.Jpize;
import jpize.sdl.event.mouse.MouseButtonAction;
import jpize.sdl.event.mouse.MouseButtonCallback;
import jpize.sdl.input.Btn;
import jpize.ui.component.UIComponent;
import jpize.ui.component.UIComponentCache;
import jpize.ui.component.render.UIRenderer;
import jpize.util.Disposable;
import jpize.util.Resizable;

public class UIContext implements Disposable, Resizable{

    private final UIRenderer renderer;
    private UIComponent root;
    private final MouseButtonCallback mouseButtonCallback;

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
    }

    public UIContext(UIComponent root){
        this();
        setRootComponent(root);
    }


    public void enable(){
        Jpize.context().callbacks().addMouseButtonCallback(mouseButtonCallback);
    }

    public void disable(){
        Jpize.context().callbacks().removeMouseButtonCallback(mouseButtonCallback);
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
        Jpize.context().callbacks().removeMouseButtonCallback(mouseButtonCallback);
        renderer.dispose();
    }

    @Override
    public void resize(int width, int height){
        renderer.resize(width, height);
    }

}
