package pize.lib.glfw;

import pize.io.key.Key;
import pize.lib.glfw.glfwenum.GlfwHint;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Glfw{

    public static void windowHint(GlfwHint hint, int value){
        glfwWindowHint(hint.GLFW, value);
    }

    public static void windowHint(GlfwHint hint, ByteBuffer value){
        glfwWindowHintString(hint.GLFW, value);
    }

    public static void windowHint(GlfwHint hint, CharSequence value){
        glfwWindowHintString(hint.GLFW, value);
    }

    public static void defaultWindowHints(){
        glfwDefaultWindowHints();
    }


    public static void swapInterval(int interval){
        glfwSwapInterval(interval);
    }

    public static void setVsync(boolean vSync){
        swapInterval(vSync ? 1 : 0);
    }

    public static boolean rawMouseMotionSupported(){
        return glfwRawMouseMotionSupported();
    }

    public static String getKeyName(int key, int scancode){
        return glfwGetKeyName(key, scancode);
    }

    public static String getKeyName(Key key){
        return glfwGetKeyName(key.GLFW, getKeyScancode(key));
    }

    public static int getKeyScancode(Key key){
        return glfwGetKeyScancode(key.GLFW);
    }

}
