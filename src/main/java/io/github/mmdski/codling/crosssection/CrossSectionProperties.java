package io.github.mmdski.codling.crosssection;

/**
 * Hydraulic properties of a cross section
 * <p>
 * Stored results from cross section hydraulic property computations
 */
public class CrossSectionProperties {

    private double[] results;

    /**
     * Hydraulic result property fields
     */
    public enum Property {
        DEPTH(0), AREA(1), TOP_WIDTH(2), WETTED_PERIMETER(3), HYDRAULIC_DEPTH(4), HYDRAULIC_RADIUS(5), CONVEYANCE(6),
        VELOCITY_COEFF(7), CRITICAL_FLOW(8);

        private final int value;

        private Property(int value) {
            this.value = value;
        }

        /**
         * Returns the integer value for a Property
         *
         * @return Integer value
         */
        public int value() {
            return value;
        }

    }

    /**
     * Creates a new cross section properties
     */
    public CrossSectionProperties() {
        results = new double[Property.values().length];
    }

    /**
     * Sets the value of a cross section property
     *
     * @param p     Cross section property
     * @param value Value of cross section property
     */
    public void setValue(Property p, double value) {
        results[p.value()] = value;
    }

    /**
     * Returns the value of a cross section property
     *
     * @param p Cross section property
     *
     * @return The value of the cross section property contained in <code>p</code>
     */
    public double value(Property p) {
        return results[p.value()];
    }
}
