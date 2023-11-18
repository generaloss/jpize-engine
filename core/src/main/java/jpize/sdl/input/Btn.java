package jpize.sdl.input;

import jpize.Jpize;

import static io.github.libsdl4j.api.mouse.SDL_Button.*;

public enum Btn{

    LEFT  (SDL_BUTTON_LEFT  ),
    MIDDLE(SDL_BUTTON_MIDDLE),
    RIGHT (SDL_BUTTON_RIGHT ),
    X1    (SDL_BUTTON_X1    ),
    X2    (SDL_BUTTON_X2    );
    
    
    public final int SDL;

    Btn(int SDL){
        this.SDL = SDL;
    }


    public boolean isDown(){
        return Jpize.input().isButtonDown(this);
    }

    public boolean isPressed(){
        return Jpize.input().isButtonPressed(this);
    }

    public boolean isReleased(){
        return Jpize.input().isButtonReleased(this);
    }
    
}
