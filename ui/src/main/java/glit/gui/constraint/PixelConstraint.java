package glit.gui.constraint;

import java.util.function.DoubleSupplier;

public class PixelConstraint implements Constraint{

    private final DoubleSupplier supplier;

    protected PixelConstraint(DoubleSupplier pixelsSupplier){
        supplier = pixelsSupplier;
    }

    protected PixelConstraint(double pixels){
        this(()->pixels);
    }


    public float pixels(){
        return (float) supplier.getAsDouble();
    }

    public ConstraintType getType(){
        return ConstraintType.PIXEL;
    }

    @Override
    public Number getValue(){
        return supplier.getAsDouble();
    }

}
