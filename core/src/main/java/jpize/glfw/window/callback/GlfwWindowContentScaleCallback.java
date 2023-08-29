package jpize.glfw.window.callback;

@FunctionalInterface
public interface GlfwWindowContentScaleCallback{

    void invoke(float contentScaleX, float contentScaleY);

}
