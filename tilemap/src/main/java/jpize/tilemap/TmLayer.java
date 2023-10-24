package jpize.tilemap;

public abstract class TmLayer{

    private final String ID;
    private int order;

    public TmLayer(String ID, int order){
        this.ID = ID;
        this.order = order;
    }

    public String getID(){
        return ID;
    }


    public int getOrder(){
        return order;
    }

    public void setOrder(int order){
        this.order = order;
    }

}
