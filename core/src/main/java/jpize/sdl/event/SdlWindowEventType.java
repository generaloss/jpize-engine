package jpize.sdl.event;

import static io.github.libsdl4j.api.video.SDL_WindowEventID.*;

public enum SdlWindowEventType{

    NONE            (SDL_WINDOWEVENT_NONE           ),
    SHOWN           (SDL_WINDOWEVENT_SHOWN          ),
    HIDDEN          (SDL_WINDOWEVENT_HIDDEN         ),
    EXPOSED         (SDL_WINDOWEVENT_EXPOSED        ),
    MOVED           (SDL_WINDOWEVENT_MOVED          ),
    RESIZED         (SDL_WINDOWEVENT_RESIZED        ),
    SIZE_CHANGED    (SDL_WINDOWEVENT_SIZE_CHANGED   ),
    MINIMIZED       (SDL_WINDOWEVENT_MINIMIZED      ),
    MAXIMIZED       (SDL_WINDOWEVENT_MAXIMIZED      ),
    RESTORED        (SDL_WINDOWEVENT_RESTORED       ),
    ENTER           (SDL_WINDOWEVENT_ENTER          ),
    LEAVE           (SDL_WINDOWEVENT_LEAVE          ),
    FOCUS_GAINED    (SDL_WINDOWEVENT_FOCUS_GAINED   ),
    FOCUS_LOST      (SDL_WINDOWEVENT_FOCUS_LOST     ),
    CLOSE           (SDL_WINDOWEVENT_CLOSE          ),
    TAKE_FOCUS      (SDL_WINDOWEVENT_TAKE_FOCUS     ),
    HIT_TEST        (SDL_WINDOWEVENT_HIT_TEST       ),
    ICCPROF_CHANGED (SDL_WINDOWEVENT_ICCPROF_CHANGED),
    DISPLAY_CHANGE  (SDL_WINDOWEVENT_DISPLAY_CHANGE );


    public final int ID;

    SdlWindowEventType(int ID){
        this.ID = ID;
    }

    public static SdlWindowEventType byID(int ID){
        return values()[ID];
    }

}
