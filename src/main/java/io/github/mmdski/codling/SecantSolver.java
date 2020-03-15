package io.github.mmdski.codling;

/**
 * Solver class for the secant root finding method
 */
public class SecantSolver {

    private int maxIterations;
    private double eps;

    /**
     * Construct a new secant solver
     *
     * @param maxIterations Maximum number of iterations
     * @param eps           Convergence tolerance
     */
    public SecantSolver(int maxIterations, double eps) {

        if (maxIterations < 2 || eps <= 0)
            throw new IllegalArgumentException();

        this.maxIterations = maxIterations;
        this.eps = eps;

    }

    /**
     * Find a root of a solvable function
     *
     * @param solvable Function to find a root of
     *
     * @return Root finding solution
     */
    public SecantSolution solve(SecantSolvable solvable) {

        int i;
        boolean solutionFound = false;
        double delta;
        double xDiff;
        double yDiff;
        double xComputed = Double.NaN;
        double[] x = new double[maxIterations];
        double[] y = new double[maxIterations];

        x[0] = solvable.firstIteration();
        x[1] = solvable.secondIteration();

        y[0] = solvable.solverFunction(x[0]);
        y[1] = solvable.solverFunction(x[1]);

        for (i = 2; i < maxIterations; i++) {

            xDiff = x[i - 1] - x[i - 2];
            yDiff = y[i - 1] - y[i - 2];

            x[i] = x[i - 1] - y[i - 1] * xDiff / yDiff;
            y[i] = solvable.solverFunction(x[i]);

            delta = Math.abs(x[i] - x[i - 1]);

            if (delta <= eps) {
                solutionFound = true;
                xComputed = x[i];
                break;
            }
        }

        SecantSolution solution = new SecantSolution(solutionFound, i, xComputed);

        return solution;
    }

}
