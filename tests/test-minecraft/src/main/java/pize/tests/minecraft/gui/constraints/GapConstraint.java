package pize.tests.minecraft.gui.constraints;

import pize.gui.constraint.PixelConstraint;

import java.util.function.DoubleSupplier;

public class GapConstraint extends PixelConstraint{
    
    public static GapConstraint gap(double pixels){
        return (GapConstraint) new GapConstraint().setValue(pixels);
    }
    
    public static GapConstraint gap(DoubleSupplier pixelsSupplier){
        return (GapConstraint) new GapConstraint().setValue(pixelsSupplier);
    }
    
}
