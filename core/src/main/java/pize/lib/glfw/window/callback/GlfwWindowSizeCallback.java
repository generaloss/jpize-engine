package pize.lib.glfw.window.callback;

@FunctionalInterface
public interface GlfwWindowSizeCallback{

    void invoke(int width, int height);

}
