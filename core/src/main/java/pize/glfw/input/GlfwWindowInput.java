package pize.glfw.input;

import pize.glfw.window.GlfwWindow;

import static org.lwjgl.glfw.GLFW.glfwGetInputMode;
import static org.lwjgl.glfw.GLFW.glfwSetInputMode;

public abstract class GlfwWindowInput{

    protected final GlfwWindow window;
    protected final long windowID;

    public GlfwWindowInput(GlfwWindow window){
        this.window = window;
        this.windowID = window.getID();
    }

    public GlfwWindow glfwWindow(){
        return window;
    }


    public void setInputMode(GlfwInputMode inputMode, int value){
        glfwSetInputMode(windowID, inputMode.GLFW, value);
    }

    public int getInputMode(GlfwInputMode inputMode){
        return glfwGetInputMode(windowID, inputMode.GLFW);
    }

}
