package pize.app;

public abstract class AppAdapter implements Resizable, Disposable{

    public void init(){ }
    
    public void render(){ }
    
    public void update(){ }
    
    public void fixedUpdate(){ }
    
    @Override
    public void resize(int width, int height){ }
    
    @Override
    public void dispose(){ }
    
}
