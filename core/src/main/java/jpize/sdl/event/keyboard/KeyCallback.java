package jpize.sdl.event.keyboard;

import jpize.sdl.input.Key;
import jpize.sdl.input.KeyAction;
import jpize.sdl.input.KeyMods;

public interface KeyCallback{

    void invoke(Key key, KeyAction action, KeyMods mods);

}
