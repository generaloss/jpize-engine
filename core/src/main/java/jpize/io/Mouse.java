package jpize.io;

import jpize.Jpize;
import jpize.glfw.input.GlfwInputMode;
import jpize.glfw.input.GlfwAction;
import jpize.glfw.key.MBtn;
import jpize.glfw.mouse.GlfwCursorMode;
import jpize.glfw.mouse.GlfwMouse;
import jpize.glfw.mouse.cursor.GlfwCursor;
import jpize.math.vecmath.vector.Vec2f;
import jpize.math.vecmath.vector.Vec2i;

import java.util.Arrays;

import static org.lwjgl.glfw.GLFW.*;

public class Mouse{

    private final GlfwMouse glfwMouse;

    private boolean inWindow;
    private float scrollX, scrollY;
    private float touchDownX, touchDownY;
    private final boolean[] down, released;

    public Mouse(GlfwMouse glfwMouse){
        this.glfwMouse = glfwMouse;

        // Arrays
        this.down = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];
        this.released = new boolean[GLFW_MOUSE_BUTTON_LAST + 1];

        // Callbacks
        glfwMouse.setCursorEnterCallback((boolean entered) -> inWindow = entered);

        glfwMouse.setScrollCallback((double offsetX, double offsetY) -> {
            scrollX += (float) offsetX;
            scrollY += (float) offsetY;
        });

        glfwMouse.setMouseButtonCallback((int button, int action, int mode) -> {
            switch(action){
                case GLFW_PRESS -> {
                    down[button] = true;

                    touchDownX = glfwMouse.getX();
                    touchDownY = glfwMouse.getY();
                }

                case GLFW_RELEASE -> released[button] = true;
            }
        });
    }


    public GlfwMouse glfwMouse(){
        return glfwMouse;
    }


    public void reset(){
        scrollX = 0;
        scrollY = 0;
        Arrays.fill(released, false);
        Arrays.fill(down, false);
    }


    public float getScrollX(){
        return scrollX;
    }

    public float getScroll(){
        return scrollY;
    }


    public boolean isShow(){
        return glfwMouse.getCursorMode() == GlfwCursorMode.NORMAL;
    }

    public void setShow(boolean show){
        glfwMouse.setCursorMode(show ? GlfwCursorMode.NORMAL : GlfwCursorMode.HIDDEN);
    }

    public void setEnabled(boolean enable){
        glfwMouse.setCursorMode(enable ? GlfwCursorMode.NORMAL : GlfwCursorMode.DISABLED);
    }

    public void show(){
        glfwMouse.setCursorMode(GlfwCursorMode.NORMAL);
    }

    public void hide(){
        glfwMouse.setCursorMode(GlfwCursorMode.HIDDEN);
    }

    public void disable(){
        glfwMouse.setCursorMode(GlfwCursorMode.DISABLED);
    }


    public void setStickyButtons(boolean stickyButtons){
        glfwMouse.setInputMode(GlfwInputMode.STICKY_MOUSE_BUTTONS, stickyButtons ? 1 : 0);
    }

    public boolean isStickyButtons(){
        return glfwMouse.getInputMode(GlfwInputMode.STICKY_MOUSE_BUTTONS) == 1;
    }


    public void setRawMotion(boolean rawMotion){
        glfwMouse.setInputMode(GlfwInputMode.RAW_MOUSE_MOTION, rawMotion ? 1 : 0);
    }

    public boolean isRawMotion(){
        return glfwMouse.getInputMode(GlfwInputMode.STICKY_MOUSE_BUTTONS) == 1;
    }

    public boolean isRawMotionSupported(){
        return glfwMouse.isRawMotionSupported();
    }


    public float getX(){
        return glfwMouse.getX();
    }

    public float getY(){
        return glfwMouse.getY();
    }

    public Vec2f getPos(){
        return glfwMouse.getPos();
    }

    public void setPos(int x, int y){
        glfwMouse.setPos(x, y);
    }

    public void toCenter(){
        final Vec2i pos = glfwMouse.glfwWindow().getSize().div(2);
        setPos(pos.x, pos.y);
    }

    public void setCursor(GlfwCursor glfwCursor){
        glfwMouse.setCursor(glfwCursor);
    }


    public boolean isInWindow(){
        return inWindow;
    }
    
    public boolean isInBounds(double x, double y, double width, double height){
        final float cursorX = Jpize.getX();
        final float cursorY = Jpize.getY();
        return !(cursorX < x || cursorY < y || cursorX >= x + width || cursorY >= y + height);
    }

    public float getTouchDownX(){
        return touchDownX;
    }

    public float getTouchDownY(){
        return touchDownY;
    }


    // Buttons
    public boolean isDown(MBtn button){
        return down[button.GLFW];
    }

    public boolean isPressed(MBtn button){
        return glfwMouse.getButtonState(button) != GlfwAction.RELEASE;
    }

    public boolean isReleased(MBtn button){
        return released[button.GLFW];
    }


    public boolean anyDown(MBtn... buttons){
        return Arrays.stream(buttons).anyMatch(this::isDown);
    }

    public boolean anyPressed(MBtn... buttons){
        return Arrays.stream(buttons).anyMatch(this::isPressed);
    }

    public boolean anyReleased(MBtn... buttons){
        return Arrays.stream(buttons).anyMatch(this::isReleased);
    }


    public boolean allDown(MBtn... buttons){
        return Arrays.stream(buttons).allMatch(this::isDown);
    }

    public boolean allPressed(MBtn... buttons){
        return Arrays.stream(buttons).allMatch(this::isPressed);
    }

    public boolean allReleased(MBtn... buttons){
        return Arrays.stream(buttons).allMatch(this::isReleased);
    }


    // Left
    public boolean isLeftDown(){
        return isDown(MBtn.LEFT);
    }

    public boolean isLeftPressed(){
        return isPressed(MBtn.LEFT);
    }

    public boolean isLeftReleased(){
        return isReleased(MBtn.LEFT);
    }

    // Middle
    public boolean isMiddleDown(){
        return isDown(MBtn.MIDDLE);
    }
    
    public boolean isMiddlePressed(){
        return isPressed(MBtn.MIDDLE);
    }
    
    public boolean isMiddleReleased(){
        return isReleased(MBtn.MIDDLE);
    }

    // Right
    public boolean isRightDown(){
        return isDown(MBtn.RIGHT);
    }

    public boolean isRightPressed(){
        return isPressed(MBtn.RIGHT);
    }

    public boolean isRightReleased(){
        return isReleased(MBtn.RIGHT);
    }

}
