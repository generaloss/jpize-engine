package pize.io.window;

@FunctionalInterface
public interface SizeCallback{
    
    void invoke(int width, int height);
    
}
