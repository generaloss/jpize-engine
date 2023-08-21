package pize.io.key;

public enum KeyState{
    
    RELEASE,
    PRESS,
    REPEAT;

    public static KeyState fromGLFW(int GLFW){
        return values()[GLFW];
    }
    
}
