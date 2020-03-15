package io.github.mmdski.codling;

/**
 * Elevation boundary condition
 */
public interface ElevationBC {

    /**
     * Return elevation boundary condition value, given discharge
     *
     * @param discharge Discharge value
     *
     * @return Elevation
     */
    public double elevation(double discharge);

}
