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


    public static SdlEventType fromSDL(int SDL){
        return sdlMap.get(SDL);
    }

    private static final Map<Integer, SdlEventType> sdlMap = new HashMap<>(){{
        put(FIRSTEVENT              .SDL, FIRSTEVENT              );
        put(QUIT                    .SDL, QUIT                    );
        put(APP_TERMINATING         .SDL, APP_TERMINATING         );
        put(APP_LOWMEMORY           .SDL, APP_LOWMEMORY           );
        put(APP_WILLENTERBACKGROUND .SDL, APP_WILLENTERBACKGROUND );
        put(APP_DIDENTERBACKGROUND  .SDL, APP_DIDENTERBACKGROUND  );
        put(APP_WILLENTERFOREGROUND .SDL, APP_WILLENTERFOREGROUND );
        put(APP_DIDENTERFOREGROUND  .SDL, APP_DIDENTERFOREGROUND  );
        put(LOCALECHANGED           .SDL, LOCALECHANGED           );
        put(DISPLAYEVENT            .SDL, DISPLAYEVENT            );
        put(WINDOWEVENT             .SDL, WINDOWEVENT             );
        put(SYSWMEVENT              .SDL, SYSWMEVENT              );
        put(KEYDOWN                 .SDL, KEYDOWN                 );
        put(KEYUP                   .SDL, KEYUP                   );
        put(TEXTEDITING             .SDL, TEXTEDITING             );
        put(TEXTINPUT               .SDL, TEXTINPUT               );
        put(KEYMAPCHANGED           .SDL, KEYMAPCHANGED           );
        put(TEXTEDITING_EXT         .SDL, TEXTEDITING_EXT         );
        put(MOUSEMOTION             .SDL, MOUSEMOTION             );
        put(MOUSEBUTTONDOWN         .SDL, MOUSEBUTTONDOWN         );
        put(MOUSEBUTTONUP           .SDL, MOUSEBUTTONUP           );
        put(MOUSEWHEEL              .SDL, MOUSEWHEEL              );
        put(JOYAXISMOTION           .SDL, JOYAXISMOTION           );
        put(JOYBALLMOTION           .SDL, JOYBALLMOTION           );
        put(JOYHATMOTION            .SDL, JOYHATMOTION            );
        put(JOYBUTTONDOWN           .SDL, JOYBUTTONDOWN           );
        put(JOYBUTTONUP             .SDL, JOYBUTTONUP             );
        put(JOYDEVICEADDED          .SDL, JOYDEVICEADDED          );
        put(JOYDEVICEREMOVED        .SDL, JOYDEVICEREMOVED        );
        put(JOYBATTERYUPDATED       .SDL, JOYBATTERYUPDATED       );
        put(CONTROLLERAXISMOTION    .SDL, CONTROLLERAXISMOTION    );
        put(CONTROLLERBUTTONDOWN    .SDL, CONTROLLERBUTTONDOWN    );
        put(CONTROLLERBUTTONUP      .SDL, CONTROLLERBUTTONUP      );
        put(CONTROLLERDEVICEADDED   .SDL, CONTROLLERDEVICEADDED   );
        put(CONTROLLERDEVICEREMOVED .SDL, CONTROLLERDEVICEREMOVED );
        put(CONTROLLERDEVICEREMAPPED.SDL, CONTROLLERDEVICEREMAPPED);
        put(CONTROLLERTOUCHPADDOWN  .SDL, CONTROLLERTOUCHPADDOWN  );
        put(CONTROLLERTOUCHPADMOTION.SDL, CONTROLLERTOUCHPADMOTION);
        put(CONTROLLERTOUCHPADUP    .SDL, CONTROLLERTOUCHPADUP    );
        put(CONTROLLERSENSORUPDATE  .SDL, CONTROLLERSENSORUPDATE  );
        put(FINGERDOWN              .SDL, FINGERDOWN              );
        put(FINGERUP                .SDL, FINGERUP                );
        put(FINGERMOTION            .SDL, FINGERMOTION            );
        put(DOLLARGESTURE           .SDL, DOLLARGESTURE           );
        put(DOLLARRECORD            .SDL, DOLLARRECORD            );
        put(MULTIGESTURE            .SDL, MULTIGESTURE            );
        put(CLIPBOARDUPDATE         .SDL, CLIPBOARDUPDATE         );
        put(DROPFILE                .SDL, DROPFILE                );
        put(DROPTEXT                .SDL, DROPTEXT                );
        put(DROPBEGIN               .SDL, DROPBEGIN               );
        put(DROPCOMPLETE            .SDL, DROPCOMPLETE            );
        put(AUDIODEVICEADDED        .SDL, AUDIODEVICEADDED        );
        put(AUDIODEVICEREMOVED      .SDL, AUDIODEVICEREMOVED      );
        put(SENSORUPDATE            .SDL, SENSORUPDATE            );
        put(RENDER_TARGETS_RESET    .SDL, RENDER_TARGETS_RESET    );
        put(RENDER_DEVICE_RESET     .SDL, RENDER_DEVICE_RESET     );
        put(POLLSENTINEL            .SDL, POLLSENTINEL            );
        put(USEREVENT               .SDL, USEREVENT               );
        put(LASTEVENT               .SDL, LASTEVENT               );
    }};

}
