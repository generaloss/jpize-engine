package jpize.sdl.input;

import static io.github.libsdl4j.api.keycode.SDL_Keymod.*;

public class KeyMods{

    private int mods;

    public KeyMods(){ }

    public KeyMods(int mods){
        this.mods = mods;
    }


    public void set(int mods){
        this.mods = mods;
    }


    public boolean hasLShift(){
        return (mods & KMOD_LSHIFT) == KMOD_LSHIFT;
    }

    public boolean hasRShift(){
        return (mods & KMOD_RSHIFT) == KMOD_RSHIFT;
    }

    public boolean hasLCtrl(){
        return (mods & KMOD_LCTRL) == KMOD_LCTRL;
    }

    public boolean hasRCtrl(){
        return (mods & KMOD_RCTRL) == KMOD_RCTRL;
    }

    public boolean hasLAlt(){
        return (mods & KMOD_LALT) == KMOD_LALT;
    }

    public boolean hasRAlt(){
        return (mods & KMOD_RALT) == KMOD_RALT;
    }

    public boolean hasLGui(){
        return (mods & KMOD_LGUI) == KMOD_LGUI;
    }

    public boolean hasRGui(){
        return (mods & KMOD_RGUI) == KMOD_RGUI;
    }


    public boolean hasNum(){
        return (mods & KMOD_NUM) == KMOD_NUM;
    }

    public boolean hasCaps(){
        return (mods & KMOD_CAPS) == KMOD_CAPS;
    }

    public boolean hasMode(){
        return (mods & KMOD_MODE) == KMOD_MODE;
    }

    public boolean hasScroll(){
        return (mods & KMOD_SCROLL) == KMOD_SCROLL;
    }


    public boolean hasCtrl(){
        return (mods & KMOD_CTRL) != 0;
    }

    public boolean hasShift(){
        return (mods & KMOD_SHIFT) != 0;
    }

    public boolean hasAlt(){
        return (mods & KMOD_ALT) != 0;
    }

    public boolean hasGui(){
        return (mods & KMOD_GUI) != 0;
    }

}
