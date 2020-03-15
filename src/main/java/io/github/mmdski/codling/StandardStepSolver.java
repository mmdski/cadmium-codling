package io.github.mmdski.codling;

import io.github.mmdski.codling.ReachNodeProperties.Property;

/**
 * Standard step solution for steady-state hydraulics
 */
public class StandardStepSolver {

    private int maxIterations;
    private double eps;

    private class StandardStepSolvable implements SecantSolvable {

        private int i1, i2; // index 1 and index 2
        private double q1, q2, wse1; // node 1 discharge, node 2 discharge, water surface elevation 1
        Reach reach;

        public StandardStepSolvable(Reach reach, int node, int direction, double[] q, double[] wse) {
            i1 = node - 1 * direction;
            i2 = node;
            q1 = q[i1];
            q2 = q[i2];
            wse1 = wse[i1];
            this.reach = reach;
        }

        public double firstIteration() {
            return wse1;
        }

        public double secondIteration() {
            return wse1 + 0.7 * solverFunction(wse1);
        }

        public double solverFunction(double elevation) {
            double x1, x2; // channel distance
            double sf1, sf2, sf; // friction slope
            double vh1, vh2; // velocity head
            double he; // head loss
            double wsComputed; // computed water surface

            ReachNodeProperties p1 = reach.nodeProperties(i1, q1, wse1);
            x1 = p1.value(Property.X);
            sf1 = p1.value(Property.FRICTION_SLOPE);
            vh1 = p1.value(Property.VELOCITY_HEAD);

            ReachNodeProperties p2 = reach.nodeProperties(i2, q2, elevation);
            x2 = p2.value(Property.X);
            sf2 = p2.value(Property.FRICTION_SLOPE);
            vh2 = p2.value(Property.VELOCITY_HEAD);

            sf = 0.5 * (sf1 + sf2);
            he = sf * (x2 - x1);

            wsComputed = wse1 + vh1 - vh2 - he;

            return wsComputed - elevation;
        }
    }

    /**
     * Construct a new standard step solver
     * <p>
     * Standard step solvers created with this constructor use the default number of
     * maximum iterations (20) and epsilon (0.003) values.
     */
    public StandardStepSolver() {
        maxIterations = 20;
        eps = 0.003;
    }

    /**
     * Construct a new standard step solver, specifying maximum iterations and
     * epsilon
     *
     * @param maxIterations Maximum iterations to use in finding water surface
     *                      elevation solution
     * @param eps           Convergence tolerance for water surface elevation
     *                      solution
     */
    public StandardStepSolver(int maxIterations, double eps) {
        if (maxIterations < 2)
            throw new IllegalArgumentException();
        if (!Double.isFinite(eps))
            throw new IllegalArgumentException();
        if (eps < 0)
            throw new IllegalArgumentException();

        this.maxIterations = maxIterations;
        this.eps = eps;
    }

    /**
     * Solve a steady state hydraulic profile
     *
     * @param reach Reach
     * @param plan  Steady state plan
     * @return Standard step solution
     */
    public StandardStepSolution solvePlan(Reach reach, SteadyPlan plan) {

        int n = reach.length();

        if (plan == null) {
            throw new IllegalArgumentException();
        }

        if (n != plan.length()) {
            throw new IllegalArgumentException();
        }

        double[] discharge = new double[n];
        double[] wsElevation = new double[n];
        double[] thalwegElevation = reach.thalweg();

        int solutionDirection = plan.solutionDirection();
        int bcNode = plan.bcNode();
        int lastNode = n - 1 - bcNode;

        for (int i = 0; i < n; i++)
            discharge[i] = plan.discharge(i);

        wsElevation[bcNode] = plan.bcElevation();

        SecantSolver solver = new SecantSolver(maxIterations, eps);
        SecantSolution solution;
        StandardStepSolvable solvable;

        for (int i = bcNode + solutionDirection; solutionDirection * i <= lastNode; i = i + solutionDirection) {
            solvable = new StandardStepSolvable(reach, i, solutionDirection, discharge, wsElevation);
            solution = solver.solve(solvable);
            wsElevation[i] = solution.solution();
        }

        return new StandardStepSolution(wsElevation, thalwegElevation, discharge);
    }

}
