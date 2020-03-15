package io.github.mmdski.codling;

import io.github.mmdski.codling.crosssection.CrossSection;

/**
 * Normal elevation boundary condition
 * <p>
 * Normal elevation is computed from a <code>crossSection</code>.
 */
public class NormalElevationBC implements ElevationBC {

    CrossSection crossSection;
    double slope;
    double lastElevation;
    double thalwegElevation;

    /**
     * Construct a new normal elevation boundary condition
     *
     * @param crossSection Cross section used to compute normal elevation
     * @param slope        Slope used with <code>crossSection</code> in the
     *                     computation of normal elevation
     */
    public NormalElevationBC(CrossSection crossSection, double slope) {

        if (crossSection == null) {
            throw new IllegalArgumentException();
        }

        if (!Double.isFinite(slope)) {
            throw new IllegalArgumentException();
        }

        this.crossSection = crossSection;
        this.slope = slope;
        this.thalwegElevation = 0;
        lastElevation = Double.NaN;

    }

    /**
     * Construct a new normal elevation boundary condition
     *
     * @param crossSection     Cross section used to compute normal elevation
     * @param slope            Slope used with <code>crossSection</code> in the
     *                         computation of normal elevation
     * @param thalwegElevation Elevation of thalweg
     */
    public NormalElevationBC(CrossSection crossSection, double slope, double thalwegElevation) {

        if (crossSection == null) {
            throw new IllegalArgumentException();
        }

        if (!Double.isFinite(slope)) {
            throw new IllegalArgumentException();
        }

        this.crossSection = crossSection;
        this.slope = slope;
        this.thalwegElevation = thalwegElevation;
        lastElevation = Double.NaN;

    }

    /**
     * Compute normal elevation
     *
     * @param normalDischarge Normal discharge
     *
     * @return Normal elevation
     */
    public double elevation(double normalDischarge) {

        if (!Double.isFinite(normalDischarge)) {
            throw new IllegalArgumentException();
        }

        double stage;
        double lastStage;
        double elevation;

        if (Double.isNaN(lastElevation)) {
            stage = crossSection.normalY(normalDischarge, slope);
        } else {
            lastStage = lastElevation - thalwegElevation;
            stage = crossSection.normalY(normalDischarge, slope, lastStage);
        }

        elevation = stage + thalwegElevation;
        lastElevation = elevation;

        return elevation;

    }

}
