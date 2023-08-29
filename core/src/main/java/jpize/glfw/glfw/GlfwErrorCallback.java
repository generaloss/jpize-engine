package jpize.glfw.glfw;

@FunctionalInterface
public interface GlfwErrorCallback{

    void invoke(int error, String description);

}
