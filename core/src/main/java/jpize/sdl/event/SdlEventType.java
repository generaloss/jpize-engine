package jpize.sdl.event;

import java.util.HashMap;
import java.util.Map;

import static io.github.libsdl4j.api.event.SDL_EventType.*;

public enum SdlEventType{

    FIRSTEVENT               (SDL_FIRSTEVENT              ),
    QUIT                     (SDL_QUIT                    ),
    APP_TERMINATING          (SDL_APP_TERMINATING         ),
    APP_LOWMEMORY            (SDL_APP_LOWMEMORY           ),
    APP_WILLENTERBACKGROUND  (SDL_APP_WILLENTERBACKGROUND ),
    APP_DIDENTERBACKGROUND   (SDL_APP_DIDENTERBACKGROUND  ),
    APP_WILLENTERFOREGROUND  (SDL_APP_WILLENTERFOREGROUND ),
    APP_DIDENTERFOREGROUND   (SDL_APP_DIDENTERFOREGROUND  ),
    LOCALECHANGED            (SDL_LOCALECHANGED           ),
    DISPLAYEVENT             (SDL_DISPLAYEVENT            ),
    WINDOWEVENT              (SDL_WINDOWEVENT             ),
    SYSWMEVENT               (SDL_SYSWMEVENT              ),
    KEYDOWN                  (SDL_KEYDOWN                 ),
    KEYUP                    (SDL_KEYUP                   ),
    TEXTEDITING              (SDL_TEXTEDITING             ),
    TEXTINPUT                (SDL_TEXTINPUT               ),
    KEYMAPCHANGED            (SDL_KEYMAPCHANGED           ),
    TEXTEDITING_EXT          (SDL_TEXTEDITING_EXT         ),
    MOUSEMOTION              (SDL_MOUSEMOTION             ),
    MOUSEBUTTONDOWN          (SDL_MOUSEBUTTONDOWN         ),
    MOUSEBUTTONUP            (SDL_MOUSEBUTTONUP           ),
    MOUSEWHEEL               (SDL_MOUSEWHEEL              ),
    JOYAXISMOTION            (SDL_JOYAXISMOTION           ),
    JOYBALLMOTION            (SDL_JOYBALLMOTION           ),
    JOYHATMOTION             (SDL_JOYHATMOTION            ),
    JOYBUTTONDOWN            (SDL_JOYBUTTONDOWN           ),
    JOYBUTTONUP              (SDL_JOYBUTTONUP             ),
    JOYDEVICEADDED           (SDL_JOYDEVICEADDED          ),
    JOYDEVICEREMOVED         (SDL_JOYDEVICEREMOVED        ),
    JOYBATTERYUPDATED        (SDL_JOYBATTERYUPDATED       ),
    CONTROLLERAXISMOTION     (SDL_CONTROLLERAXISMOTION    ),
    CONTROLLERBUTTONDOWN     (SDL_CONTROLLERBUTTONDOWN    ),
    CONTROLLERBUTTONUP       (SDL_CONTROLLERBUTTONUP      ),
    CONTROLLERDEVICEADDED    (SDL_CONTROLLERDEVICEADDED   ),
    CONTROLLERDEVICEREMOVED  (SDL_CONTROLLERDEVICEREMOVED ),
    CONTROLLERDEVICEREMAPPED (SDL_CONTROLLERDEVICEREMAPPED),
    CONTROLLERTOUCHPADDOWN   (SDL_CONTROLLERTOUCHPADDOWN  ),
    CONTROLLERTOUCHPADMOTION (SDL_CONTROLLERTOUCHPADMOTION),
    CONTROLLERTOUCHPADUP     (SDL_CONTROLLERTOUCHPADUP    ),
    CONTROLLERSENSORUPDATE   (SDL_CONTROLLERSENSORUPDATE  ),
    FINGERDOWN               (SDL_FINGERDOWN              ),
    FINGERUP                 (SDL_FINGERUP                ),
    FINGERMOTION             (SDL_FINGERMOTION            ),
    DOLLARGESTURE            (SDL_DOLLARGESTURE           ),
    DOLLARRECORD             (SDL_DOLLARRECORD            ),
    MULTIGESTURE             (SDL_MULTIGESTURE            ),
    CLIPBOARDUPDATE          (SDL_CLIPBOARDUPDATE         ),
    DROPFILE                 (SDL_DROPFILE                ),
    DROPTEXT                 (SDL_DROPTEXT                ),
    DROPBEGIN                (SDL_DROPBEGIN               ),
    DROPCOMPLETE             (SDL_DROPCOMPLETE            ),
    AUDIODEVICEADDED         (SDL_AUDIODEVICEADDED        ),
    AUDIODEVICEREMOVED       (SDL_AUDIODEVICEREMOVED      ),
    SENSORUPDATE             (SDL_SENSORUPDATE            ),
    RENDER_TARGETS_RESET     (SDL_RENDER_TARGETS_RESET    ),
    RENDER_DEVICE_RESET      (SDL_RENDER_DEVICE_RESET     ),
    POLLSENTINEL             (SDL_POLLSENTINEL            ),
    USEREVENT                (SDL_USEREVENT               ),
    LASTEVENT                (SDL_LASTEVENT               );

    public final int SDL;

    SdlEventType(int SDL){
        this.SDL = SDL;
    }


    public static SdlEventType bySdlConst(int SDL){
        return BY_SDL_CONST.get(SDL);
    }

    private static final Map<Integer, SdlEventType> BY_SDL_CONST = new HashMap<>(){{
        for(SdlEventType value: values())
            put(value.SDL, value);
    }};

}
