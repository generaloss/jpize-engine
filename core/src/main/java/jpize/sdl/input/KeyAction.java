package jpize.sdl.input;

public enum KeyAction{

    DOWN(true),
    REPEAT(true),
    UP(false);

    public final boolean pressed;

    KeyAction(boolean pressed){
        this.pressed = pressed;
    }

}
