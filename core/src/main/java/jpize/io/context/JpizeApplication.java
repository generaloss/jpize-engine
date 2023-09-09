package jpize.io.context;

import jpize.util.Disposable;
import jpize.util.Resizable;

public abstract class JpizeApplication implements Resizable, Disposable{

    public void init(){ }
    
    public void render(){ }
    
    public void update(){ }
    
    public void fixedUpdate(){ }
    
    @Override
    public void resize(int width, int height){ }
    
    @Override
    public void dispose(){ }
    
}
