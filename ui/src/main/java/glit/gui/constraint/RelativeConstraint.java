package glit.gui.constraint;

import java.util.function.DoubleSupplier;

public class RelativeConstraint implements Constraint{

    private final DoubleSupplier supplier;
    private final RelativeTo relativeTo;

    protected RelativeConstraint(DoubleSupplier percentageSupplier, RelativeTo relativeTo){
        supplier = percentageSupplier;
        this.relativeTo = relativeTo;
    }

    protected RelativeConstraint(double percentage, RelativeTo relativeTo){
        this(()->percentage, relativeTo);
    }


    public float percentage(){
        return (float) supplier.getAsDouble();
    }

    public RelativeTo relativeTo(){
        return relativeTo;
    }

    public ConstraintType getType(){
        return ConstraintType.RELATIVE;
    }

    @Override
    public Number getValue(){
        return supplier.getAsDouble();
    }


    public enum RelativeTo{
        AUTO,
        WIDTH,
        HEIGHT
    }

}
