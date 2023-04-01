package glit.gui.constraint;

import glit.Pize;

import java.util.function.DoubleSupplier;

public interface Constraint{

    PixelConstraint zero = pixel(0);
    PixelConstraint scr_width = pixel(Pize::getWidth);
    PixelConstraint scr_height = pixel(Pize::getHeight);
    RelativeConstraint match_parent = relative(1);


    static PixelConstraint pixel(double pixels){
        return new PixelConstraint(pixels);
    }

    static PixelConstraint pixel(DoubleSupplier pixelsSupplier){
        return new PixelConstraint(pixelsSupplier);
    }


    static AspectConstraint aspect(double aspect){
        return new AspectConstraint(aspect);
    }

    static AspectConstraint aspect(DoubleSupplier aspectSupplier){
        return new AspectConstraint(aspectSupplier);
    }


    static RelativeConstraint relative(double percentage){
        return new RelativeConstraint(percentage, RelativeConstraint.RelativeTo.AUTO);
    }

    static RelativeConstraint relativeToWidth(double percentage){
        return new RelativeConstraint(percentage, RelativeConstraint.RelativeTo.WIDTH);
    }

    static RelativeConstraint relativeToHeight(double percentage){
        return new RelativeConstraint(percentage, RelativeConstraint.RelativeTo.HEIGHT);
    }


    static RelativeConstraint relative(DoubleSupplier percentageSupplier){
        return new RelativeConstraint(percentageSupplier, RelativeConstraint.RelativeTo.AUTO);
    }

    static RelativeConstraint relativeToWidth(DoubleSupplier percentageSupplier){
        return new RelativeConstraint(percentageSupplier, RelativeConstraint.RelativeTo.WIDTH);
    }

    static RelativeConstraint relativeToHeight(DoubleSupplier percentageSupplier){
        return new RelativeConstraint(percentageSupplier, RelativeConstraint.RelativeTo.HEIGHT);
    }


    Number getValue();

    ConstraintType getType();

}
