package io.github.mmdski.codling.crosssection;

import io.github.mmdski.codling.Constants;
import io.github.mmdski.codling.SecantSolvable;
import io.github.mmdski.codling.SecantSolution;
import io.github.mmdski.codling.SecantSolver;
import io.github.mmdski.codling.crosssection.CrossSectionProperties.Property;

/**
 * Rectangular cross section
 * <p>
 * Cross section type for a simple rectangular cross section. The rectangular
 * cross section type has a single roughness value.
 */
public class RectangularCrossSection implements CrossSection {

    private double width;
    private double roughness;
    private double thalweg; // thalweg elevation

    private class NormalDepthSolvable implements SecantSolvable {

        private double Q, sqrtS, y0;

        public NormalDepthSolvable(double Q, double S, double y0) {
            this.Q = Q;
            this.sqrtS = Math.sqrt(S);
            this.y0 = y0;
        }

        public double firstIteration() {
            return y0;
        }

        public double secondIteration() {
            double err = solverFunction(y0);
            return y0 + 0.7 * err;
        }

        public double solverFunction(double y) {

            CrossSectionProperties properties = properties(y);
            double conveyance = properties.value(Property.CONVEYANCE);
            return conveyance * sqrtS - Q;
        }

    }

    /**
     * Construct a simple rectangular cross section
     * <p>
     * The thalweg elevation is initalized as 0.
     *
     * @param width     Cross section width
     * @param roughness Cross section roughness
     */
    public RectangularCrossSection(double width, double roughness) {

        if (!Double.isFinite(roughness) || roughness <= 0)
            throw new IllegalArgumentException();

        if (!Double.isFinite(width) || width <= 0)
            throw new IllegalArgumentException();

        this.width = width;
        this.roughness = roughness;
        thalweg = 0;
    }

    /**
     * Computes hydraulic properties of this cross section for an elevation
     *
     * @param y Elevation to compute hydraulic properites for
     *
     * @return Hydraulic properties computed for elevation <code>y</code>
     */
    public CrossSectionProperties properties(double y) {

        if (!Double.isFinite(y))
            throw new IllegalArgumentException();

        double depth = y - thalweg;

        CrossSectionProperties properties = new CrossSectionProperties();

        double area = depth * width;
        double perimeter = 2 * depth + width;
        double radius = area / perimeter;
        double conveyance = Constants.manningK() / roughness * area * Math.pow(radius, 2.0 / 3.0);
        double criticalFlow = area * Math.sqrt(Constants.gravity() * depth);

        properties.setValue(Property.DEPTH, depth);
        properties.setValue(Property.AREA, area);
        properties.setValue(Property.TOP_WIDTH, width);
        properties.setValue(Property.WETTED_PERIMETER, perimeter);
        properties.setValue(Property.HYDRAULIC_DEPTH, depth);
        properties.setValue(Property.HYDRAULIC_RADIUS, radius);
        properties.setValue(Property.CONVEYANCE, conveyance);
        properties.setValue(Property.VELOCITY_COEFF, 1);
        properties.setValue(Property.CRITICAL_FLOW, criticalFlow);

        return properties;
    }

    /**
     * Computes critical elevation for discharge <code>Q</code>
     *
     * @param Q Critical discharge
     *
     * @return Critical elevation for discharge <code>Q</code>
     */
    public double criticalY(double Q) {

        if (!Double.isFinite(Q) || Q <= 0)
            throw new IllegalArgumentException();

        double criticalDepth = Math.pow(Q * Q / (Constants.gravity() * width * width), 1.0 / 3.0);
        return criticalDepth + thalweg;
    }

    /**
     * Computes critical elevation for critical discharge <code>Q</code> using
     * <code>y0</code> as an initial estimate
     * <p>
     * Since the solution for critical depth of a rectangular channel is analytic,
     * <code>y0</code> is not used. However, an
     * <code>IllegalArgumentexception</code> will be thrown if <code>y0</code> is
     * less than or equal to the thalweg elevation of this cross section.
     *
     * @param Q  Critical discharge
     * @param y0 Initial estimate of critical elevation
     *
     * @return Critical elevation for discharge <code>Q</code>
     */
    public double criticalY(double Q, double y0) {

        if (!Double.isFinite(y0) || y0 <= thalweg)
            throw new IllegalArgumentException();

        if (!Double.isFinite(Q) || Q <= 0)
            throw new IllegalArgumentException();

        return criticalY(Q);
    }

    /**
     * Computes normal elevation for normal discharge <code>Q</code> and channel
     * slope <code>S</code> using an internally computed initial estimate of normal
     * elevation
     * <p>
     * The initial elevation estimate is equal to half of the width plus the thalweg
     * elevation of this cross section.
     *
     * @param Q Normal discharge
     * @param S Channel slope
     *
     * @return Normal elevation for discharge <code>Q</code> and slope
     *         <code>S</code>
     */
    public double normalY(double Q, double S) {

        if (!Double.isFinite(Q) || Q <= 0)
            throw new IllegalArgumentException();

        if (!Double.isFinite(S) || S <= 0)
            throw new IllegalArgumentException();

        double y0 = 0.5 * width + thalweg;

        return normalY(Q, S, y0);
    }

    /**
     * Computes normal elevation for normal discharge <code>Q</code> and channel
     * slope <code>S</code> using initial elevation estimate <code>y0</code>
     *
     * @param Q  Normal discharge
     * @param S  Channel slope
     * @param y0 Initial estimate of normal elevation
     *
     * @return Normal elevation for discharge <code>Q</code> and slope
     *         <code>S</code>
     */
    public double normalY(double Q, double S, double y0) {

        if (!Double.isFinite(y0) || y0 <= thalweg)
            throw new IllegalArgumentException();

        if (!Double.isFinite(Q) || Q <= 0)
            throw new IllegalArgumentException();

        if (!Double.isFinite(S) || S <= 0)
            throw new IllegalArgumentException();

        int maxIterations = 20;
        double eps = 0.003;

        NormalDepthSolvable solvable = new NormalDepthSolvable(Q, S, y0);
        SecantSolver solver = new SecantSolver(maxIterations, eps);
        SecantSolution solution = solver.solve(solvable);

        return solution.solution();
    }

    /**
     * Returns the thalweg elevation of this cross section
     *
     * @return Thalweg elevation
     */
    public double thalweg() {
        return thalweg;
    }
}
