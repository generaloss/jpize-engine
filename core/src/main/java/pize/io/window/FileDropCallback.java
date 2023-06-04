package pize.io.window;

@FunctionalInterface
public interface FileDropCallback{
    
    void invoke(int count, String[] names);
    
}
