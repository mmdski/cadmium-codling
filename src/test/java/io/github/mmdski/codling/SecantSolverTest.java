package io.github.mmdski.codling;

import org.junit.Test;
import static org.junit.Assert.*;

public class SecantSolverTest {

    /*
     * use example from wikipedia
     * https://en.wikipedia.org/wiki/Secant_method#Computational_example
     */
    private class TestSolvable implements SecantSolvable {

        private double x0;
        private double x1;

        public TestSolvable() {
            x0 = 10;
            x1 = 30;
        }

        public double firstIteration() {
            return x0;
        }

        public double secondIteration() {
            return x1;
        }

        public double solverFunction(double x) {
            return x * x - 612;
        }
    }

    @Test
    public void testConstruction() {
        int maxIterations = 20;
        double eps = 0.001;

        SecantSolver solver = new SecantSolver(maxIterations, eps);
        assertNotNull(solver);
        solver = null;

        boolean illegalArgumentExceptionCaught = false;
        try {
            solver = new SecantSolver(1, eps);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            solver = new SecantSolver(maxIterations, 0);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            solver = new SecantSolver(0, 0);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }
    }

    @Test
    public void testSolve() {

        int maxIterations = 20;
        double eps = 0.003;
        double root = 24.738633748750722;

        TestSolvable solvable = new TestSolvable();
        SecantSolver solver = new SecantSolver(maxIterations, eps);
        SecantSolution solution = solver.solve(solvable);

        assertTrue(solution.solutionFound());
        assertEquals(root, solution.solution(), eps);
    }

    @Test
    public void testSolutionNotFound() {
        int maxIterations = 2;
        double eps = 0.003;

        TestSolvable solvable = new TestSolvable();
        SecantSolver solver = new SecantSolver(maxIterations, eps);
        SecantSolution solution = solver.solve(solvable);

        assertFalse(solution.solutionFound());
        assertTrue(Double.isNaN(solution.solution()));
    }
}
