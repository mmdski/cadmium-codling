package io.github.mmdski.codling;

import org.junit.Test;

import static org.junit.Assert.*;

public class SecantSolutionTest {

    @Test
    public void testSolution() {
        boolean solutionFound = true;
        int iterations = 5;
        double solution = 1.0;

        SecantSolution secantSolution = new SecantSolution(solutionFound, iterations, solution);

        assertEquals(solutionFound, secantSolution.solutionFound());
        assertEquals(iterations, secantSolution.iterations());
        assertEquals(solution, secantSolution.solution(), 0);
    }
}
