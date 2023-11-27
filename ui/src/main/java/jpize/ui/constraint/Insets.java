package jpize.ui.constraint;

public class Insets{

    public Constraint top, left, bottom, right;

    public Insets(Constraint top, Constraint left, Constraint bottom, Constraint right){
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public Insets(Constraint all){
        this.top = all;
        this.left = all;
        this.bottom = all;
        this.right = all;
    }

    public Insets(){
        this(Constr.auto);
    }


    public void set(Constraint top, Constraint left, Constraint bottom, Constraint right){
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public void set(Constraint all){
        this.top = all;
        this.left = all;
        this.bottom = all;
        this.right = all;
    }

    public void setTop(Constraint top){
        this.top = top;
    }

    public void setLeft(Constraint left){
        this.left = left;
    }

    public void setBottom(Constraint bottom){
        this.bottom = bottom;
    }

    public void setRight(Constraint right){
        this.right = right;
    }

}
