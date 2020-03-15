package io.github.mmdski.codling;

public class SecantSolution {

    private boolean solutionFound;
    private int iterations;
    private double solution;

    public SecantSolution(boolean solutionFound, int iterations, double solution) {
        this.solutionFound = solutionFound;
        this.iterations = iterations;
        this.solution = solution;
    }

    public boolean solutionFound() {
        return solutionFound;
    }

    public int iterations() {
        return iterations;
    }

    public double solution() {
        return solution;
    }
}
