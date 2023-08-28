package pize.io.context;

import pize.util.Disposable;
import pize.util.Resizable;

public abstract class ContextAdapter implements Resizable, Disposable{

    public void init(){ }
    
    public void render(){ }
    
    public void update(){ }
    
    public void fixedUpdate(){ }
    
    @Override
    public void resize(int width, int height){ }
    
    @Override
    public void dispose(){ }
    
}
