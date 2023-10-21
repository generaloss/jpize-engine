package jpize.tests.mcose.client.options;

import jpize.glfw.key.Key;

public enum KeyMapping implements Option<Key>{

    FORWARD     (Key.W),
    LEFT        (Key.A),
    BACK        (Key.S),
    RIGHT       (Key.D),
    JUMP        (Key.SPACE),
    SNEAK       (Key.LEFT_SHIFT),
    SPRINT      (Key.LEFT_CONTROL),

    CHAT        (Key.T),
    COMMAND     (Key.SLASH),
    ZOOM        (Key.C),

    FULLSCREEN  (Key.F11),
    TOGGLE_PERSPECTIVE(Key.F5),
    SCREENSHOT  (Key.F2);
    
    
    private final Key defaultKey;

    KeyMapping(Key defaultKey){
        this.defaultKey = defaultKey;
    }

    @Override
    public Key getDefault(){
        return defaultKey;
    }

}