package pize.io;

import pize.glfw.input.GlfwInputMode;
import pize.glfw.input.GlfwMods;
import pize.glfw.key.Key;
import pize.glfw.input.GlfwAction;
import pize.glfw.keyboard.GlfwKeyboard;
import pize.glfw.keyboard.callback.GlfwCharCallback;
import pize.glfw.keyboard.callback.GlfwKeyCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard{

    private final GlfwKeyboard glfwKeyboard;

    private final boolean[] down, released;
    private final List<GlfwCharCallback> charCallbackList;
    private final List<GlfwKeyCallback> keyCallbackList;

    public Keyboard(GlfwKeyboard glfwKeyboard){
        this.glfwKeyboard = glfwKeyboard;

        // Arrays
        this.down = new boolean[GLFW_KEY_LAST + 1];
        this.released = new boolean[GLFW_KEY_LAST + 1];

        // Callback Lists
        this.keyCallbackList = new ArrayList<>();
        this.charCallbackList = new ArrayList<>();

        // Callbacks
        glfwKeyboard.setKeyCallback((Key key, int scancode, GlfwAction action, GlfwMods mods) -> {
            switch(action){
                case PRESS   -> down     [key.GLFW] = true;
                case RELEASE -> released [key.GLFW] = true;
            }

            for(GlfwKeyCallback keyCallback: keyCallbackList)
                keyCallback.invoke(key, scancode, action, mods);
        });

        glfwKeyboard.setCharCallback((char character) -> {
            for(GlfwCharCallback charCallback: charCallbackList)
                charCallback.invoke(character);
        });
    }

    public GlfwKeyboard glfwKeyboard(){
        return glfwKeyboard;
    }


    public void setStickyKeys(boolean stickyKeys){
        glfwKeyboard.setInputMode(GlfwInputMode.STICKY_KEYS, stickyKeys ? 1 : 0);
    }

    public boolean isStickyKeys(){
        return glfwKeyboard.getInputMode(GlfwInputMode.STICKY_KEYS) == 1;
    }


    public void lockKeyMods(boolean lock){
        glfwKeyboard.setInputMode(GlfwInputMode.LOCK_KEY_MODS, lock ? 1 : 0);
    }

    public boolean isLockKeyMods(){
        return glfwKeyboard.getInputMode(GlfwInputMode.LOCK_KEY_MODS) == 1;
    }


    public void reset(){
        Arrays.fill(released, false);
        Arrays.fill(down, false);
    }


    public boolean isDown(Key key){
        return down[key.GLFW];
    }

    public boolean isPressed(Key key){
        return glfwKeyboard.getKeyState(key) != GlfwAction.RELEASE;
    }

    public boolean isReleased(Key key){
        return released[key.GLFW];
    }


    public boolean anyDown(Key... keys){
        return Arrays.stream(keys).anyMatch(this::isDown);
    }

    public boolean anyPressed(Key... keys){
        return Arrays.stream(keys).anyMatch(this::isPressed);
    }

    public boolean anyReleased(Key... keys){
        return Arrays.stream(keys).anyMatch(this::isReleased);
    }


    public boolean allDown(Key... keys){
        return Arrays.stream(keys).allMatch(this::isDown);
    }

    public boolean allPressed(Key... keys){
        return Arrays.stream(keys).allMatch(this::isPressed);
    }

    public boolean allReleased(Key... keys){
        return Arrays.stream(keys).allMatch(this::isReleased);
    }


    public void addKeyCallback(GlfwKeyCallback keyCallback){
        keyCallbackList.add(keyCallback);
    }

    public void removeKeyCallback(GlfwKeyCallback keyCallback){
        keyCallbackList.remove(keyCallback);
    }


    public void addCharCallback(GlfwCharCallback charCallback){
        charCallbackList.add(charCallback);
    }
    
    public void removeCharCallback(GlfwCharCallback charCallback){
        charCallbackList.remove(charCallback);
    }

}
