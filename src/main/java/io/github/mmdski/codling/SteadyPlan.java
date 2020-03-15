package io.github.mmdski.codling;

/**
 * Steady state plan
 * <p>
 * A steady state plan includes information for a steady state solution
 */
public class SteadyPlan {

    /**
     * Boundary condition location
     */
    public enum BCLocation {
        UPSTREAM, DOWNSTREAM;
    }

    private double[] discharge;
    private double[] wse;
    private BCLocation bcLocation;
    private int bcIndex;

    /**
     * Construct a new steady state plan
     *
     * @param discharge         Discharge values for each node
     * @param boundaryCondition Elevation boundary condition type
     * @param bcLocation        Location of elevation boundary location
     */
    public SteadyPlan(double[] discharge, ElevationBC boundaryCondition, BCLocation bcLocation) {

        int n = discharge.length;
        this.discharge = new double[n];
        this.wse = new double[n];
        this.bcLocation = bcLocation;

        for (int i = 0; i < n; i++) {
            if (!Double.isFinite(discharge[i]))
                throw new IllegalArgumentException();
            this.discharge[i] = discharge[i];
        }

        if (bcLocation == BCLocation.UPSTREAM)
            bcIndex = 0;
        else
            bcIndex = length() - 1;

        wse[bcIndex] = boundaryCondition.elevation(discharge[bcIndex]);
    }

    /**
     * Elevation at the boundary condition node
     *
     * @return Boundary condition elevation
     */
    public double bcElevation() {
        return wse[bcIndex];
    }

    /**
     * Location of boundary condition
     *
     * @return Boundary condition location
     */
    public BCLocation bcLocation() {
        return bcLocation;
    }

    /**
     * Index number of boundary condition node
     *
     * @return Boundary
     */
    public int bcNode() {
        return bcIndex;
    }

    /**
     * Discharge at node <code>i</code>
     *
     * @param i Node number
     *
     * @return Discharge
     */
    public double discharge(int i) {
        return discharge[i];
    }

    /**
     * The number of nodes in this plan
     *
     * @return Number of nodes in this plan
     */
    public int length() {
        return discharge.length;
    }

    /**
     * Direction of solution
     * <p>
     * Returns -1 if the direction is from downstream to upstream, 1 otherwise.
     *
     * @return Direction of solution
     */
    public int solutionDirection() {
        if (bcLocation == BCLocation.DOWNSTREAM)
            return -1;
        else
            return 1;
    }

}
