package pize.glfw.key;

import pize.Pize;

import static org.lwjgl.glfw.GLFW.*;

public enum MBtn{

    LEFT     (GLFW_MOUSE_BUTTON_LEFT  , "Left Button"  ),
    RIGHT    (GLFW_MOUSE_BUTTON_RIGHT , "Right Button" ),
    MIDDLE   (GLFW_MOUSE_BUTTON_MIDDLE, "Middle Button"),

    BUTTON_4 (GLFW_MOUSE_BUTTON_4     , "Button 4"),
    BUTTON_5 (GLFW_MOUSE_BUTTON_5     , "Button 5"),
    BUTTON_6 (GLFW_MOUSE_BUTTON_6     , "Button 6"),
    BUTTON_7 (GLFW_MOUSE_BUTTON_7     , "Button 7"),
    BUTTON_8 (GLFW_MOUSE_BUTTON_8     , "Button 8");


    public final int GLFW;
    public final String name;

    MBtn(int GLFW, String name){
        this.GLFW = GLFW;
        this.name = name;
    }

    public String getName(){
        return name;
    }


    public boolean isDown(){
        return Pize.mouse().isDown(this);
    }

    public boolean isPressed(){
        return Pize.mouse().isPressed(this);
    }

    public boolean isReleased(){
        return Pize.mouse().isReleased(this);
    }

}
