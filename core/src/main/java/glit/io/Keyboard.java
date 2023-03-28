package glit.io;

import glit.io.glfw.Key;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Keyboard{

    private final boolean[] pressed, down, released;

    public Keyboard(Window window){
        pressed = new boolean[GLFW_KEY_LAST + 1];
        down = new boolean[GLFW_KEY_LAST + 1];
        released = new boolean[GLFW_KEY_LAST + 1];

        glfwSetKeyCallback(window.getId(), (long windowId, int key, int scancode, int action, int mods)->{
            if(key == -1)
                return;

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

}
