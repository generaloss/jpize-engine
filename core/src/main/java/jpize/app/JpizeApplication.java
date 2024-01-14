package jpize.app;

import jpize.Jpize;
import jpize.app.context.Context;

public abstract class JpizeApplication implements Resizable, Disposable{

    public void init(){ }

    public void update(){ }

    public void render(){ }

    @Override
    public void resize(int width, int height){ }
    
    @Override
    public void dispose(){ }


    public final Context context(){
        return Jpize.context();
    }
    
}
