package pize.glfw.key;

import pize.Pize;
import pize.glfw.glfw.Glfw;

import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.glfw.GLFW.*;

public enum Key{

    SPACE         (GLFW_KEY_SPACE     , "Space"),
    APOSTROPHE    (GLFW_KEY_APOSTROPHE), // '
    COMMA         (GLFW_KEY_COMMA     ), // ,
    MINUS         (GLFW_KEY_MINUS     ), // -
    PERIOD        (GLFW_KEY_PERIOD    ), // .
    SLASH         (GLFW_KEY_SLASH     ), // /
    NUM_0         (GLFW_KEY_0         ), // 0
    NUM_1         (GLFW_KEY_1         ), // 1
    NUM_2         (GLFW_KEY_2         ), // 2
    NUM_3         (GLFW_KEY_3         ), // 3
    NUM_4         (GLFW_KEY_4         ), // 4
    NUM_5         (GLFW_KEY_5         ), // 5
    NUM_6         (GLFW_KEY_6         ), // 6
    NUM_7         (GLFW_KEY_7         ), // 7
    NUM_8         (GLFW_KEY_8         ), // 8
    NUM_9         (GLFW_KEY_9         ), // 9
    SEMICOLON     (GLFW_KEY_SEMICOLON ), // ;
    EQUAL         (GLFW_KEY_EQUAL     ), // =

    A             (GLFW_KEY_A, "A"),
    B             (GLFW_KEY_B, "B"),
    C             (GLFW_KEY_C, "C"),
    D             (GLFW_KEY_D, "D"),
    E             (GLFW_KEY_E, "E"),
    F             (GLFW_KEY_F, "F"),
    G             (GLFW_KEY_G, "G"),
    H             (GLFW_KEY_H, "H"),
    I             (GLFW_KEY_I, "I"),
    J             (GLFW_KEY_J, "J"),
    K             (GLFW_KEY_K, "K"),
    L             (GLFW_KEY_L, "L"),
    M             (GLFW_KEY_M, "M"),
    N             (GLFW_KEY_N, "N"),
    O             (GLFW_KEY_O, "O"),
    P             (GLFW_KEY_P, "P"),
    Q             (GLFW_KEY_Q, "Q"),
    R             (GLFW_KEY_R, "R"),
    S             (GLFW_KEY_S, "S"),
    T             (GLFW_KEY_T, "T"),
    U             (GLFW_KEY_U, "U"),
    V             (GLFW_KEY_V, "V"),
    W             (GLFW_KEY_W, "W"),
    X             (GLFW_KEY_X, "X"),
    Y             (GLFW_KEY_Y, "Y"),
    Z             (GLFW_KEY_Z, "Z"),

    LEFT_BRACKET  (GLFW_KEY_LEFT_BRACKET ), // [
    BACKSLASH     (GLFW_KEY_BACKSLASH    ), // \
    RIGHT_BRACKET (GLFW_KEY_RIGHT_BRACKET), // ]
    GRAVE_ACCENT  (GLFW_KEY_GRAVE_ACCENT ), // `
    WORLD_1       (GLFW_KEY_WORLD_1      ), // <
    WORLD_2       (GLFW_KEY_WORLD_2      , "World 2"),

    ESCAPE        (GLFW_KEY_ESCAPE       , "Escape"      ),
    ENTER         (GLFW_KEY_ENTER        , "Enter"       ),
    TAB           (GLFW_KEY_TAB          , "Tab"         ),
    BACKSPACE     (GLFW_KEY_BACKSPACE    , "Backspace"   ),
    INSERT        (GLFW_KEY_INSERT       , "Insert"      ),
    DELETE        (GLFW_KEY_DELETE       , "Delete"      ),
    RIGHT         (GLFW_KEY_RIGHT        , "Right"       ),
    LEFT          (GLFW_KEY_LEFT         , "Left"        ),
    DOWN          (GLFW_KEY_DOWN         , "Down"        ),
    UP            (GLFW_KEY_UP           , "Up"          ),
    PAGE_UP       (GLFW_KEY_PAGE_UP      , "Page Up"     ),
    PAGE_DOWN     (GLFW_KEY_PAGE_DOWN    , "Page Down"   ),
    HOME          (GLFW_KEY_HOME         , "Home"        ),
    END           (GLFW_KEY_END          , "End"         ),
    CAPS_LOCK     (GLFW_KEY_CAPS_LOCK    , "Caps Lock"   ),
    SCROLL_LOCK   (GLFW_KEY_SCROLL_LOCK  , "Scroll Lock" ),
    NUM_LOCK      (GLFW_KEY_NUM_LOCK     , "Num Lock"    ),
    PRINT_SCREEN  (GLFW_KEY_PRINT_SCREEN , "Print Screen"),
    PAUSE         (GLFW_KEY_PAUSE        , "Pause"       ),

