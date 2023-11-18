package jpize.gui.constraint;

import java.util.function.DoubleSupplier;

public class RelativeConstraint extends Constraint{
    
    public enum RelativeTo{
        AUTO,
        WIDTH,
        HEIGHT
    }
    
    private RelativeTo relativeTo;

    protected RelativeConstraint(){
        this.relativeTo = RelativeTo.AUTO;
    }
    
    
    public RelativeConstraint setValue(DoubleSupplier valueSupplier){
        supplier = valueSupplier;
        return this;
    }
    
    public RelativeConstraint setValue(double value){
        supplier = ()->value;
        return this;
    }
    

    public RelativeTo getRelativeTo(){
        return relativeTo;
    }
    
    public RelativeConstraint setRelativeTo(RelativeTo relativeTo){
        this.relativeTo = relativeTo;
        return this;
    }
    

    public ConstraintType getType(){
        return ConstraintType.RELATIVE;
    }
    
}
