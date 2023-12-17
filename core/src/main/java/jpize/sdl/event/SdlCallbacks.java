package jpize.sdl.event;

import io.github.libsdl4j.api.event.events.SDL_MouseButtonEvent;
import io.github.libsdl4j.api.keyboard.SDL_Keysym;
import jpize.io.Window;
import jpize.sdl.event.keyboard.CharCallback;
import jpize.sdl.event.keyboard.KeyCallback;
import jpize.sdl.event.mouse.MouseButtonAction;
import jpize.sdl.event.mouse.MouseButtonCallback;
import jpize.sdl.event.window.*;
import jpize.sdl.input.Btn;
import jpize.sdl.input.Key;
import jpize.sdl.input.KeyAction;
import jpize.sdl.input.KeyMods;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SdlCallbacks{

    // keyboard
    private final List<CharCallback> charCallbacks = new CopyOnWriteArrayList<>();
    private final List<KeyCallback> keyCallbacks = new CopyOnWriteArrayList<>();
    // mouse
    private final List<MouseButtonCallback> mouseButtonCallbacks = new CopyOnWriteArrayList<>();
    // window
    private final List<WinCloseCallback> winCloseCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinEnterCallback> winEnterCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinExposedCallback> winExposedCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinFocusGainedCallback> winFocusGainedCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinFocusLostCallback> winFocusLostCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinHiddenCallback> winHiddenCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinHitTestCallback> winHitTestCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinLeaveCallback> winLeaveCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinMaximizedCallback> winMaximizedCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinMinimizedCallback> winMinimizedCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinMovedCallback> winMovedCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinResizedCallback> winResizedCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinRestoredCallback> winRestoredCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinShownCallback> winShownCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinSizeChangedCallback> winSizeChangedCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinTakeFocusCallback> winTakeFocusCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinIccProfChangedCallback> winIccProfChangedCallbacks = new CopyOnWriteArrayList<>();
    private final List<WinDisplayChangeCallback> winDisplayChangeCallbacks = new CopyOnWriteArrayList<>();

    private final KeyMods mods;

    public SdlCallbacks(){
        this.mods = new KeyMods();
    }


    // <--- INVOKE METHODS --->
    // keyboard

    public void invokeCharCallbacks(char character){
        for(CharCallback callback: charCallbacks)
            callback.invoke(character);
    }

    public void invokeKeyCallbacks(SDL_Keysym keySym, KeyAction action){
        final Key key = Key.fromScancode(keySym.scancode);
        mods.set(keySym.mod);

        for(KeyCallback callback: keyCallbacks)
            callback.invoke(key, action, mods);
    }

    // mouse

    public void invokeMouseButtonCallback(SDL_MouseButtonEvent event, MouseButtonAction action){
        final Btn button = Btn.fromSDL(event.button);
        for(MouseButtonCallback callback: mouseButtonCallbacks)
            callback.invoke(button, action);
    }

    // window

    public void invokeWinCloseCallbacks(Window window){
        for(WinCloseCallback callback: winCloseCallbacks)
            callback.invoke(window);
    }

    public void invokeWinEnterCallbacks(Window window){
        for(WinEnterCallback callback: winEnterCallbacks)
            callback.invoke(window);
    }

    public void invokeWinExposedCallbacks(Window window){
        for(WinExposedCallback callback: winExposedCallbacks)
            callback.invoke(window);
    }

    public void invokeWinFocusGainedCallbacks(Window window){
        for(WinFocusGainedCallback callback: winFocusGainedCallbacks)
            callback.invoke(window);
    }

    public void invokeWinFocusLostCallbacks(Window window){
        for(WinFocusLostCallback callback: winFocusLostCallbacks)
            callback.invoke(window);
    }

    public void invokeWinHiddenCallbacks(Window window){
        for(WinHiddenCallback callback: winHiddenCallbacks)
            callback.invoke(window);
    }

    public void invokeWinHitTestCallbacks(Window window){
        for(WinHitTestCallback callback: winHitTestCallbacks)
            callback.invoke(window);
    }

    public void invokeWinLeaveCallbacks(Window window){
        for(WinLeaveCallback callback: winLeaveCallbacks)
            callback.invoke(window);
    }

    public void invokeWinMaximizedCallbacks(Window window){
        for(WinMaximizedCallback callback: winMaximizedCallbacks)
            callback.invoke(window);
    }

    public void invokeWinMinimizedCallbacks(Window window){
        for(WinMinimizedCallback callback: winMinimizedCallbacks)
            callback.invoke(window);
    }

    public void invokeWinMovedCallbacks(Window window, int x, int y){
        for(WinMovedCallback callback: winMovedCallbacks)
            callback.invoke(window, x, y);
    }

    public void invokeWinResizedCallbacks(Window window, int width, int height){
        for(WinResizedCallback callback: winResizedCallbacks)
            callback.invoke(window, width, height);
    }

    public void invokeWinRestoredCallbacks(Window window){
        for(WinRestoredCallback callback: winRestoredCallbacks)
            callback.invoke(window);
    }

    public void invokeWinShownCallbacks(Window window){
        for(WinShownCallback callback: winShownCallbacks)
            callback.invoke(window);
    }

    public void invokeWinSizeChangedCallbacks(Window window, int width, int height){
        for(WinSizeChangedCallback callback: winSizeChangedCallbacks)
            callback.invoke(window, width, height);
    }

    public void invokeWinTakeFocusCallbacks(Window window){
        for(WinTakeFocusCallback callback: winTakeFocusCallbacks)
            callback.invoke(window);
    }

    public void invokeWinIccProfChangedCallbacks(Window window){
        for(WinIccProfChangedCallback callback: winIccProfChangedCallbacks)
            callback.invoke(window);
    }

    public void invokeWinDisplayChangeCallbacks(Window window, int data1){
        for(WinDisplayChangeCallback callback: winDisplayChangeCallbacks)
            callback.invoke(window, data1);
    }



    // <--- ADD / REMOVE METHODS --->
    // keyboard

    public void addCharCallback(CharCallback callback){
        charCallbacks.add(callback);
    }

    public void removeCharCallback(CharCallback callback){
        charCallbacks.remove(callback);
    }


    public void addKeyCallback(KeyCallback callback){
        keyCallbacks.add(callback);
    }

    public void removeKeyCallback(KeyCallback callback){
        keyCallbacks.remove(callback);
    }

    // mouse

    public void addMouseButtonCallback(MouseButtonCallback callback){
        mouseButtonCallbacks.add(callback);
    }

    public void removeMouseButtonCallback(MouseButtonCallback callback){
        mouseButtonCallbacks.remove(callback);
    }

    // window

    public void addWinCloseCallback(WinCloseCallback callback){
        winCloseCallbacks.add(callback);
    }

    public void removeWinCloseCallback(WinCloseCallback callback){
        winCloseCallbacks.remove(callback);
    }


    public void addWinEnterCallback(WinEnterCallback callback){
        winEnterCallbacks.add(callback);
    }

    public void removeWinEnterCallback(WinEnterCallback callback){
        winEnterCallbacks.remove(callback);
    }


    public void addWinExposedCallback(WinExposedCallback callback){
        winExposedCallbacks.add(callback);
    }

    public void removeWinExposedCallback(WinExposedCallback callback){
        winExposedCallbacks.remove(callback);
    }


    public void addWinFocusGainedCallback(WinFocusGainedCallback callback){
        winFocusGainedCallbacks.add(callback);
    }

    public void removeWinFocusGainedCallback(WinFocusGainedCallback callback){
        winFocusGainedCallbacks.remove(callback);
    }


    public void addWinFocusLostCallback(WinFocusLostCallback callback){
        winFocusLostCallbacks.add(callback);
    }

    public void removeWinFocusLostCallback(WinFocusLostCallback callback){
        winFocusLostCallbacks.remove(callback);
    }


    public void addWinHiddenCallback(WinHiddenCallback callback){
        winHiddenCallbacks.add(callback);
    }

    public void removeWinHiddenCallback(WinHiddenCallback callback){
        winHiddenCallbacks.remove(callback);
    }


    public void addWinHitTestCallback(WinHitTestCallback callback){
        winHitTestCallbacks.add(callback);
    }

    public void removeWinHitTestCallback(WinHitTestCallback callback){
        winHitTestCallbacks.remove(callback);
    }


    public void addWinLeaveCallback(WinLeaveCallback callback){
        winLeaveCallbacks.add(callback);
    }

    public void removeWinLeaveCallback(WinLeaveCallback callback){
        winLeaveCallbacks.remove(callback);
    }


    public void addWinMaximizedCallback(WinMaximizedCallback callback){
        winMaximizedCallbacks.add(callback);
    }

    public void removeWinMaximizedCallback(WinMaximizedCallback callback){
        winMaximizedCallbacks.remove(callback);
    }


    public void addWinMinimizedCallback(WinMinimizedCallback callback){
        winMinimizedCallbacks.add(callback);
    }

    public void removeWinMinimizedCallback(WinMinimizedCallback callback){
        winMinimizedCallbacks.remove(callback);
    }


    public void addWinMovedCallback(WinMovedCallback callback){
        winMovedCallbacks.add(callback);
    }

    public void removeWinMovedCallback(WinMovedCallback callback){
        winMovedCallbacks.remove(callback);
    }


    public void addWinResizedCallback(WinResizedCallback callback){
        winResizedCallbacks.add(callback);
    }

    public void removeWinResizedCallback(WinResizedCallback callback){
        winResizedCallbacks.remove(callback);
    }


    public void addWinRestoredCallback(WinRestoredCallback callback){
        winRestoredCallbacks.add(callback);
    }

    public void removeWinRestoredCallback(WinRestoredCallback callback){
        winRestoredCallbacks.remove(callback);
    }


    public void addWinShownCallback(WinShownCallback callback){
        winShownCallbacks.add(callback);
    }

    public void removeWinShownCallback(WinShownCallback callback){
        winShownCallbacks.remove(callback);
    }


    public void addWinSizeChangedCallback(WinSizeChangedCallback callback){
        winSizeChangedCallbacks.add(callback);
    }

    public void removeWinSizeChangedCallback(WinSizeChangedCallback callback){
        winSizeChangedCallbacks.remove(callback);
    }


    public void addWinTakeFocusCallback(WinTakeFocusCallback callback){
        winTakeFocusCallbacks.add(callback);
    }

    public void removeWinTakeFocusCallback(WinTakeFocusCallback callback){
        winTakeFocusCallbacks.remove(callback);
    }


    public void addWinIccProfChangedCallback(WinIccProfChangedCallback callback){
        winIccProfChangedCallbacks.add(callback);
    }

    public void removeWinIccProfChangedCallback(WinIccProfChangedCallback callback){
        winIccProfChangedCallbacks.remove(callback);
    }


    public void addWinDisplayChangeCallback(WinDisplayChangeCallback callback){
        winDisplayChangeCallbacks.add(callback);
    }

    public void removeWinDisplayChangeCallback(WinDisplayChangeCallback callback){
        winDisplayChangeCallbacks.remove(callback);
    }

}
