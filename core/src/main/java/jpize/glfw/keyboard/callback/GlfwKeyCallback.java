package jpize.glfw.keyboard.callback;

import jpize.glfw.input.GlfwAction;
import jpize.glfw.input.GlfwMods;
import jpize.glfw.key.Key;

@FunctionalInterface
public interface GlfwKeyCallback{
    
    void invoke(Key key, int scancode, GlfwAction action, GlfwMods mods);
    
}
