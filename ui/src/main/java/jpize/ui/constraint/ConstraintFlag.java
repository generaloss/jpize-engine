package jpize.ui.constraint;

public class ConstraintFlag extends Constraint{

    public static final ConstraintFlag match_parent = new ConstraintFlag("match_parent");
    public static final ConstraintFlag wrap_content = new ConstraintFlag("wrap_content");
    public static final ConstraintFlag auto = new ConstraintFlag("auto");
    public static final ConstraintFlag zero = new ConstraintFlag("zero");

    public static ConstraintFlag fromString(String string){
        return switch(string){
            default -> zero;
            case "auto" -> auto;
            case "match_parent" -> match_parent;
            case "wrap_content" -> wrap_content;
        };
    }


    protected final String flag;

    protected ConstraintFlag(String flag){
        this.flag = flag;
    }

    public String getFlag(){
        return flag;
    }

    @Override
    public String toString(){
        return flag;
    }

}
