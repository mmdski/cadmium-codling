package io.github.mmdski.codling;

/**
 * Solution from the standard step method
 */
public class StandardStepSolution {

    private double[] wse;
    private double[] thalweg;
    private double[] q;

    /**
     * Construct a standard step solution
     *
     * @param wsElevation      Water surface elevation of solution
     * @param thalwegElevation Thalweg elevation of solution
     * @param discharge        Discharge of solution
     */
    public StandardStepSolution(double[] wsElevation, double[] thalwegElevation, double[] discharge) {

        if (wsElevation == null)
            throw new IllegalArgumentException();

        if (thalwegElevation == null)
            throw new IllegalArgumentException();

        if (discharge == null)
            throw new IllegalArgumentException();

        int n = wsElevation.length;

        if (thalwegElevation.length != n)
            throw new IllegalArgumentException();

        if (discharge.length != n)
            throw new IllegalArgumentException();

        wse = new double[n];
        thalweg = new double[n];
        q = new double[n];

        for (int i = 0; i < n; i++) {
            wse[i] = wsElevation[i];
            thalweg[i] = thalwegElevation[i];
            q[i] = discharge[i];
        }

    }

    /**
     * Returns an array containing the discharge for each node in the solution
     *
     * @return Discharge array
     */
    public double[] discharge() {
        int n = q.length;
        double[] discharge = new double[n];
        for (int i = 0; i < n; i++)
            discharge[i] = q[i];
        return discharge;
    }

    /**
     * Returns an array containing the thalweg elevation for each node in the
     * solution
     *
     * @return Thalweg elevation array
     */
    public double[] thalwegElevation() {
        int n = thalweg.length;
        double[] thalwegElevation = new double[n];
        for (int i = 0; i < n; i++)
            thalwegElevation[i] = thalweg[i];
        return thalwegElevation;
    }

    /**
     * Returns an array containing the water surface elevation for each node in the
     * solution
     *
     * @return Water surface elevation array
     */
    public double[] wsElevation() {
        int n = wse.length;
        double[] wsElevation = new double[n];
        for (int i = 0; i < n; i++)
            wsElevation[i] = wse[i];
        return wsElevation;
    }

}
