package jpize.glfw.glfw;

import org.lwjgl.glfw.GLFWErrorCallback;
import jpize.glfw.key.Key;
import jpize.util.Utils;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Glfw{

    private static AutoCloseable errorCallback;


    public static void windowHint(GlfwHint hint, int value){
        glfwWindowHint(hint.GLFW, value);
    }

    public static void windowHint(GlfwHint hint, boolean value){
        glfwWindowHint(hint.GLFW, value ? GLFW_TRUE : GLFW_FALSE);
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


    public static String getKeyName(Key key, int scancode){
        return glfwGetKeyName(key.GLFW, scancode);
    }

    public static String getKeyName(Key key){
        System.out.println("Scancode: " + getKeyScancode(key));
        return glfwGetKeyName(key.GLFW, getKeyScancode(key));
    }

    public static int getKeyScancode(Key key){
        return glfwGetKeyScancode(key.GLFW);
    }


    public static String getVersionString(){
        return glfwGetVersionString();
    }


    public static void terminate(){
        Utils.close(errorCallback);
        glfwTerminate();
    }

    public static GlfwPlatform getPlatform(){
        return GlfwPlatform.fromGLFW(glfwGetPlatform());
    }

    public static boolean platformSupported(GlfwPlatform platform){
        return glfwPlatformSupported(platform.GLFW);
    }

    public static void setErrorCallback(GlfwErrorCallback callback){
        Utils.close(errorCallback);
        errorCallback = glfwSetErrorCallback((int error, long description) ->
                callback.invoke(error, GLFWErrorCallback.getDescription(description))
        );
    }

    public static void setErrorCallback(){
        Utils.close(errorCallback);
        errorCallback = glfwSetErrorCallback(GLFWErrorCallback.createPrint());
    }


    public static void pollEvents(){
        glfwPollEvents();
    }

}
