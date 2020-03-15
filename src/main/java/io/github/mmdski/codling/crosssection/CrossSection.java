package io.github.mmdski.codling.crosssection;

/**
 * Cross section interface
 */
public interface CrossSection {

    /**
     * Computes hydraulic properties for an elevation
     *
     * @param y Elevation
     *
     * @return Hydraulic properties for elevation <code>y</code>
     */
    CrossSectionProperties properties(double y);

    /**
     * Computes critical elevation for discharge <code>Q</code>
     *
     * @param Q Critical discharge
     *
     * @return Critical elevation for discharge <code>Q</code>
     */
    public double criticalY(double Q);

    /**
     * Computes critical elevation for critical discharge <code>Q</code> using
     * <code>y0</code> as an initial estimate
     *
     * @param Q  Critical discharge
     * @param y0 Initial estimate of critical elevation
     *
     * @return Critical elevation for discharge <code>Q</code>
     */
    public double criticalY(double Q, double y0);

    /**
     * Computes normal elevation for discharge <code>Q</code> and slope
     * <code>S</code>
     *
     * @param Q Normal discharge
     * @param S Channel slope
     *
     * @return Normal elevation for discharge <code>Q</code> and slope
     *         <code>S</code>
     */
    public double normalY(double Q, double S);

    /**
     * Computes normal elevation for discharge <code>Q</code> and slope
     * <code>S</code> using <code>y0</code> as an initial estimate
     *
     * @param Q  Normal discharge
     * @param S  Channel slope
     * @param y0 Initial estimate of normal elevation
     *
     * @return Normal elevation for discharge <code>Q</code> and slope
     *         <code>S</code>
     */
    public double normalY(double Q, double S, double y0);

    /**
     * Returns the thalweg elevation of this cross section
     *
     * @return Thalweg elevation
     */
    public double thalweg();

}
