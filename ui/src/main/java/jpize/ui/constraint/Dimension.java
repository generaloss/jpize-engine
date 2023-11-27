package jpize.ui.constraint;

public class Dimension{

    public Constraint x, y;

    public Dimension(Constraint x, Constraint y){
        this.x = x;
        this.y = y;
    }

    public Dimension(Constraint xy){
        this.x = xy;
        this.y = xy;
    }

    public Dimension(){
        this(Constr.auto);
    }


    public void set(Constraint x, Constraint y){
        this.x = x;
        this.y = y;
    }

    public void set(Constraint xy){
        this.x = xy;
        this.y = xy;
    }

    public void setX(Constraint x){
        this.x = x;
    }

    public void setY(Constraint y){
        this.y = y;
    }

}
