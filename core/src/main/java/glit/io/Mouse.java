package glit.io;

import glit.io.glfw.Key;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse{

    private final long windowId;
    private boolean inWindow, visible;
    private int scroll, grabX, grabY;
    private final boolean[] down, pressed, released;

    public Mouse(Window window){
        this.windowId = window.getId();
        visible = true;

        down = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];
        pressed = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];
        released = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];

        glfwSetCursorEnterCallback(windowId, (long windowHandle, boolean entered)->{
            inWindow = entered;
        });

        glfwSetScrollCallback(windowId, (long windowHandle, double x, double y)->{
            scroll += y;
        });

        glfwSetMouseButtonCallback(windowId, (long windowHandle, int button, int action, int mode)->{
            if(action == GLFW_PRESS){
                down[button] = true;
                pressed[button] = true;

                grabX = getX();
                grabY = getY();
            }else if(action == GLFW_RELEASE){
                released[button] = true;
                pressed[button] = false;
            }
        });
    }

    public void reset(){
        scroll = 0;
        Arrays.fill(released, false);
        Arrays.fill(down, false);
    }

    public void show(boolean show){
        if(show == visible)
            return;
        glfwSetInputMode(windowId, GLFW_CURSOR, show ? GLFW_CURSOR_NORMAL : GLFW_CURSOR_HIDDEN);
        visible = show;
    }

    public boolean isVisible(){
        return visible;
    }

    public void setPos(int x, int y){
        glfwSetCursorPos(windowId, x, y);
    }

    public void setPosCenter(Window window){
        glfwSetCursorPos(windowId, window.getWidth() * 0.5, window.getHeight() * 0.5);
    }

    public int getScroll(){
        return scroll;
    }

    public long getWindowId(){
        return windowId;
    }

    public boolean inWindow(){
        return inWindow;
    }

    public int[] getPos(){
        double[] x = new double[1];
        double[] y = new double[1];
        glfwGetCursorPos(windowId, x, y);

        return new int[]{
            (int) x[0],
            (int) y[0]
        };
    }

    public int getX(){
        double[] x = new double[1];
        glfwGetCursorPos(windowId, x, null);

        return (int) x[0];
    }

    public int getY(){
        double[] y = new double[1];
        glfwGetCursorPos(windowId, null, y);

        return (int) y[0];
    }

    public int getGrabX(){
        return grabX;
    }

    public int getGrabY(){
        return grabY;
    }

    public boolean isLeftDown(){
        return down[Key.MOUSE_LEFT.GLFW];
    }

    public boolean isLeftPressed(){
        return pressed[Key.MOUSE_LEFT.GLFW];
    }

    public boolean isLeftReleased(){
        return released[Key.MOUSE_LEFT.GLFW];
    }

    public boolean isRightDown(){
        return down[Key.MOUSE_RIGHT.GLFW];
    }

    public boolean isRightPressed(){
        return pressed[Key.MOUSE_RIGHT.GLFW];
    }

    public boolean isRightReleased(){
        return released[Key.MOUSE_RIGHT.GLFW];
    }

    public boolean isButtonDown(Key button){
        return down[button.GLFW];
    }

    public boolean isButtonPressed(Key button){
        return pressed[button.GLFW];
    }

    public boolean isButtonReleased(Key button){
        return released[button.GLFW];
    }


    public int getScrollX(Keyboard keyboard){
        return keyboard.isPressed(Key.LEFT_SHIFT, Key.RIGHT_SHIFT) ? scroll : 0;
    }

    public int getScrollY(Keyboard keyboard){
        return keyboard.isPressed(Key.LEFT_SHIFT, Key.RIGHT_SHIFT) ? 0 : scroll;
    }

}
