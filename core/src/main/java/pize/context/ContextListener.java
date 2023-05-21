package pize.context;

public interface ContextListener extends Resizable, Disposable{

    void init();
    void render();

}
