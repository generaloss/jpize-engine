package jpize.sdl.event.callback.keyboard;

import jpize.sdl.input.Key;
import jpize.sdl.input.KeyAction;
import jpize.sdl.input.KeyMods;

@FunctionalInterface
public interface KeyCallback{

    void invoke(Key key, KeyAction action, KeyMods mods);

}
