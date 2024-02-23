package jpize.util.function;

@FunctionalInterface
public interface FloatFunction<R>{

    R apply(double value);

}