package jpize.ui.component.input;

import jpize.Jpize;
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
        this.clickable = true;
    }

    public boolean isCursorBounds(){
        return Jpize.input().isInBounds(component.cache().x, component.cache().y, component.cache().width, component.cache().height);
    }

    public boolean isTouchDown(){
        if(isCursorBounds() && Jpize.isTouchDown()){
            component.cache().press();
            return true;
        }
        return false;
    }

    public boolean isTouched(){
        return isCursorBounds() && Jpize.isTouched();
    }

    public boolean isTouchReleased(){
        if(Jpize.isTouchReleased())
            return component.cache().release();
        return false;
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

}
