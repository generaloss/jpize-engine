package jpize.ui.component.input;

import jpize.sdl.input.Btn;
import jpize.ui.component.UIComponent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class UIInput{

    private final UIComponent component;
    private final List<UIPressCallback> pressCallbacks;
    private final List<UIReleaseCallback> releaseCallbacks;
    private boolean clickable;

    public UIInput(UIComponent component){
        this.component = component;
        this.pressCallbacks = new CopyOnWriteArrayList<>();
        this.releaseCallbacks = new CopyOnWriteArrayList<>();
    }


    public void invokePressCallbacks(Btn button){
        for(UIPressCallback callback: pressCallbacks)
            callback.invoke(component, button);
    }

    public void addPressCallback(UIPressCallback callback){
        pressCallbacks.add(callback);
    }

    public void removePressCallback(UIPressCallback callback){
        pressCallbacks.remove(callback);
    }

    public void invokeReleaseCallbacks(Btn button){
        for(UIReleaseCallback callback: releaseCallbacks)
            callback.invoke(component, button);
    }

    public void addReleaseCallback(UIReleaseCallback callback){
        releaseCallbacks.add(callback);
    }

    public void removeReleaseCallback(UIReleaseCallback callback){
        releaseCallbacks.remove(callback);
    }


    public boolean isClickable(){
        return clickable;
    }

    public void setClickable(boolean clickable){
        this.clickable = clickable;
    }


    public boolean isHovered(){
        return component.context().isHovered(component);
    }

    public boolean isFocused(){
        return component.context().isFocused(component);
    }

    public boolean isPressed(){
        return component.context().isPressed(component);
    }

}
