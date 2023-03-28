package glit.graphics.gl;

import glit.context.Disposable;

public abstract class GlObject implements Disposable{
    
    protected final int TARGET;
    protected int ID;
    
    public GlObject(int TARGET){
        this.TARGET = TARGET;
    }
    
    
    public int getID(){
        return ID;
    }
    
}
