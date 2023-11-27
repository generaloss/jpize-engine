package jpize.ui.constraint;

public abstract class Constraint implements Cloneable{

    public boolean isFlag(String flag){
        if(this instanceof ConstraintFlag constraintFlag)
            return constraintFlag.getFlag().equals(flag);
        return false;
    }

    public boolean isFlagAuto(){
        return isFlag(ConstraintFlag.auto.getFlag());
    }

    public boolean isFlagWrapContent(){
        return isFlag(ConstraintFlag.wrap_content.getFlag());
    }

    public float numValue(){
        if(this instanceof ConstraintNum constraintNum)
            return constraintNum.value();
        return 0;
    }


    public Constraint copy(){
        try{
            return (Constraint) super.clone();
        }catch(CloneNotSupportedException e){
            throw new AssertionError(e);
        }
    }

}
