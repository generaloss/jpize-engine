package pize.gui.constraint;

import java.util.function.DoubleSupplier;

public class PixelConstraint extends Constraint{
    
    public PixelConstraint setValue(DoubleSupplier valueSupplier){
        supplier = valueSupplier;
        return this;
    }
    
    public PixelConstraint setValue(double value){
        supplier = ()->value;
        return this;
    }
    
    public ConstraintType getType(){
        return ConstraintType.PIXEL;
    }
    
}
