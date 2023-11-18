package jpize.sdl.event;

import io.github.libsdl4j.api.keyboard.SDL_Keysym;
import jpize.sdl.input.Key;
import jpize.sdl.input.KeyAction;
import jpize.sdl.input.KeyMods;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class SdlCallbacks{

    private final List<CharCallback> charCallbacks;
    private final List<KeyCallback> keyCallbacks;
    private final KeyMods mods;

    public SdlCallbacks(){
        this.charCallbacks = new CopyOnWriteArrayList<>();
        this.keyCallbacks = new CopyOnWriteArrayList<>();
        this.mods = new KeyMods();
    }


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


    public void addCharCallback(CharCallback callback){
        charCallbacks.add(callback);
    }

    public void removeCharCallback(CharCallback callback){
        charCallbacks.add(callback);
    }


    public void addKeyCallback(KeyCallback callback){
        keyCallbacks.add(callback);
    }

    public void removeKeyCallback(KeyCallback callback){
        keyCallbacks.add(callback);
    }

}
