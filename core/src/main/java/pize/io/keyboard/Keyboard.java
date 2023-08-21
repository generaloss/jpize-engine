package pize.io.keyboard;

import pize.io.window.Window;
import pize.io.key.Key;
import pize.io.key.KeyState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard{

    private final boolean[] pressed, down, released;
    private final List<CharCallback> charCallbackList;
    private final List<KeyCallback> keyCallbackList;

    public Keyboard(Window window){
        this.pressed = new boolean[GLFW_KEY_LAST + 1];
        this.down = new boolean[GLFW_KEY_LAST + 1];
        this.released = new boolean[GLFW_KEY_LAST + 1];
        
        this.keyCallbackList = new ArrayList<>();
        
        glfwSetKeyCallback(window.getID(), (long windowID, int key, int scancode, int action, int mods)->{
            if(key == -1)
                return;
            
            for(KeyCallback keyCallback: keyCallbackList)
                keyCallback.invoke(key, KeyState.values()[action]);

            switch(action){
                case GLFW_PRESS -> {
                    down[key] = true;
                    pressed[key] = true;
                }
                case GLFW_RELEASE -> {
                    released[key] = true;
                    pressed[key] = false;
                }
            }
        });
        
        this.charCallbackList = new ArrayList<>();
        
        glfwSetCharCallback(window.getID(), (long windowID, int character)->{
            for(CharCallback charCallback: charCallbackList)
                charCallback.invoke((char) character);
        });
    }

    public void reset(){
        Arrays.fill(released, false);
        Arrays.fill(down, false);
    }

    public boolean isDown(Key key){
        return down[key.GLFW];
    }

    public boolean isPressed(Key key){
        return pressed[key.GLFW];
    }

    public boolean isReleased(Key key){
        return released[key.GLFW];
    }


    public boolean isDown(Key... keys){
        for(Key key: keys)
            if(down[key.GLFW])
                return true;
        return false;
    }

    public boolean isPressed(Key... keys){
        for(Key key: keys)
            if(pressed[key.GLFW])
                return true;
        return false;
    }

    public boolean isReleased(Key... keys){
        for(Key key: keys)
            if(released[key.GLFW])
                return true;
        return false;
    }


    public boolean isDownAll(Key... keys){
        for(Key key: keys)
            if(!down[key.GLFW])
                return false;
        return true;
    }

    public boolean isPressedAll(Key... keys){
        for(Key key: keys)
            if(!pressed[key.GLFW])
                return false;
        return true;
    }

    public boolean isReleasedAll(Key... keys){
        for(Key key: keys)
            if(!released[key.GLFW])
                return false;
        return true;
    }
    
    
    public void addKeyCallback(KeyCallback keyCallback){
        keyCallbackList.add(keyCallback);
    }
    
    public void removeKeyCallback(KeyCallback keyCallback){
        keyCallbackList.remove(keyCallback);
    }
    
    public void addCharCallback(CharCallback charCallback){
        charCallbackList.add(charCallback);
    }
    
    public void removeCharCallback(CharCallback charCallback){
        charCallbackList.remove(charCallback);
    }

}
