package pize.glfw.window.callback;

@FunctionalInterface
public interface GlfwWindowFocusCallback{

    void invoke(boolean focus);

}