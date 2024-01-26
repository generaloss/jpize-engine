package jpize.ui.constraint;

import jpize.Jpize;
import jpize.util.streamapi.FloatSupplier;

public class Constr{

    public static final ConstraintFlag match_parent = ConstraintFlag.match_parent;
    public static final ConstraintFlag wrap_content = ConstraintFlag.wrap_content;

    public static final ConstraintFlag auto = ConstraintFlag.auto;
    public static final ConstraintFlag zero = ConstraintFlag.zero;

    public static final ConstraintNum win_width = px(Jpize::getWidth);
    public static final ConstraintNum win_height = px(Jpize::getHeight);


    public static ConstraintNum px(double pixels){
        return new ConstraintNum(pixels, ConstraintNum.Type.PX);
    }

    public static ConstraintNum px(float pixels){
        return new ConstraintNum(pixels, ConstraintNum.Type.PX);
    }

    public static ConstraintNum px(FloatSupplier supplierPixels){
        return new ConstraintNum(supplierPixels, ConstraintNum.Type.PX);
    }


    public static ConstraintNum aspect(double aspect){
        return new ConstraintNum(aspect, ConstraintNum.Type.ASPECT);
    }

    public static ConstraintNum aspect(float aspect){
        return new ConstraintNum(aspect, ConstraintNum.Type.ASPECT);
    }

    public static ConstraintNum aspect(FloatSupplier supplierAspect){
        return new ConstraintNum(supplierAspect, ConstraintNum.Type.ASPECT);
    }


    public static ConstraintNum relw(double factor){
        return new ConstraintNum(factor, ConstraintNum.Type.REL_W);
    }

    public static ConstraintNum relh(double factor){
        return new ConstraintNum(factor, ConstraintNum.Type.REL_H);
    }


    public static ConstraintNum relw(float widthFactor){
        return new ConstraintNum(widthFactor, ConstraintNum.Type.REL_W);
    }

    public static ConstraintNum relh(float heightFactor){
        return new ConstraintNum(heightFactor, ConstraintNum.Type.REL_H);
    }


    public static ConstraintNum relw(FloatSupplier supplierWidthFactor){
        return new ConstraintNum(supplierWidthFactor, ConstraintNum.Type.REL_W);
    }

    public static ConstraintNum relh(FloatSupplier supplierHeightFactor){
        return new ConstraintNum(supplierHeightFactor, ConstraintNum.Type.REL_W);
    }

}
