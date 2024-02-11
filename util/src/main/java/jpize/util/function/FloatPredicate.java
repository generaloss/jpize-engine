package jpize.util.function;

import java.util.Objects;

@FunctionalInterface
public interface FloatPredicate{

    boolean test(float value);

    default FloatPredicate and(FloatPredicate other){
        Objects.requireNonNull(other);
        return (value) -> this.test(value) && other.test(value);
    }

    default FloatPredicate negate(){
        return (value) -> !this.test(value);
    }

    default FloatPredicate or(FloatPredicate other){
        Objects.requireNonNull(other);
        return (value) -> this.test(value) || other.test(value);
    }

}

