package jpize.glfw.window.callback;

@FunctionalInterface
public interface GlfwWindowPosCallback{

    void invoke(int x, int y);

}