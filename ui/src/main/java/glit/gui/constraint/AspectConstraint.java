package glit.gui.constraint;

import java.util.function.DoubleSupplier;

public class AspectConstraint implements Constraint{

    private final DoubleSupplier supplier;

    protected AspectConstraint(DoubleSupplier aspectSupplier){
        supplier = aspectSupplier;
    }

    protected AspectConstraint(double aspect){
        this(()->aspect);
    }


    public float aspect(){
        return (float) supplier.getAsDouble();
    }

    public ConstraintType getType(){
        return ConstraintType.ASPECT;
    }

    @Override
    public Number getValue(){
        return supplier.getAsDouble();
    }

}
