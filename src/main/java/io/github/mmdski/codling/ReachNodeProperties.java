package io.github.mmdski.codling;

/**
 * Reach node properties
 * <p>
 * Stores results from reach node property computation
 */
public class ReachNodeProperties {

    private double[] results;

    /**
     * Reach node property fields
     */
    public enum Property {
        X(0), Y(1), DEPTH(2), DISCHARGE(3), VELOCITY(4), FRICTION_SLOPE(5), VELOCITY_HEAD(6);

        private final int value;

        private Property(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }

    }

    /**
     * Creates a new reach node properties
     */
    public ReachNodeProperties() {
        results = new double[Property.values().length];
    }

    /**
     * Sets the value of a reach node property
     *
     * @param p     Reach node property
     * @param value Value of reach node property
     */
    public void setValue(Property p, double value) {
        results[p.value()] = value;
    }

    /**
     * Returns the value of a reach node property
     *
     * @param p Reach node property
     *
     * @return The value of the reach node property contained in <code>p</code>
     */
    public double value(Property p) {
        return results[p.value()];
    }
}
