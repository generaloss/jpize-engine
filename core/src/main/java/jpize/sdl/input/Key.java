package jpize.sdl.input;

import jpize.Jpize;

import java.util.HashMap;
import java.util.Map;

import static io.github.libsdl4j.api.scancode.SDL_Scancode.*;

public enum Key{

    UNKNOWN           (SDL_SCANCODE_UNKNOWN           ),
    A                 (SDL_SCANCODE_A                 ),
    B                 (SDL_SCANCODE_B                 ),
    C                 (SDL_SCANCODE_C                 ),
    D                 (SDL_SCANCODE_D                 ),
    E                 (SDL_SCANCODE_E                 ),
    F                 (SDL_SCANCODE_F                 ),
    G                 (SDL_SCANCODE_G                 ),
    H                 (SDL_SCANCODE_H                 ),
    I                 (SDL_SCANCODE_I                 ),
    J                 (SDL_SCANCODE_J                 ),
    K                 (SDL_SCANCODE_K                 ),
    L                 (SDL_SCANCODE_L                 ),
    M                 (SDL_SCANCODE_M                 ),
    N                 (SDL_SCANCODE_N                 ),
    O                 (SDL_SCANCODE_O                 ),
    P                 (SDL_SCANCODE_P                 ),
    Q                 (SDL_SCANCODE_Q                 ),
    R                 (SDL_SCANCODE_R                 ),
    S                 (SDL_SCANCODE_S                 ),
    T                 (SDL_SCANCODE_T                 ),
    U                 (SDL_SCANCODE_U                 ),
    V                 (SDL_SCANCODE_V                 ),
    W                 (SDL_SCANCODE_W                 ),
    X                 (SDL_SCANCODE_X                 ),
    Y                 (SDL_SCANCODE_Y                 ),
    Z                 (SDL_SCANCODE_Z                 ),
    NUM_1             (SDL_SCANCODE_1                 ),
    NUM_2             (SDL_SCANCODE_2                 ),
    NUM_3             (SDL_SCANCODE_3                 ),
    NUM_4             (SDL_SCANCODE_4                 ),
    NUM_5             (SDL_SCANCODE_5                 ),
    NUM_6             (SDL_SCANCODE_6                 ),
    NUM_7             (SDL_SCANCODE_7                 ),
    NUM_8             (SDL_SCANCODE_8                 ),
    NUM_9             (SDL_SCANCODE_9                 ),
    NUM_0             (SDL_SCANCODE_0                 ),
    ENTER             (SDL_SCANCODE_RETURN            ),
    ESCAPE            (SDL_SCANCODE_ESCAPE            ),
    BACKSPACE         (SDL_SCANCODE_BACKSPACE         ),
    TAB               (SDL_SCANCODE_TAB               ),
    SPACE             (SDL_SCANCODE_SPACE             ),
    MINUS             (SDL_SCANCODE_MINUS             ),
    EQUALS            (SDL_SCANCODE_EQUALS            ),
    LEFTBRACKET       (SDL_SCANCODE_LEFTBRACKET       ),
    RIGHTBRACKET      (SDL_SCANCODE_RIGHTBRACKET      ),
    BACKSLASH         (SDL_SCANCODE_BACKSLASH         ),
    NONUSHASH         (SDL_SCANCODE_NONUSHASH         ),
    SEMICOLON         (SDL_SCANCODE_SEMICOLON         ),
    APOSTROPHE        (SDL_SCANCODE_APOSTROPHE        ),
    GRAVE             (SDL_SCANCODE_GRAVE             ),
    COMMA             (SDL_SCANCODE_COMMA             ),
    PERIOD            (SDL_SCANCODE_PERIOD            ),
    SLASH             (SDL_SCANCODE_SLASH             ),
    CAPSLOCK          (SDL_SCANCODE_CAPSLOCK          ),
    F1                (SDL_SCANCODE_F1                ),
    F2                (SDL_SCANCODE_F2                ),
    F3                (SDL_SCANCODE_F3                ),
    F4                (SDL_SCANCODE_F4                ),
    F5                (SDL_SCANCODE_F5                ),
    F6                (SDL_SCANCODE_F6                ),
    F7                (SDL_SCANCODE_F7                ),
    F8                (SDL_SCANCODE_F8                ),
    F9                (SDL_SCANCODE_F9                ),
    F10               (SDL_SCANCODE_F10               ),
    F11               (SDL_SCANCODE_F11               ),
    F12               (SDL_SCANCODE_F12               ),
    PRINTSCREEN       (SDL_SCANCODE_PRINTSCREEN       ),
    SCROLLLOCK        (SDL_SCANCODE_SCROLLLOCK        ),
    PAUSE             (SDL_SCANCODE_PAUSE             ),
    INSERT            (SDL_SCANCODE_INSERT            ),
    HOME              (SDL_SCANCODE_HOME              ),
    PAGEUP            (SDL_SCANCODE_PAGEUP            ),
    DELETE            (SDL_SCANCODE_DELETE            ),
    END               (SDL_SCANCODE_END               ),
    PAGEDOWN          (SDL_SCANCODE_PAGEDOWN          ),
    RIGHT             (SDL_SCANCODE_RIGHT             ),
    LEFT              (SDL_SCANCODE_LEFT              ),
    DOWN              (SDL_SCANCODE_DOWN              ),
    UP                (SDL_SCANCODE_UP                ),
    NUMLOCKCLEAR      (SDL_SCANCODE_NUMLOCKCLEAR      ),
    KP_DIVIDE         (SDL_SCANCODE_KP_DIVIDE         ),
    KP_MULTIPLY       (SDL_SCANCODE_KP_MULTIPLY       ),
    KP_MINUS          (SDL_SCANCODE_KP_MINUS          ),
    KP_PLUS           (SDL_SCANCODE_KP_PLUS           ),
    KP_ENTER          (SDL_SCANCODE_KP_ENTER          ),
    KP_1              (SDL_SCANCODE_KP_1              ),
    KP_2              (SDL_SCANCODE_KP_2              ),
    KP_3              (SDL_SCANCODE_KP_3              ),
    KP_4              (SDL_SCANCODE_KP_4              ),
    KP_5              (SDL_SCANCODE_KP_5              ),
    KP_6              (SDL_SCANCODE_KP_6              ),
    KP_7              (SDL_SCANCODE_KP_7              ),
    KP_8              (SDL_SCANCODE_KP_8              ),
    KP_9              (SDL_SCANCODE_KP_9              ),
    KP_0              (SDL_SCANCODE_KP_0              ),
    KP_PERIOD         (SDL_SCANCODE_KP_PERIOD         ),
    NONUSBACKSLASH    (SDL_SCANCODE_NONUSBACKSLASH    ),
    APPLICATION       (SDL_SCANCODE_APPLICATION       ),
    POWER             (SDL_SCANCODE_POWER             ),
    KP_EQUALS         (SDL_SCANCODE_KP_EQUALS         ),
    F13               (SDL_SCANCODE_F13               ),
    F14               (SDL_SCANCODE_F14               ),
    F15               (SDL_SCANCODE_F15               ),
    F16               (SDL_SCANCODE_F16               ),
    F17               (SDL_SCANCODE_F17               ),
    F18               (SDL_SCANCODE_F18               ),
    F19               (SDL_SCANCODE_F19               ),
    F20               (SDL_SCANCODE_F20               ),
    F21               (SDL_SCANCODE_F21               ),
    F22               (SDL_SCANCODE_F22               ),
    F23               (SDL_SCANCODE_F23               ),
    F24               (SDL_SCANCODE_F24               ),
    EXECUTE           (SDL_SCANCODE_EXECUTE           ),
    HELP              (SDL_SCANCODE_HELP              ),
    MENU              (SDL_SCANCODE_MENU              ),
    SELECT            (SDL_SCANCODE_SELECT            ),
    STOP              (SDL_SCANCODE_STOP              ),
    AGAIN             (SDL_SCANCODE_AGAIN             ),
    UNDO              (SDL_SCANCODE_UNDO              ),
    CUT               (SDL_SCANCODE_CUT               ),
    COPY              (SDL_SCANCODE_COPY              ),
    PASTE             (SDL_SCANCODE_PASTE             ),
    FIND              (SDL_SCANCODE_FIND              ),
    MUTE              (SDL_SCANCODE_MUTE              ),
    VOLUMEUP          (SDL_SCANCODE_VOLUMEUP          ),
    VOLUMEDOWN        (SDL_SCANCODE_VOLUMEDOWN        ),
    KP_COMMA          (SDL_SCANCODE_KP_COMMA          ),
    KP_EQUALSAS400    (SDL_SCANCODE_KP_EQUALSAS400    ),
    INTERNATIONAL1    (SDL_SCANCODE_INTERNATIONAL1    ),
    INTERNATIONAL2    (SDL_SCANCODE_INTERNATIONAL2    ),
    INTERNATIONAL3    (SDL_SCANCODE_INTERNATIONAL3    ),
    INTERNATIONAL4    (SDL_SCANCODE_INTERNATIONAL4    ),
    INTERNATIONAL5    (SDL_SCANCODE_INTERNATIONAL5    ),
    INTERNATIONAL6    (SDL_SCANCODE_INTERNATIONAL6    ),
    INTERNATIONAL7    (SDL_SCANCODE_INTERNATIONAL7    ),
    INTERNATIONAL8    (SDL_SCANCODE_INTERNATIONAL8    ),
    INTERNATIONAL9    (SDL_SCANCODE_INTERNATIONAL9    ),
    LANG1             (SDL_SCANCODE_LANG1             ),
    LANG2             (SDL_SCANCODE_LANG2             ),
    LANG3             (SDL_SCANCODE_LANG3             ),
    LANG4             (SDL_SCANCODE_LANG4             ),
    LANG5             (SDL_SCANCODE_LANG5             ),
    LANG6             (SDL_SCANCODE_LANG6             ),
    LANG7             (SDL_SCANCODE_LANG7             ),
    LANG8             (SDL_SCANCODE_LANG8             ),
    LANG9             (SDL_SCANCODE_LANG9             ),
    ALTERASE          (SDL_SCANCODE_ALTERASE          ),
    SYSREQ            (SDL_SCANCODE_SYSREQ            ),
    CANCEL            (SDL_SCANCODE_CANCEL            ),
    CLEAR             (SDL_SCANCODE_CLEAR             ),
    PRIOR             (SDL_SCANCODE_PRIOR             ),
    RETURN2           (SDL_SCANCODE_RETURN2           ),
    SEPARATOR         (SDL_SCANCODE_SEPARATOR         ),
    OUT               (SDL_SCANCODE_OUT               ),
    OPER              (SDL_SCANCODE_OPER              ),
    CLEARAGAIN        (SDL_SCANCODE_CLEARAGAIN        ),
    CRSEL             (SDL_SCANCODE_CRSEL             ),
    EXSEL             (SDL_SCANCODE_EXSEL             ),
    KP_00             (SDL_SCANCODE_KP_00             ),
    KP_000            (SDL_SCANCODE_KP_000            ),
    THOUSANDSSEPARATOR(SDL_SCANCODE_THOUSANDSSEPARATOR),
    DECIMALSEPARATOR  (SDL_SCANCODE_DECIMALSEPARATOR  ),
    CURRENCYUNIT      (SDL_SCANCODE_CURRENCYUNIT      ),
    CURRENCYSUBUNIT   (SDL_SCANCODE_CURRENCYSUBUNIT   ),
    KP_LEFTPAREN      (SDL_SCANCODE_KP_LEFTPAREN      ),
    KP_RIGHTPAREN     (SDL_SCANCODE_KP_RIGHTPAREN     ),
    KP_LEFTBRACE      (SDL_SCANCODE_KP_LEFTBRACE      ),
    KP_RIGHTBRACE     (SDL_SCANCODE_KP_RIGHTBRACE     ),
    KP_TAB            (SDL_SCANCODE_KP_TAB            ),
    KP_BACKSPACE      (SDL_SCANCODE_KP_BACKSPACE      ),
    KP_A              (SDL_SCANCODE_KP_A              ),
    KP_B              (SDL_SCANCODE_KP_B              ),
    KP_C              (SDL_SCANCODE_KP_C              ),
    KP_D              (SDL_SCANCODE_KP_D              ),
    KP_E              (SDL_SCANCODE_KP_E              ),
    KP_F              (SDL_SCANCODE_KP_F              ),
    KP_XOR            (SDL_SCANCODE_KP_XOR            ),
    KP_POWER          (SDL_SCANCODE_KP_POWER          ),
    KP_PERCENT        (SDL_SCANCODE_KP_PERCENT        ),
    KP_LESS           (SDL_SCANCODE_KP_LESS           ),
    KP_GREATER        (SDL_SCANCODE_KP_GREATER        ),
    KP_AMPERSAND      (SDL_SCANCODE_KP_AMPERSAND      ),
    KP_DBLAMPERSAND   (SDL_SCANCODE_KP_DBLAMPERSAND   ),
    KP_VERTICALBAR    (SDL_SCANCODE_KP_VERTICALBAR    ),
    KP_DBLVERTICALBAR (SDL_SCANCODE_KP_DBLVERTICALBAR ),
    KP_COLON          (SDL_SCANCODE_KP_COLON          ),
    KP_HASH           (SDL_SCANCODE_KP_HASH           ),
    KP_SPACE          (SDL_SCANCODE_KP_SPACE          ),
    KP_AT             (SDL_SCANCODE_KP_AT             ),
    KP_EXCLAM         (SDL_SCANCODE_KP_EXCLAM         ),
    KP_MEMSTORE       (SDL_SCANCODE_KP_MEMSTORE       ),
    KP_MEMRECALL      (SDL_SCANCODE_KP_MEMRECALL      ),
    KP_MEMCLEAR       (SDL_SCANCODE_KP_MEMCLEAR       ),
    KP_MEMADD         (SDL_SCANCODE_KP_MEMADD         ),
    KP_MEMSUBTRACT    (SDL_SCANCODE_KP_MEMSUBTRACT    ),
    KP_MEMMULTIPLY    (SDL_SCANCODE_KP_MEMMULTIPLY    ),
    KP_MEMDIVIDE      (SDL_SCANCODE_KP_MEMDIVIDE      ),
    KP_PLUSMINUS      (SDL_SCANCODE_KP_PLUSMINUS      ),
    KP_CLEAR          (SDL_SCANCODE_KP_CLEAR          ),
    KP_CLEARENTRY     (SDL_SCANCODE_KP_CLEARENTRY     ),
    KP_BINARY         (SDL_SCANCODE_KP_BINARY         ),
    KP_OCTAL          (SDL_SCANCODE_KP_OCTAL          ),
    KP_DECIMAL        (SDL_SCANCODE_KP_DECIMAL        ),
    KP_HEXADECIMAL    (SDL_SCANCODE_KP_HEXADECIMAL    ),
    LCTRL             (SDL_SCANCODE_LCTRL             ),
    LSHIFT            (SDL_SCANCODE_LSHIFT            ),
    LALT              (SDL_SCANCODE_LALT              ),
    LGUI              (SDL_SCANCODE_LGUI              ),
    RCTRL             (SDL_SCANCODE_RCTRL             ),
    RSHIFT            (SDL_SCANCODE_RSHIFT            ),
    RALT              (SDL_SCANCODE_RALT              ),
    RGUI              (SDL_SCANCODE_RGUI              ),
    MODE              (SDL_SCANCODE_MODE              ),
    AUDIONEXT         (SDL_SCANCODE_AUDIONEXT         ),
    AUDIOPREV         (SDL_SCANCODE_AUDIOPREV         ),
    AUDIOSTOP         (SDL_SCANCODE_AUDIOSTOP         ),
    AUDIOPLAY         (SDL_SCANCODE_AUDIOPLAY         ),
    AUDIOMUTE         (SDL_SCANCODE_AUDIOMUTE         ),
    MEDIASELECT       (SDL_SCANCODE_MEDIASELECT       ),
    WWW               (SDL_SCANCODE_WWW               ),
    MAIL              (SDL_SCANCODE_MAIL              ),
    CALCULATOR        (SDL_SCANCODE_CALCULATOR        ),
    COMPUTER          (SDL_SCANCODE_COMPUTER          ),
    AC_SEARCH         (SDL_SCANCODE_AC_SEARCH         ),
    AC_HOME           (SDL_SCANCODE_AC_HOME           ),
    AC_BACK           (SDL_SCANCODE_AC_BACK           ),
    AC_FORWARD        (SDL_SCANCODE_AC_FORWARD        ),
    AC_STOP           (SDL_SCANCODE_AC_STOP           ),
    AC_REFRESH        (SDL_SCANCODE_AC_REFRESH        ),
    AC_BOOKMARKS      (SDL_SCANCODE_AC_BOOKMARKS      ),
    BRIGHTNESSDOWN    (SDL_SCANCODE_BRIGHTNESSDOWN    ),
    BRIGHTNESSUP      (SDL_SCANCODE_BRIGHTNESSUP      ),
    DISPLAYSWITCH     (SDL_SCANCODE_DISPLAYSWITCH     ),
    KBDILLUMTOGGLE    (SDL_SCANCODE_KBDILLUMTOGGLE    ),
    KBDILLUMDOWN      (SDL_SCANCODE_KBDILLUMDOWN      ),
    KBDILLUMUP        (SDL_SCANCODE_KBDILLUMUP        ),
    EJECT             (SDL_SCANCODE_EJECT             ),
    SLEEP             (SDL_SCANCODE_SLEEP             ),
    APP1              (SDL_SCANCODE_APP1              ),
    APP2              (SDL_SCANCODE_APP2              ),
    AUDIOREWIND       (SDL_SCANCODE_AUDIOREWIND       ),
    AUDIOFASTFORWARD  (SDL_SCANCODE_AUDIOFASTFORWARD  ),
    SOFTLEFT          (SDL_SCANCODE_SOFTLEFT          ),
    SOFTRIGHT         (SDL_SCANCODE_SOFTRIGHT         ),
    CALL              (SDL_SCANCODE_CALL              ),
    ENDCALL           (SDL_SCANCODE_ENDCALL           );


