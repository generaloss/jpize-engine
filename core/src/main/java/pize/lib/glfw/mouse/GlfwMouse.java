package pize.lib.glfw.mouse;

import pize.io.key.Key;
import pize.io.key.KeyState;
import pize.lib.glfw.glfwenum.GlfwInputMode;
import pize.lib.glfw.mouse.callback.GlfwCursorEnterCallback;
import pize.lib.glfw.mouse.callback.GlfwCursorPosCallback;
import pize.lib.glfw.mouse.callback.GlfwMouseButtonCallback;
import pize.lib.glfw.mouse.callback.GlfwScrollCallback;
import pize.lib.glfw.window.GlfwWindow;
import pize.math.vecmath.vector.Vec2f;
import pize.math.vecmath.vector.Vec2i;

import static org.lwjgl.glfw.GLFW.*;

public class GlfwMouse{

    protected final GlfwWindow window;
    protected final long windowID;
    private final double[] tmp_x = new double[1];
    private final double[] tmp_y = new double[1];

    public GlfwMouse(GlfwWindow window){
        this.window = window;
        this.windowID = window.getID();
    }


    public void setCursorEnterCallback(GlfwCursorEnterCallback callback){
        glfwSetCursorEnterCallback(windowID, (long windowID, boolean entered) -> callback.invoke(entered));
    }

    public void setCursorPosCallback(GlfwCursorPosCallback callback){
        glfwSetCursorPosCallback(windowID, (long windowID, double x, double y) -> callback.invoke(x, y));
    }

    public void setMouseButtonCallback(GlfwMouseButtonCallback callback){
        glfwSetMouseButtonCallback(windowID, (long windowID, int button, int action, int mods) -> callback.invoke(button, action, mods));
    }

    public void setScrollCallback(GlfwScrollCallback callback){
        glfwSetScrollCallback(windowID, (long windowID, double offsetX, double offsetY) -> callback.invoke(offsetX, offsetY));
    }


    public void setMode(GlfwCursorMode mode){
        window.setInputMode(GlfwInputMode.CURSOR, mode.GLFW);
    }

    public GlfwCursorMode getMode(){
        final int mode = window.getInputMode(GlfwInputMode.CURSOR);
        return GlfwCursorMode.fromGLFW(mode);
    }


    public KeyState getMouseButton(Key key){
        final int state = glfwGetMouseButton(windowID, key.GLFW);
        return KeyState.fromGLFW(state);
    }


    public void setPos(int x, int y){
        glfwSetCursorPos(windowID, x, y);
    }

    public void toCenter(){
        final Vec2i pos = window.getSize().div(2);
        setPos(pos.x, pos.y);
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

}
