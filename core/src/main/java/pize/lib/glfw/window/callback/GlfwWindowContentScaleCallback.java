package pize.lib.glfw.window.callback;

@FunctionalInterface
public interface GlfwWindowContentScaleCallback{

    void invoke(float contentScaleX, float contentScaleY);

}