    public final int SDL;

    Key(int SDL){
        this.SDL = SDL;
    }


    public boolean isDown(){
        return Jpize.input().isKeyDown(this);
    }

    public boolean isPressed(){
        return Jpize.input().isKeyPressed(this);
    }

    public boolean isReleased(){
        return Jpize.input().isKeyReleased(this);
    }


    public static Key fromScancode(int scancode){
        return scancodeKeyMap.get(scancode);
    }


    public static final Map<Integer, Key> scancodeKeyMap = new HashMap<>(){{
        put(UNKNOWN           .SDL, UNKNOWN           );
        put(A                 .SDL, A                 );
        put(B                 .SDL, B                 );
        put(C                 .SDL, C                 );
        put(D                 .SDL, D                 );
        put(E                 .SDL, E                 );
        put(F                 .SDL, F                 );
        put(G                 .SDL, G                 );
        put(H                 .SDL, H                 );
        put(I                 .SDL, I                 );
        put(J                 .SDL, J                 );
        put(K                 .SDL, K                 );
        put(L                 .SDL, L                 );
        put(M                 .SDL, M                 );
        put(N                 .SDL, N                 );
        put(O                 .SDL, O                 );
        put(P                 .SDL, P                 );
        put(Q                 .SDL, Q                 );
        put(R                 .SDL, R                 );
        put(S                 .SDL, S                 );
        put(T                 .SDL, T                 );
        put(U                 .SDL, U                 );
        put(V                 .SDL, V                 );
        put(W                 .SDL, W                 );
        put(X                 .SDL, X                 );
        put(Y                 .SDL, Y                 );
        put(Z                 .SDL, Z                 );
        put(NUM_1             .SDL, NUM_1             );
        put(NUM_2             .SDL, NUM_2             );
        put(NUM_3             .SDL, NUM_3             );
        put(NUM_4             .SDL, NUM_4             );
        put(NUM_5             .SDL, NUM_5             );
        put(NUM_6             .SDL, NUM_6             );
        put(NUM_7             .SDL, NUM_7             );
        put(NUM_8             .SDL, NUM_8             );
        put(NUM_9             .SDL, NUM_9             );
        put(NUM_0             .SDL, NUM_0             );
        put(ENTER.SDL, ENTER);
        put(ESCAPE            .SDL, ESCAPE            );
        put(BACKSPACE         .SDL, BACKSPACE         );
        put(TAB               .SDL, TAB               );
        put(SPACE             .SDL, SPACE             );
        put(MINUS             .SDL, MINUS             );
        put(EQUALS            .SDL, EQUALS            );
        put(LEFTBRACKET       .SDL, LEFTBRACKET       );
        put(RIGHTBRACKET      .SDL, RIGHTBRACKET      );
        put(BACKSLASH         .SDL, BACKSLASH         );
        put(NONUSHASH         .SDL, NONUSHASH         );
        put(SEMICOLON         .SDL, SEMICOLON         );
        put(APOSTROPHE        .SDL, APOSTROPHE        );
        put(GRAVE             .SDL, GRAVE             );
        put(COMMA             .SDL, COMMA             );
        put(PERIOD            .SDL, PERIOD            );
        put(SLASH             .SDL, SLASH             );
        put(CAPSLOCK          .SDL, CAPSLOCK          );
        put(F1                .SDL, F1                );
        put(F2                .SDL, F2                );
        put(F3                .SDL, F3                );
        put(F4                .SDL, F4                );
        put(F5                .SDL, F5                );
        put(F6                .SDL, F6                );
        put(F7                .SDL, F7                );
        put(F8                .SDL, F8                );
        put(F9                .SDL, F9                );
        put(F10               .SDL, F10               );
        put(F11               .SDL, F11               );
        put(F12               .SDL, F12               );
        put(PRINTSCREEN       .SDL, PRINTSCREEN       );
        put(SCROLLLOCK        .SDL, SCROLLLOCK        );
        put(PAUSE             .SDL, PAUSE             );
        put(INSERT            .SDL, INSERT            );
        put(HOME              .SDL, HOME              );
        put(PAGEUP            .SDL, PAGEUP            );
        put(DELETE            .SDL, DELETE            );
        put(END               .SDL, END               );
        put(PAGEDOWN          .SDL, PAGEDOWN          );
        put(RIGHT             .SDL, RIGHT             );
        put(LEFT              .SDL, LEFT              );
        put(DOWN              .SDL, DOWN              );
        put(UP                .SDL, UP                );
        put(NUMLOCKCLEAR      .SDL, NUMLOCKCLEAR      );
        put(KP_DIVIDE         .SDL, KP_DIVIDE         );
        put(KP_MULTIPLY       .SDL, KP_MULTIPLY       );
        put(KP_MINUS          .SDL, KP_MINUS          );
        put(KP_PLUS           .SDL, KP_PLUS           );
        put(KP_ENTER          .SDL, KP_ENTER          );
        put(KP_1              .SDL, KP_1              );
        put(KP_2              .SDL, KP_2              );
        put(KP_3              .SDL, KP_3              );
        put(KP_4              .SDL, KP_4              );
        put(KP_5              .SDL, KP_5              );
        put(KP_6              .SDL, KP_6              );
        put(KP_7              .SDL, KP_7              );
        put(KP_8              .SDL, KP_8              );
        put(KP_9              .SDL, KP_9              );
        put(KP_0              .SDL, KP_0              );
        put(KP_PERIOD         .SDL, KP_PERIOD         );
        put(NONUSBACKSLASH    .SDL, NONUSBACKSLASH    );
        put(APPLICATION       .SDL, APPLICATION       );
        put(POWER             .SDL, POWER             );
        put(KP_EQUALS         .SDL, KP_EQUALS         );
        put(F13               .SDL, F13               );
        put(F14               .SDL, F14               );
        put(F15               .SDL, F15               );
        put(F16               .SDL, F16               );
        put(F17               .SDL, F17               );
        put(F18               .SDL, F18               );
        put(F19               .SDL, F19               );
        put(F20               .SDL, F20               );
        put(F21               .SDL, F21               );
        put(F22               .SDL, F22               );
        put(F23               .SDL, F23               );
        put(F24               .SDL, F24               );
        put(EXECUTE           .SDL, EXECUTE           );
        put(HELP              .SDL, HELP              );
        put(MENU              .SDL, MENU              );
        put(SELECT            .SDL, SELECT            );
        put(STOP              .SDL, STOP              );
        put(AGAIN             .SDL, AGAIN             );
        put(UNDO              .SDL, UNDO              );
        put(CUT               .SDL, CUT               );
        put(COPY              .SDL, COPY              );
        put(PASTE             .SDL, PASTE             );
        put(FIND              .SDL, FIND              );
        put(MUTE              .SDL, MUTE              );
        put(VOLUMEUP          .SDL, VOLUMEUP          );
        put(VOLUMEDOWN        .SDL, VOLUMEDOWN        );
        put(KP_COMMA          .SDL, KP_COMMA          );
        put(KP_EQUALSAS400    .SDL, KP_EQUALSAS400    );
        put(INTERNATIONAL1    .SDL, INTERNATIONAL1    );
        put(INTERNATIONAL2    .SDL, INTERNATIONAL2    );
        put(INTERNATIONAL3    .SDL, INTERNATIONAL3    );
        put(INTERNATIONAL4    .SDL, INTERNATIONAL4    );
        put(INTERNATIONAL5    .SDL, INTERNATIONAL5    );
        put(INTERNATIONAL6    .SDL, INTERNATIONAL6    );
        put(INTERNATIONAL7    .SDL, INTERNATIONAL7    );
        put(INTERNATIONAL8    .SDL, INTERNATIONAL8    );
        put(INTERNATIONAL9    .SDL, INTERNATIONAL9    );
        put(LANG1             .SDL, LANG1             );
        put(LANG2             .SDL, LANG2             );
        put(LANG3             .SDL, LANG3             );
        put(LANG4             .SDL, LANG4             );
        put(LANG5             .SDL, LANG5             );
        put(LANG6             .SDL, LANG6             );
        put(LANG7             .SDL, LANG7             );
        put(LANG8             .SDL, LANG8             );
        put(LANG9             .SDL, LANG9             );
        put(ALTERASE          .SDL, ALTERASE          );
        put(SYSREQ            .SDL, SYSREQ            );
        put(CANCEL            .SDL, CANCEL            );
        put(CLEAR             .SDL, CLEAR             );
        put(PRIOR             .SDL, PRIOR             );
        put(RETURN2           .SDL, RETURN2           );
        put(SEPARATOR         .SDL, SEPARATOR         );
        put(OUT               .SDL, OUT               );
        put(OPER              .SDL, OPER              );
        put(CLEARAGAIN        .SDL, CLEARAGAIN        );
        put(CRSEL             .SDL, CRSEL             );
        put(EXSEL             .SDL, EXSEL             );
        put(KP_00             .SDL, KP_00             );
        put(KP_000            .SDL, KP_000            );
        put(THOUSANDSSEPARATOR.SDL, THOUSANDSSEPARATOR);
        put(DECIMALSEPARATOR  .SDL, DECIMALSEPARATOR  );
        put(CURRENCYUNIT      .SDL, CURRENCYUNIT      );
        put(CURRENCYSUBUNIT   .SDL, CURRENCYSUBUNIT   );
        put(KP_LEFTPAREN      .SDL, KP_LEFTPAREN      );
        put(KP_RIGHTPAREN     .SDL, KP_RIGHTPAREN     );
        put(KP_LEFTBRACE      .SDL, KP_LEFTBRACE      );
        put(KP_RIGHTBRACE     .SDL, KP_RIGHTBRACE     );
        put(KP_TAB            .SDL, KP_TAB            );
        put(KP_BACKSPACE      .SDL, KP_BACKSPACE      );
        put(KP_A              .SDL, KP_A              );
        put(KP_B              .SDL, KP_B              );
        put(KP_C              .SDL, KP_C              );
        put(KP_D              .SDL, KP_D              );
        put(KP_E              .SDL, KP_E              );
        put(KP_F              .SDL, KP_F              );
        put(KP_XOR            .SDL, KP_XOR            );
        put(KP_POWER          .SDL, KP_POWER          );
        put(KP_PERCENT        .SDL, KP_PERCENT        );
        put(KP_LESS           .SDL, KP_LESS           );
        put(KP_GREATER        .SDL, KP_GREATER        );
        put(KP_AMPERSAND      .SDL, KP_AMPERSAND      );
        put(KP_DBLAMPERSAND   .SDL, KP_DBLAMPERSAND   );
        put(KP_VERTICALBAR    .SDL, KP_VERTICALBAR    );
        put(KP_DBLVERTICALBAR .SDL, KP_DBLVERTICALBAR );
        put(KP_COLON          .SDL, KP_COLON          );
        put(KP_HASH           .SDL, KP_HASH           );
        put(KP_SPACE          .SDL, KP_SPACE          );
        put(KP_AT             .SDL, KP_AT             );
        put(KP_EXCLAM         .SDL, KP_EXCLAM         );
        put(KP_MEMSTORE       .SDL, KP_MEMSTORE       );
        put(KP_MEMRECALL      .SDL, KP_MEMRECALL      );
        put(KP_MEMCLEAR       .SDL, KP_MEMCLEAR       );
        put(KP_MEMADD         .SDL, KP_MEMADD         );
        put(KP_MEMSUBTRACT    .SDL, KP_MEMSUBTRACT    );
        put(KP_MEMMULTIPLY    .SDL, KP_MEMMULTIPLY    );
        put(KP_MEMDIVIDE      .SDL, KP_MEMDIVIDE      );
        put(KP_PLUSMINUS      .SDL, KP_PLUSMINUS      );
        put(KP_CLEAR          .SDL, KP_CLEAR          );
        put(KP_CLEARENTRY     .SDL, KP_CLEARENTRY     );
        put(KP_BINARY         .SDL, KP_BINARY         );
        put(KP_OCTAL          .SDL, KP_OCTAL          );
        put(KP_DECIMAL        .SDL, KP_DECIMAL        );
        put(KP_HEXADECIMAL    .SDL, KP_HEXADECIMAL    );
        put(LCTRL             .SDL, LCTRL             );
        put(LSHIFT            .SDL, LSHIFT            );
        put(LALT              .SDL, LALT              );
        put(LGUI              .SDL, LGUI              );
        put(RCTRL             .SDL, RCTRL             );
        put(RSHIFT            .SDL, RSHIFT            );
        put(RALT              .SDL, RALT              );
        put(RGUI              .SDL, RGUI              );
        put(MODE              .SDL, MODE              );
        put(AUDIONEXT         .SDL, AUDIONEXT         );
        put(AUDIOPREV         .SDL, AUDIOPREV         );
        put(AUDIOSTOP         .SDL, AUDIOSTOP         );
        put(AUDIOPLAY         .SDL, AUDIOPLAY         );
        put(AUDIOMUTE         .SDL, AUDIOMUTE         );
        put(MEDIASELECT       .SDL, MEDIASELECT       );
        put(WWW               .SDL, WWW               );
        put(MAIL              .SDL, MAIL              );
        put(CALCULATOR        .SDL, CALCULATOR        );
        put(COMPUTER          .SDL, COMPUTER          );
        put(AC_SEARCH         .SDL, AC_SEARCH         );
        put(AC_HOME           .SDL, AC_HOME           );
        put(AC_BACK           .SDL, AC_BACK           );
        put(AC_FORWARD        .SDL, AC_FORWARD        );
        put(AC_STOP           .SDL, AC_STOP           );
        put(AC_REFRESH        .SDL, AC_REFRESH        );
        put(AC_BOOKMARKS      .SDL, AC_BOOKMARKS      );
        put(BRIGHTNESSDOWN    .SDL, BRIGHTNESSDOWN    );
        put(BRIGHTNESSUP      .SDL, BRIGHTNESSUP      );
        put(DISPLAYSWITCH     .SDL, DISPLAYSWITCH     );
        put(KBDILLUMTOGGLE    .SDL, KBDILLUMTOGGLE    );
        put(KBDILLUMDOWN      .SDL, KBDILLUMDOWN      );
        put(KBDILLUMUP        .SDL, KBDILLUMUP        );
        put(EJECT             .SDL, EJECT             );
        put(SLEEP             .SDL, SLEEP             );
        put(APP1              .SDL, APP1              );
        put(APP2              .SDL, APP2              );
        put(AUDIOREWIND       .SDL, AUDIOREWIND       );
        put(AUDIOFASTFORWARD  .SDL, AUDIOFASTFORWARD  );
        put(SOFTLEFT          .SDL, SOFTLEFT          );
        put(SOFTRIGHT         .SDL, SOFTRIGHT         );
        put(CALL              .SDL, CALL              );
        put(ENDCALL           .SDL, ENDCALL           );
    }};

}
