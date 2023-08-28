package pize.glfw.mouse;

import pize.glfw.input.GlfwInputMode;
import pize.glfw.input.GlfwAction;
import pize.glfw.input.GlfwWindowInput;
import pize.glfw.key.MBtn;
import pize.glfw.mouse.callback.GlfwCursorEnterCallback;
import pize.glfw.mouse.callback.GlfwCursorPosCallback;
import pize.glfw.mouse.callback.GlfwMouseButtonCallback;
import pize.glfw.mouse.callback.GlfwScrollCallback;
import pize.glfw.mouse.cursor.GlfwCursor;
import pize.glfw.window.GlfwWindow;
import pize.math.vecmath.vector.Vec2f;
import pize.util.Utils;

import static org.lwjgl.glfw.GLFW.*;

public class GlfwMouse extends GlfwWindowInput{

    private static final double[] tmp_x = new double[1];
    private static final double[] tmp_y = new double[1];


    private AutoCloseable cursorEnterCallback, cursorPosCallback, mouseButtonCallback, scrollCallback;

    public GlfwMouse(GlfwWindow window){
        super(window);
    }


    public GlfwAction getButtonState(int glfwMouseButton){
        final int state = glfwGetMouseButton(windowID, glfwMouseButton);
        return GlfwAction.fromGLFW(state);
    }

    public GlfwAction getButtonState(MBtn button){
        return this.getButtonState(button.GLFW);
    }


    public void setCursorEnterCallback(GlfwCursorEnterCallback callback){
        Utils.close(cursorEnterCallback);
        cursorEnterCallback = glfwSetCursorEnterCallback(windowID, (long windowID, boolean entered) -> callback.invoke(entered));
    }

    public void setCursorPosCallback(GlfwCursorPosCallback callback){
        Utils.close(cursorPosCallback);
        cursorPosCallback = glfwSetCursorPosCallback(windowID, (long windowID, double x, double y) -> callback.invoke(x, y));
    }

    public void setMouseButtonCallback(GlfwMouseButtonCallback callback){
        Utils.close(mouseButtonCallback);
        mouseButtonCallback = glfwSetMouseButtonCallback(windowID, (long windowID, int button, int action, int mods) -> callback.invoke(button, action, mods));
    }

    public void setScrollCallback(GlfwScrollCallback callback){
        Utils.close(scrollCallback);
        scrollCallback = glfwSetScrollCallback(windowID, (long windowID, double offsetX, double offsetY) -> callback.invoke(offsetX, offsetY));
    }


    public void setCursorMode(GlfwCursorMode mode){
        super.setInputMode(GlfwInputMode.CURSOR, mode.GLFW);
    }

    public GlfwCursorMode getCursorMode(){
        final int mode = super.getInputMode(GlfwInputMode.CURSOR);
        return GlfwCursorMode.fromGLFW(mode);
    }


    public boolean isRawMotionSupported(){
        return glfwRawMouseMotionSupported();
    }


    public void setPos(int x, int y){
        glfwSetCursorPos(windowID, x, y);
    }


    public float getX(){
        glfwGetCursorPos(windowID, tmp_x, null);
        return (float) tmp_x[0];
    }

    public float getY(){
        glfwGetCursorPos(windowID, null, tmp_y);
        return (float) tmp_y[0];
    }

    public Vec2f getPos(){
        glfwGetCursorPos(windowID, tmp_x, tmp_y);
        return new Vec2f(tmp_x[0], tmp_y[0]);
    }



    public void setCursor(GlfwCursor glfwCursor){
        glfwSetCursor(windowID, glfwCursor != null ? glfwCursor.getID() : 0);
    }

}
