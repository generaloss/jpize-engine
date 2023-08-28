package pize.glfw.keyboard.callback;

import pize.glfw.input.GlfwMods;

@FunctionalInterface
public interface GlfwCharModsCallback{

    void invoke(int codepoint, GlfwMods mods);

}
