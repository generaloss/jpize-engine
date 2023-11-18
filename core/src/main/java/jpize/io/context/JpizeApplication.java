package jpize.io.context;

import jpize.util.Disposable;
import jpize.util.Resizable;

public abstract class JpizeApplication implements Resizable, Disposable{

    public void init(){ }

    public void update(){ }

    public void render(){ }

    @Override
    public void resize(int width, int height){ }
    
    @Override
    public void dispose(){ }
    
}
