package util.Range;

import java.util.function.Predicate;


@FunctionalInterface
public interface CollisionPredicate extends Predicate<Range> {
    boolean test(Range pacmanX, Range pacmanY,boolean movingRight,boolean movingLeft,boolean movingUp, boolean movingDown);

    @Override
    default boolean test(Range blockRange) {
        throw new UnsupportedOperationException("Use the full method with movement directions.");
    }
}
