package pize.io.glfw;

import static org.lwjgl.glfw.GLFW.*;

public enum JKey{
    
    KEY_1    (GLFW_JOYSTICK_1   ),
    KEY_2    (GLFW_JOYSTICK_2   ),
    KEY_3    (GLFW_JOYSTICK_3   ),
    KEY_4    (GLFW_JOYSTICK_4   ),
    KEY_5    (GLFW_JOYSTICK_5   ),
    KEY_6    (GLFW_JOYSTICK_6   ),
    KEY_7    (GLFW_JOYSTICK_7   ),
    KEY_8    (GLFW_JOYSTICK_8   ),
    KEY_9    (GLFW_JOYSTICK_9   ),
    KEY_10   (GLFW_JOYSTICK_10  ),
    KEY_11   (GLFW_JOYSTICK_11  ),
    KEY_12   (GLFW_JOYSTICK_12  ),
    KEY_13   (GLFW_JOYSTICK_13  ),
    KEY_14   (GLFW_JOYSTICK_14  ),
    KEY_15   (GLFW_JOYSTICK_15  ),
    KEY_16   (GLFW_JOYSTICK_16  );
    
    public final int GLFW;
    
    JKey(int GLFW){
        this.GLFW = GLFW;
    }
    
}
