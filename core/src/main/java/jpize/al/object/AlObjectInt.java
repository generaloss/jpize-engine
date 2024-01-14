package jpize.al.object;

import jpize.app.Disposable;

public abstract class AlObjectInt implements Disposable{

    protected final int ID;

    public AlObjectInt(int ID){
        this.ID = ID;
    }


    public int getID(){
        return ID;
    }

    @Override
    public int hashCode(){
        return ID;
    }

}
