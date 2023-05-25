package pize.gui.constraint;

import java.util.function.DoubleSupplier;

public class AspectConstraint extends Constraint{
    
    public AspectConstraint setValue(DoubleSupplier valueSupplier){
        supplier = valueSupplier;
        return this;
    }
    
    public AspectConstraint setValue(double value){
        supplier = ()->value;
        return this;
    }
    
    @Override
    public ConstraintType getType(){
        return ConstraintType.ASPECT;
    }

}
