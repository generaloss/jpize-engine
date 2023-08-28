package pize.glfw.keyboard;

import pize.glfw.input.GlfwAction;
import pize.glfw.input.GlfwMods;
import pize.glfw.input.GlfwWindowInput;
import pize.glfw.key.Key;
import pize.glfw.keyboard.callback.GlfwCharCallback;
import pize.glfw.keyboard.callback.GlfwCharModsCallback;
import pize.glfw.keyboard.callback.GlfwKeyCallback;
import pize.glfw.window.GlfwWindow;
import pize.util.Utils;

import static org.lwjgl.glfw.GLFW.*;

public class GlfwKeyboard extends GlfwWindowInput{

    private AutoCloseable keyCallback, charCallback, charModsCallback;

    public GlfwKeyboard(GlfwWindow window){
        super(window);
    }


    public void setKeyCallback(GlfwKeyCallback callback){
        Utils.close(keyCallback);
        final GlfwMods glfwMods = new GlfwMods();
        keyCallback = glfwSetKeyCallback(window.getID(), (long windowID, int key, int scancode, int action, int mods) -> {
            if(key == -1)
                return;

            glfwMods.setMods(mods);
            callback.invoke(Key.fromGLFW(key), scancode, GlfwAction.fromGLFW(action), glfwMods);
        });
    }

    public void setCharCallback(GlfwCharCallback callback){
        Utils.close(charCallback);
        charCallback = glfwSetCharCallback(window.getID(), (long windowID, int character) -> callback.invoke((char) character));
    }

    public void setCharModsCallback(GlfwCharModsCallback callback){
        Utils.close(charModsCallback);
        final GlfwMods glfwMods = new GlfwMods();
        charModsCallback = glfwSetCharModsCallback(window.getID(), (long window, int codepoint, int mods) -> {
            glfwMods.setMods(mods);
            callback.invoke(codepoint, glfwMods);
        });
    }


    public GlfwAction getKeyState(int glfwKey){
        final int state = glfwGetKey(windowID, glfwKey);
        return GlfwAction.fromGLFW(state);
    }

    public GlfwAction getKeyState(Key key){
        return this.getKeyState(key.GLFW);
    }

}
