package pize.lib.glfw.mouse.callback;

@FunctionalInterface
public interface GlfwScrollCallback{

    void invoke(double offsetX, double offsetY);

}
