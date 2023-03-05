package glit.tests.minecraft.client.game.options;

import glit.io.glfw.Key;

public enum KeyMapping{

    FORWARD   (Key.W),
    LEFT      (Key.A),
    BACK      (Key.S),
    RIGHT     (Key.F),
    JUMP      (Key.SPACE),
    SNEAK     (Key.LEFT_SHIFT),
    SPRINT    (Key.LEFT_CONTROL),

    CHAT      (Key.T),
    ZOOM      (Key.C),

    FULLSCREEN(Key.F11),
    SCREENSHOT(Key.F2);


    private final Key defaultKey;

    KeyMapping(Key defaultKey){
        this.defaultKey = defaultKey;
    }

    public Key getDefaultKey(){
        return defaultKey;
    }

}
