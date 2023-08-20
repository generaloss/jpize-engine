package pize.lib.gl;

import pize.app.Disposable;

public abstract class GlObject implements Disposable{
    
    protected final int ID;
    
    public GlObject(int ID){
        this.ID = ID;
    }

    public final int getID(){
        return ID;
    }
    
}
