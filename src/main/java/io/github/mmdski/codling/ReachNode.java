package io.github.mmdski.codling;

import io.github.mmdski.codling.crosssection.CrossSection;
import io.github.mmdski.codling.crosssection.CrossSectionProperties;;

/**
 * Node in river reach data structure
 */
public class ReachNode {

    private double x;
    private double thalweg;
    private CrossSection xs;

    /**
     * Construct a new reach node
     * <p>
     * <code>x</code> and <code>thalweg</code> must be finite, and <code>xs</code>
     * must not be <code>null</code>.
     * <p>
     * <code>ReachNode</code> maintains a reference to <code>xs</code>.
     *
     * @param x       Distance downstream of this node
     * @param thalweg Thalweg elevation of this node
     * @param xs      <code>CrossSection</code> of this node
     */
    public ReachNode(double x, double thalweg, CrossSection xs) {

        if (!Double.isFinite(x))
            throw new IllegalArgumentException();

        if (!Double.isFinite(thalweg))
            throw new IllegalArgumentException();

        if (xs == null)
            throw new IllegalArgumentException();

        this.x = x;
        this.thalweg = thalweg;
        this.xs = xs;

    }

    /**
     * Returns the downstream distance of this reach node
     *
     * @return Downstream distance
     */
    public double distanceDownstream() {
        return x;
    }

    /**
     * Returns properties computed at this node for a given discharge and water
     * surface elevation
     *
     * @param discharge             Discharge value for computing properties
     * @param waterSurfaceElevation Water surface elvation for computing properties
     *
     * @return Computed reach node properties
     */
    public ReachNodeProperties properties(double discharge, double waterSurfaceElevation) {

        if (!Double.isFinite(discharge))
            throw new IllegalArgumentException();

        if (!Double.isFinite(waterSurfaceElevation))
            throw new IllegalArgumentException();

        double depth = waterSurfaceElevation - thalweg;

        CrossSectionProperties p = xs.properties(waterSurfaceElevation);
        double area = p.value(CrossSectionProperties.Property.AREA);
        double conveyance = p.value(CrossSectionProperties.Property.CONVEYANCE);
        double velocityCoeff = p.value(CrossSectionProperties.Property.CONVEYANCE);

        double velocity = discharge / area;
        double frictionSlope = (discharge * discharge) / (conveyance * conveyance);
        double velocityHead = velocityCoeff * velocity * velocity / (2 * Constants.gravity());

        ReachNodeProperties properties = new ReachNodeProperties();
        properties.setValue(ReachNodeProperties.Property.X, x);
        properties.setValue(ReachNodeProperties.Property.Y, thalweg);
        properties.setValue(ReachNodeProperties.Property.DEPTH, depth);
        properties.setValue(ReachNodeProperties.Property.DISCHARGE, discharge);
        properties.setValue(ReachNodeProperties.Property.VELOCITY, velocity);
        properties.setValue(ReachNodeProperties.Property.FRICTION_SLOPE, frictionSlope);
        properties.setValue(ReachNodeProperties.Property.VELOCITY_HEAD, velocityHead);

        return properties;

    }

    /**
     * Returns the thalweg elevation of this reach node
     *
     * @return Thalweg elevation
     */
    public double thalweg() {
        return thalweg;
    }
}
