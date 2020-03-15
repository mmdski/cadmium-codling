package io.github.mmdski.codling.crosssection;

/**
 * Spatial information for cross section coordinates
 */
public class Coordinate {

    private double y, z;

    /**
     * Creates a new coordinate.
     *
     * @param y y-value of coordinate
     * @param z z-value of coordinate
     */
    public Coordinate(double y, double z) {

        if (!Double.isFinite(y) || !Double.isFinite(z))
            throw new IllegalArgumentException();

        this.y = y;
        this.z = z;
    }

    /**
     * Creates a copy of <code>c</code>
     *
     * @param c Coordinate to copy
     */
    public Coordinate(Coordinate c) {

        if (c == null)
            throw new NullPointerException();

        y = c.y;
        z = c.z;
    }

    /**
     * Returns a new coordinate interpolated in y-value.
     *
     * The z-value of the new coordinate is <code>z</code>, while the y-value is
     * linearly interpolated between <code>this.y</code> and <code>c.y</code>.
     *
     * @param c Other coordinate providing interpolation information
     * @param z z-value of interpolated coordinate
     *
     * @return New coordinate with interpolated y-value
     */
    public Coordinate interpY(Coordinate c, double z) {

        if (c == null)
            throw new NullPointerException();

        // prevent extrapolation
        if ((z < c.z) && (z < this.z) || (c.z < z && this.z < z))
            throw new IllegalArgumentException();

        double slope = (c.y - this.y) / (c.z - this.z);
        double y = slope * (z - this.z) + this.y;

        return new Coordinate(y, z);
    }

    /**
     * Returns a new coordinate interpolated in z-value.
     *
     * The y-value of the new coordinate is <code>y</code>, while the z-value is
     * linearly interpolated between <code>this.z</code> and <code>c.z</code>.
     *
     * @param c Other coordinate providing interpolation information
     * @param y y-value of interpolated coordinate
     *
     * @return New coordinate with interpolated z-value
     */
    public Coordinate interpZ(Coordinate c, double y) {

        if (c == null)
            throw new NullPointerException("c must be non-null");

        // prevent extrapolation
        if ((y < c.y) && (y < this.y) || (c.y < y && this.y < y))
            throw new IllegalArgumentException();

        double slope = (c.z - this.z) / (c.y - this.y);
        double z = slope * (y - this.y) + this.z;

        return new Coordinate(y, z);
    }

    /**
     * @return y-value of this coordinate
     */
    public double y() {
        return y;
    }

    /**
     * @return z-value of this coordinate
     */
    public double z() {
        return z;
    }
}
