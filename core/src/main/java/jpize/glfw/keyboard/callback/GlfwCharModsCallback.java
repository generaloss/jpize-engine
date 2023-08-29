package jpize.glfw.keyboard.callback;

import jpize.glfw.input.GlfwMods;

@FunctionalInterface
public interface GlfwCharModsCallback{

    void invoke(int codepoint, GlfwMods mods);

}
