package io.github.mmdski.codling;

/**
 * Interface defining function solvable using the secant method
 */
public interface SecantSolvable {

    /**
     * Function to find the root for
     *
     * @param x Independent variable of function
     *
     * @return Function value for <code>x</code>
     */
    public double solverFunction(double x);

    /**
     * First iteration for the solution of the root of the function
     *
     * @return First iteration of solution
     */
    public double firstIteration();

    /**
     * Second iteration for the solution of the root of the function
     *
     * @return Second iteration of solution
     */
    public double secondIteration();

}
