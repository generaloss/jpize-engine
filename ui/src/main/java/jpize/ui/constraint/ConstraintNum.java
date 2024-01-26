package jpize.ui.constraint;

import jpize.util.streamapi.FloatSupplier;

public class ConstraintNum extends Constraint{

    public enum Type{
        PX    ("px"),
        REL_W ("rw"),
        REL_H ("rh"),
        ASPECT("ap");

        public final String literal;
        Type(String literal){
            this.literal = literal;
        }
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

    @Override
    public String toString(){
        return supplier.getAsFloat() + type.literal;
    }

}
