package pize.lib.glfw.mouse.callback;

@FunctionalInterface
public interface GlfwMouseButtonCallback{

    void invoke(int button, int action, int mods);

}
