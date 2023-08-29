package pize.gui.constraint;

import pize.Jize;

import java.util.function.DoubleSupplier;

public abstract class Constraint{
    
    public static PixelConstraint zero(){
        return pixel(0);
    }
    
    public static PixelConstraint scrWidth(){
        return pixel(Jize::getWidth);
    }
    
    public static PixelConstraint scrHeight(){
        return pixel(Jize::getHeight);
    }
    
    public static RelativeConstraint matchParent(){
        return relative(1);
    }


    public static PixelConstraint pixel(double pixels){
        return new PixelConstraint()
            .setValue(pixels);
    }
    
    public static PixelConstraint pixel(DoubleSupplier pixelsSupplier){
        return new PixelConstraint()
            .setValue(pixelsSupplier);
    }
    
    
    public static AspectConstraint aspect(double aspect){
        return new AspectConstraint()
            .setValue(aspect);
    }
    
    public static AspectConstraint aspect(DoubleSupplier aspectSupplier){
        return new AspectConstraint()
            .setValue(aspectSupplier);
    }
    
    
    public static RelativeConstraint relative(double percentage){
        return new RelativeConstraint()
            .setValue(percentage)
            .setRelativeTo(RelativeConstraint.RelativeTo.AUTO);
    }
    
    public static RelativeConstraint relativeToWidth(double percentage){
        return new RelativeConstraint()
            .setValue(percentage)
            .setRelativeTo(RelativeConstraint.RelativeTo.WIDTH);
    }
    
    public static RelativeConstraint relativeToHeight(double percentage){
        return new RelativeConstraint()
            .setValue(percentage)
            .setRelativeTo(RelativeConstraint.RelativeTo.HEIGHT);
    }
    
    
    public static RelativeConstraint relative(DoubleSupplier percentageSupplier){
        return new RelativeConstraint()
            .setValue(percentageSupplier)
            .setRelativeTo(RelativeConstraint.RelativeTo.AUTO);
    }
    
    public static RelativeConstraint relativeToWidth(DoubleSupplier percentageSupplier){
        return new RelativeConstraint()
            .setValue(percentageSupplier)
            .setRelativeTo(RelativeConstraint.RelativeTo.WIDTH);
    }
    
    public static RelativeConstraint relativeToHeight(DoubleSupplier percentageSupplier){
        return new RelativeConstraint()
            .setValue(percentageSupplier)
            .setRelativeTo(RelativeConstraint.RelativeTo.HEIGHT);
    }


    protected DoubleSupplier supplier;
    
    public float getValue(){
        return (float) supplier.getAsDouble();
    }
    
    public Constraint setValue(DoubleSupplier valueSupplier){
        supplier = valueSupplier;
        return this;
    }
    
    public Constraint setValue(double value){
        supplier = ()->value;
        return this;
    }
    
    public abstract ConstraintType getType();

}
