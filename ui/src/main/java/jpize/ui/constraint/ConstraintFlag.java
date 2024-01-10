package jpize.ui.constraint;

public class ConstraintFlag extends Constraint{

    public static final ConstraintFlag match_parent = new ConstraintFlag("match_parent");
    public static final ConstraintFlag wrap_content = new ConstraintFlag("wrap_content");
    public static final ConstraintFlag auto = new ConstraintFlag("auto");
    public static final ConstraintFlag zero = new ConstraintFlag("zero");

    public static ConstraintFlag byName(String name){
        return switch(name){
            default -> zero;
            case "auto" -> auto;
            case "match_parent" -> match_parent;
            case "wrap_content" -> wrap_content;
        };
    }


    protected final String name;

    protected ConstraintFlag(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    @Override
    public String toString(){
        return getName();
    }

}
