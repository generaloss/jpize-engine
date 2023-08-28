package pize.glfw.keyboard.callback;

import pize.glfw.input.GlfwAction;
import pize.glfw.input.GlfwMods;
import pize.glfw.key.Key;

@FunctionalInterface
public interface GlfwKeyCallback{
    
    void invoke(Key key, int scancode, GlfwAction action, GlfwMods mods);
    
}
