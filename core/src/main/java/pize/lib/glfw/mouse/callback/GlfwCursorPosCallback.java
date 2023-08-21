package pize.lib.glfw.mouse.callback;

@FunctionalInterface
public interface GlfwCursorPosCallback{

    void invoke(double x, double y);

}
