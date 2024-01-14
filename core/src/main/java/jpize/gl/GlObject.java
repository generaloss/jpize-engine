package jpize.gl;

import jpize.app.Disposable;

public abstract class GlObject implements Disposable{
    
    protected final int ID;
    
    public GlObject(int ID){
        this.ID = ID;
    }


    public final int getID(){
        return ID;
    }

    @Override
    public int hashCode(){
        return ID;
    }
    
}
