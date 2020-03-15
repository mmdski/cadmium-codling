package io.github.mmdski.codling.crosssection;

/**
 * Cross section coordinate array
 *
 * Provides methods for cross section coordinate array manipulation
 */
public class CoArray {

    private double minY;
    private Coordinate[] coordinates;

    /**
     * Creates a <code>CoArray</code> from coordinates defined in <code>y</code> and
     * <code>z</code>
     * <p>
     * <code>y</code> and <code>z</code> must not be <code>null</code>, must have
     * length greater than 2, and must be of equal length.
     * <p>
     * <code>z</code> must represent a finite length, and <code>z</code> must be in
     * ascending order.
     *
     * @param y y-values of array
     * @param z z-values of array
     *
     */
    public CoArray(double[] y, double z[]) {

        if (y == null || z == null)
            throw new NullPointerException();

        int n = y.length;

        if (n < 2 || z.length < 2)
            throw new IllegalArgumentException();

        if (n != z.length)
            throw new IllegalArgumentException();

        if (z[n - 1] - z[0] <= 0)
            throw new IllegalArgumentException();

        coordinates = new Coordinate[n];
        minY = y[0];
        coordinates[0] = new Coordinate(y[0], z[0]);

        for (int i = 1; i < n; i++) {

            if (y[i] < minY)
                minY = y[i];

            coordinates[i] = new Coordinate(y[i], z[i]);

            if (coordinates[i - 1].z() > coordinates[i].z())
                throw new IllegalArgumentException();
        }
    }

    /**
     * Creates a copy of a <code>CoArray</code>
     *
     * @param coArray <code>CoArray</code> to copy
     */
    public CoArray(CoArray coArray) {

        if (coArray == null)
            throw new NullPointerException();

        minY = coArray.minY;
        coordinates = new Coordinate[coArray.coordinates.length];
        for (int i = 0; i < coArray.coordinates.length; i++) {
            coordinates[i] = new Coordinate(coArray.coordinates[i].y(), coArray.coordinates[i].z());
        }
    }

    private CoArray(Coordinate[] coords, double minY) {
        this.minY = minY;
        coordinates = coords;
    }

    /**
     * Find the index of the coordinate with the greatest z-value that's lower than
     * z.
     */
    private int findLow(Coordinate[] coordinates, double z, int lo, int hi) {

        // if the highest and lowest indices are off by one, return lo.
        if (hi - lo == 1)
            return lo;

        int mid = (hi - lo) / 2;

        if (coordinates[mid].z() > z)
            return findLow(coordinates, z, lo, mid);
        else
            return findLow(coordinates, z, mid + 1, hi);
    }

    /**
     * Adds a y value to all y values in this coordinate array and returns a new
     * <code>CoArray</code>
     *
     * @param y Value added to all y coordinates of this instance
     * @return new <code>CoArray</code>
     */
    public CoArray addY(double y) {

        if (!Double.isFinite(y))
            throw new IllegalArgumentException();

        Coordinate[] new_coords = new Coordinate[coordinates.length];
        for (int i = 0; i < coordinates.length; i++)
            new_coords[i] = new Coordinate(coordinates[i].y() + y, coordinates[i].z());
        return new CoArray(new_coords, minY + y);
    }

    /**
     * Returns the length of this coordinate array
     *
     * @return Length of this coordinate array
     */
    public int length() {
        return coordinates.length;
    }

    /**
     * Returns minimum y value
     *
     * @return Minimum y value
     */
    public double minY() {
        return minY;
    }

    /**
     * Returns a subset of coordinates in this array based on z-values
     * <p>
     * End coordinates are interpolated when <code>zLo</code> and <code>zHi</code>
     * are not equal to z-values of coordinates in this array.
     * <p>
     * <code>zLo</code> and <code>zHi</code> must be within the minimum and maximum
     * z-values of this array.
     * <p>
     * <code>zHi</code> must be greater than <code>zLo</code>.
     *
     * @param zLo Low z-value of the subset to create
     * @param zHi High z-value of the subset to create
     * @return Subset of coordinates in a new coordinate array
     */
    public CoArray subArray(double zLo, double zHi) {

        int n = this.length();

        if (!Double.isFinite(zLo) || !Double.isFinite(zHi))
            throw new IllegalArgumentException();

        if (zHi <= zLo)
            throw new IllegalArgumentException();

        if (zLo < coordinates[0].z() || coordinates[n - 1].z() < zHi)
            throw new IllegalArgumentException();

        int i; // index of this array
        int j = 0; // index of other array

        double minY;

        // at most, the new array will be equal to the length of this array
        Coordinate[] tempSubCoords = new Coordinate[n];
        Coordinate[] subCoords;

        i = findLow(coordinates, zLo, 0, n - 1);

        // add the first coordinate to the array by interpolation
        tempSubCoords[0] = coordinates[i].interpY(coordinates[i + 1], zLo);
        minY = tempSubCoords[0].y();

        // add coordinates in between zLo and zHi
        while (coordinates[++i].z() < zHi) {
            tempSubCoords[++j] = new Coordinate(coordinates[i]);
            if (tempSubCoords[j].y() < minY)
                minY = tempSubCoords[j].y();
        }

        tempSubCoords[j + 1] = coordinates[i].interpY(coordinates[i - 1], zHi);
        if (tempSubCoords[j + 1].y() < minY)
            minY = tempSubCoords[j + 1].y();

        int subLength = j + 2;

        subCoords = new Coordinate[subLength];
        for (int k = 0; k < subLength; k++)
            subCoords[k] = tempSubCoords[k];

        return new CoArray(subCoords, minY);
    }

    /**
     * Returns the y-values of this coordinate array
     *
     * @return y-values of this coordinate array
     */
    public double[] y() {
        double[] y = new double[this.coordinates.length];
        for (int i = 0; i < this.coordinates.length; i++) {
            y[i] = this.coordinates[i].y();
        }
        return y;
    }

    /**
     * Returns the z-values of this coordinate array
     *
     * @return z-values of this coordinate array
     */
    public double[] z() {
        double[] z = new double[this.coordinates.length];
        for (int i = 0; i < this.coordinates.length; i++) {
            z[i] = this.coordinates[i].z();
        }
        return z;
    }
}
