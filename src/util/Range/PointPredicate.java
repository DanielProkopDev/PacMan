package util.Range;

@FunctionalInterface
public interface PointPredicate {
    boolean test(Range pacmanX, Range pacmanY, boolean movingRight, boolean movingLeft, boolean movingUp, boolean movingDown);

    default boolean test(Range blockRange) {
        throw new UnsupportedOperationException("Use the full method with movement directions.");
    }
}
