package jpize.ui.gravity;

public class Gravity{

    public static final int NONE   =  0;
    public static final int TOP    =  1;
    public static final int LEFT   =  2;
    public static final int BOTTOM =  4;
    public static final int RIGHT  =  8;
    public static final int CENTER = 16;


    private int gravity;

    public Gravity(int gravity){
        this.gravity = gravity;
    }

    public Gravity(){
        this(0);
    }


    public void set(int gravity){
        this.gravity = gravity;
    }

    public void reset(){
        gravity = 0;
    }

    public void add(int gravity){
        this.gravity |= gravity;
    }

    public void remove(int gravity){
        this.gravity &= ~gravity;
    }

    public boolean has(int gravity){
        return (this.gravity & gravity) != 0;
    }

    public boolean is(int gravity){
        return this.gravity == gravity;
    }

}