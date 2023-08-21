package pize.io.mouse;

import pize.Pize;
import pize.io.key.Key;
import pize.io.window.Window;
import pize.lib.glfw.mouse.GlfwCursorMode;
import pize.lib.glfw.mouse.GlfwMouse;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse extends GlfwMouse{

    private boolean inWindow, visible;
    private float scrollX, scrollY, touchDownX, touchDownY;
    private final boolean[] down, pressed, released;

    public Mouse(Window window){
        super(window);
        visible = true;

        down = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];
        pressed = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];
        released = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];

        super.setCursorEnterCallback((boolean entered) -> inWindow = entered);

        super.setScrollCallback((double x, double y) -> {
            scrollX += (float) x;
            scrollY += (float) y;
        });

        super.setMouseButtonCallback((int button, int action, int mode) -> {
            if(action == GLFW_PRESS){
                down[button] = true;
                pressed[button] = true;

                touchDownX = super.getX();
                touchDownY = super.getY();
            }else if(action == GLFW_RELEASE){
                released[button] = true;
                pressed[button] = false;
            }
        });
    }

    public void reset(){
        scrollX = 0;
        scrollY = 0;
        Arrays.fill(released, false);
        Arrays.fill(down, false);
    }

    public void setShow(boolean show){
        if(show == visible)
            return;

        super.setMode(show ? GlfwCursorMode.NORMAL : GlfwCursorMode.HIDDEN);
        visible = show;
    }

    public boolean isShow(){
        return visible;
    }

    public float getScrollX(){
        return scrollX;
    }

    public float getScrollY(){
        return scrollY;
    }

    public long getWindowID(){
        return windowID;
    }

    public boolean isInWindow(){
        return inWindow;
    }
    
    public boolean isInBounds(double x, double y, double width, double height){
        final float cursorX = Pize.getX();
        final float cursorY = Pize.getY();
        
        return !(cursorX < x || cursorY < y || cursorX >= x + width || cursorY >= y + height);
    }

    public float getTouchDownX(){
        return touchDownX;
    }

    public float getTouchDownY(){
        return touchDownY;
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
    
    public boolean isMiddleDown(){
        return down[Key.MOUSE_MIDDLE.GLFW];
    }
    
    public boolean isMiddlePressed(){
        return pressed[Key.MOUSE_MIDDLE.GLFW];
    }
    
    public boolean isMiddleReleased(){
        return released[Key.MOUSE_MIDDLE.GLFW];
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

}