    F1            (GLFW_KEY_F1 ),
    F2            (GLFW_KEY_F2 ),
    F3            (GLFW_KEY_F3 ),
    F4            (GLFW_KEY_F4 ),
    F5            (GLFW_KEY_F5 ),
    F6            (GLFW_KEY_F6 ),
    F7            (GLFW_KEY_F7 ),
    F8            (GLFW_KEY_F8 ),
    F9            (GLFW_KEY_F9 ),
    F10           (GLFW_KEY_F10),
    F11           (GLFW_KEY_F11),
    F12           (GLFW_KEY_F12),
    F13           (GLFW_KEY_F13),
    F14           (GLFW_KEY_F14),
    F15           (GLFW_KEY_F15),
    F16           (GLFW_KEY_F16),
    F17           (GLFW_KEY_F17),
    F18           (GLFW_KEY_F18),
    F19           (GLFW_KEY_F19),
    F20           (GLFW_KEY_F20),
    F21           (GLFW_KEY_F21),
    F22           (GLFW_KEY_F22),
    F23           (GLFW_KEY_F23),
    F24           (GLFW_KEY_F24),
    F25           (GLFW_KEY_F25),

    KP_0          (GLFW_KEY_KP_0       ), // 0
    KP_1          (GLFW_KEY_KP_1       ), // 1
    KP_2          (GLFW_KEY_KP_2       ), // 2
    KP_3          (GLFW_KEY_KP_3       ), // 3
    KP_4          (GLFW_KEY_KP_4       ), // 4
    KP_5          (GLFW_KEY_KP_5       ), // 5
    KP_6          (GLFW_KEY_KP_6       ), // 6
    KP_7          (GLFW_KEY_KP_7       ), // 7
    KP_8          (GLFW_KEY_KP_8       ), // 8
    KP_9          (GLFW_KEY_KP_9       ), // 9
    KP_DECIMAL    (GLFW_KEY_KP_DECIMAL ), // .
    KP_DIVIDE     (GLFW_KEY_KP_DIVIDE  ), // /
    KP_MULTIPLY   (GLFW_KEY_KP_MULTIPLY), // *
    KP_SUBTRACT   (GLFW_KEY_KP_SUBTRACT), // -
    KP_ADD        (GLFW_KEY_KP_ADD     ), // +
    KP_ENTER      (GLFW_KEY_KP_ENTER   , "Enter"),
    KP_EQUAL      (GLFW_KEY_KP_EQUAL   ), // =

    LEFT_SHIFT    (GLFW_KEY_LEFT_SHIFT   , "Left Shift" ),
    LEFT_CONTROL  (GLFW_KEY_LEFT_CONTROL , "Left Ctrl"  ),
    LEFT_ALT      (GLFW_KEY_LEFT_ALT     , "Left Alt"   ),
    LEFT_SUPER    (GLFW_KEY_LEFT_SUPER   , "Left Super" ),
    RIGHT_SHIFT   (GLFW_KEY_RIGHT_SHIFT  , "Right Shift"),
    RIGHT_CONTROL (GLFW_KEY_RIGHT_CONTROL, "Right Ctrl" ),
    RIGHT_ALT     (GLFW_KEY_RIGHT_ALT    , "Right Alt"  ),
    RIGHT_SUPER   (GLFW_KEY_RIGHT_SUPER  , "Right Super"),
    MENU          (GLFW_KEY_MENU         , "Menu"       );


    public final int GLFW;
    private final int scancode;
    private final String glfwName, name;

    Key(int GLFW){
        this.GLFW = GLFW;
        this.scancode = Glfw.getKeyScancode(this);

        // Set Name
        this.glfwName = (scancode != -1) ? Glfw.getKeyName(this, scancode) : null;
        this.name = (glfwName != null) ? glfwName : this.toString();
    }

