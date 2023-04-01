package glit.graphics.gl;

import glit.context.Disposable;

public abstract class GlObject implements Disposable{
    
    protected final int ID;
    
    public GlObject(int ID){
        this.ID = ID;
    }
    
    
    public int getID(){
        return ID;
    }
    
}
