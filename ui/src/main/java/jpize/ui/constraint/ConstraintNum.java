package jpize.ui.constraint;

import jpize.util.stream.FloatSupplier;

public class ConstraintNum extends Constraint{

    public enum Type{
        PX,
        REL_W,
        REL_H,
        ASPECT;
    }


    private final FloatSupplier supplier;
    private final Type type;


    public ConstraintNum(FloatSupplier supplier, Type type){
        this.supplier = supplier;
        this.type = type;
    }

    public ConstraintNum(float value, Type type){
        this(() -> value, type);
    }

    public ConstraintNum(double value, Type type){
        this((float) value, type);
    }


    public float value(){
        return supplier.getAsFloat();
    }

    public Type type(){
        return type;
    }

}