    Key(int GLFW, String name){
        this.GLFW = GLFW;
        this.scancode = Glfw.getKeyScancode(this);

        // Set Name
        this.glfwName = (scancode != -1) ? Glfw.getKeyName(this, scancode) : null;
        this.name = name;
    }

    public int getScancode(){
        return scancode;
    }

    public String getGlfwName(){
        return glfwName;
    }

    public String getName(){
        return name;
    }


    public boolean isDown(){
        return Pize.keyboard().isDown(this);
    }

    public boolean isPressed(){
        return Pize.keyboard().isPressed(this);
    }

    public boolean isReleased(){
        return Pize.keyboard().isReleased(this);
    }


    private static final Map<Integer, Key> keyMap = new HashMap<>();

    public static Key fromGLFW(int GLFW){
        return keyMap.get(GLFW);
    }

    static{
        keyMap.put(SPACE.GLFW         , SPACE        );
        keyMap.put(APOSTROPHE.GLFW    , APOSTROPHE   );
        keyMap.put(COMMA.GLFW         , COMMA        );
        keyMap.put(MINUS.GLFW         , MINUS        );
        keyMap.put(PERIOD.GLFW        , PERIOD       );
        keyMap.put(SLASH.GLFW         , SLASH        );
        keyMap.put(NUM_0.GLFW         , NUM_0        );
        keyMap.put(NUM_1.GLFW         , NUM_1        );
        keyMap.put(NUM_2.GLFW         , NUM_2        );
        keyMap.put(NUM_3.GLFW         , NUM_3        );
        keyMap.put(NUM_4.GLFW         , NUM_4        );
        keyMap.put(NUM_5.GLFW         , NUM_5        );
        keyMap.put(NUM_6.GLFW         , NUM_6        );
        keyMap.put(NUM_7.GLFW         , NUM_7        );
        keyMap.put(NUM_8.GLFW         , NUM_8        );
        keyMap.put(NUM_9.GLFW         , NUM_9        );
        keyMap.put(SEMICOLON.GLFW     , SEMICOLON    );
        keyMap.put(EQUAL.GLFW         , EQUAL        );

        keyMap.put(A.GLFW             , A            );
        keyMap.put(B.GLFW             , B            );
        keyMap.put(C.GLFW             , C            );
        keyMap.put(D.GLFW             , D            );
        keyMap.put(E.GLFW             , E            );
        keyMap.put(F.GLFW             , F            );
        keyMap.put(G.GLFW             , G            );
        keyMap.put(H.GLFW             , H            );
        keyMap.put(I.GLFW             , I            );
        keyMap.put(J.GLFW             , J            );
        keyMap.put(K.GLFW             , K            );
        keyMap.put(L.GLFW             , L            );
        keyMap.put(M.GLFW             , M            );
        keyMap.put(N.GLFW             , N            );
        keyMap.put(O.GLFW             , O            );
        keyMap.put(P.GLFW             , P            );
        keyMap.put(Q.GLFW             , Q            );
        keyMap.put(R.GLFW             , R            );
        keyMap.put(S.GLFW             , S            );
        keyMap.put(T.GLFW             , T            );
        keyMap.put(U.GLFW             , U            );
        keyMap.put(V.GLFW             , V            );
        keyMap.put(W.GLFW             , W            );
        keyMap.put(X.GLFW             , X            );
        keyMap.put(Y.GLFW             , Y            );
        keyMap.put(Z.GLFW             , Z            );

        keyMap.put(LEFT_BRACKET.GLFW  , LEFT_BRACKET );
        keyMap.put(BACKSLASH.GLFW     , BACKSLASH    );
        keyMap.put(RIGHT_BRACKET.GLFW , RIGHT_BRACKET);
        keyMap.put(GRAVE_ACCENT.GLFW  , GRAVE_ACCENT );
        keyMap.put(WORLD_1.GLFW       , WORLD_1      );
        keyMap.put(WORLD_2.GLFW       , WORLD_2      );

        keyMap.put(ESCAPE.GLFW        , ESCAPE       );
        keyMap.put(ENTER.GLFW         , ENTER        );
        keyMap.put(TAB.GLFW           , TAB          );
        keyMap.put(BACKSPACE.GLFW     , BACKSPACE    );
        keyMap.put(INSERT.GLFW        , INSERT       );
        keyMap.put(DELETE.GLFW        , DELETE       );
        keyMap.put(RIGHT.GLFW         , RIGHT        );
        keyMap.put(LEFT.GLFW          , LEFT         );
        keyMap.put(DOWN.GLFW          , DOWN         );
        keyMap.put(UP.GLFW            , UP           );
        keyMap.put(PAGE_UP.GLFW       , PAGE_UP      );
        keyMap.put(PAGE_DOWN.GLFW     , PAGE_DOWN    );
        keyMap.put(HOME.GLFW          , HOME         );
        keyMap.put(END.GLFW           , END          );
        keyMap.put(CAPS_LOCK.GLFW     , CAPS_LOCK    );
        keyMap.put(SCROLL_LOCK.GLFW   , SCROLL_LOCK  );
        keyMap.put(NUM_LOCK.GLFW      , NUM_LOCK     );
        keyMap.put(PRINT_SCREEN.GLFW  , PRINT_SCREEN );
        keyMap.put(PAUSE.GLFW         , PAUSE        );

        keyMap.put(F1.GLFW            , F1           );
        keyMap.put(F2.GLFW            , F2           );
        keyMap.put(F3.GLFW            , F3           );
        keyMap.put(F4.GLFW            , F4           );
        keyMap.put(F5.GLFW            , F5           );
        keyMap.put(F6.GLFW            , F6           );
        keyMap.put(F7.GLFW            , F7           );
        keyMap.put(F8.GLFW            , F8           );
        keyMap.put(F9.GLFW            , F9           );
        keyMap.put(F10.GLFW           , F10          );
        keyMap.put(F11.GLFW           , F11          );
        keyMap.put(F12.GLFW           , F12          );
        keyMap.put(F13.GLFW           , F13          );
        keyMap.put(F14.GLFW           , F14          );
        keyMap.put(F15.GLFW           , F15          );
        keyMap.put(F16.GLFW           , F16          );
        keyMap.put(F17.GLFW           , F17          );
        keyMap.put(F18.GLFW           , F18          );
        keyMap.put(F19.GLFW           , F19          );
        keyMap.put(F20.GLFW           , F20          );
        keyMap.put(F21.GLFW           , F21          );
        keyMap.put(F22.GLFW           , F22          );
        keyMap.put(F23.GLFW           , F23          );
        keyMap.put(F24.GLFW           , F24          );
        keyMap.put(F25.GLFW           , F25          );

        keyMap.put(KP_0.GLFW          , KP_0         );
        keyMap.put(KP_1.GLFW          , KP_1         );
        keyMap.put(KP_2.GLFW          , KP_2         );
        keyMap.put(KP_3.GLFW          , KP_3         );
        keyMap.put(KP_4.GLFW          , KP_4         );
        keyMap.put(KP_5.GLFW          , KP_5         );
        keyMap.put(KP_6.GLFW          , KP_6         );
        keyMap.put(KP_7.GLFW          , KP_7         );
        keyMap.put(KP_8.GLFW          , KP_8         );
        keyMap.put(KP_9.GLFW          , KP_9         );
        keyMap.put(KP_DECIMAL.GLFW    , KP_DECIMAL   );
        keyMap.put(KP_DIVIDE.GLFW     , KP_DIVIDE    );
        keyMap.put(KP_MULTIPLY.GLFW   , KP_MULTIPLY  );
        keyMap.put(KP_SUBTRACT.GLFW   , KP_SUBTRACT  );
        keyMap.put(KP_ADD.GLFW        , KP_ADD       );
        keyMap.put(KP_ENTER.GLFW      , KP_ENTER     );
        keyMap.put(KP_EQUAL.GLFW      , KP_EQUAL     );

        keyMap.put(LEFT_SHIFT.GLFW    , LEFT_SHIFT   );
        keyMap.put(LEFT_CONTROL.GLFW  , LEFT_CONTROL );
        keyMap.put(LEFT_ALT.GLFW      , LEFT_ALT     );
        keyMap.put(LEFT_SUPER.GLFW    , LEFT_SUPER   );
        keyMap.put(RIGHT_SHIFT.GLFW   , RIGHT_SHIFT  );
        keyMap.put(RIGHT_CONTROL.GLFW , RIGHT_CONTROL);
        keyMap.put(RIGHT_ALT.GLFW     , RIGHT_ALT    );
        keyMap.put(RIGHT_SUPER.GLFW   , RIGHT_SUPER  );
        keyMap.put(MENU.GLFW          , MENU         );
    }

}
