package pize.lib.glfw.window.callback;

@FunctionalInterface
public interface GlfwWindowDropCallback{
    
    void invoke(int count, String[] names);
    
}
