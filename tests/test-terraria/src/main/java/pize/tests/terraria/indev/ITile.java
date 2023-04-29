package pize.tests.terraria.indev;

public abstract class ITile{

    private final String id;

    public ITile(String id){
        this.id = id;
    }


    public String get(){
        return id;
    }

}
