package io.github.mmdski.codling;

import org.junit.Test;
import static org.junit.Assert.*;

import io.github.mmdski.codling.SteadyPlan.BCLocation;
import io.github.mmdski.codling.crosssection.CrossSectionProperties;
import io.github.mmdski.codling.crosssection.RectangularCrossSection;

public class StandardStepTest {

    @Test
    public void normalDepthTest() {

        double eps = 0.003;
        int maxIterations = 20;

        /* compute normal depth */

        /* cross section properties */
        double width = 1;
        double roughness = 0.03;
        RectangularCrossSection crossSection = new RectangularCrossSection(width, roughness);
        RectangularCrossSection[] crossSections = { crossSection };

        /* reach properties */
        int nNodes = 25;
        double slope = 0.001;
        double dx = 10;
        double[] x = new double[nNodes];
        double[] thalwegElevation = new double[nNodes];
        int[] xsNumber = new int[nNodes];

        /* plan properties */
        double[] discharge = new double[nNodes];

        double normalDepth = 0.75;
        CrossSectionProperties properties = crossSection.properties(normalDepth);
        double conveyance = properties.value(CrossSectionProperties.Property.CONVEYANCE);
        double normalDischarge = conveyance * Math.sqrt(slope);

        for (int i = 0; i < nNodes; i++) {
            discharge[i] = normalDischarge;
            x[i] = dx * i;
            thalwegElevation[i] = (nNodes - i - 1) * dx * slope;
            xsNumber[i] = 0;
        }

        NormalElevationBC boundaryC = new NormalElevationBC(crossSection, slope);
        Reach reach = new Reach(x, thalwegElevation, xsNumber, crossSections);
        SteadyPlan plan = new SteadyPlan(discharge, boundaryC, BCLocation.DOWNSTREAM);
        StandardStepSolver solver = new StandardStepSolver(maxIterations, eps);
        StandardStepSolution solution = solver.solvePlan(reach, plan);

        double[] wseSolution = solution.wsElevation();
        double depth;
        for (int i = 0; i < nNodes; i++) {
            depth = wseSolution[i] - thalwegElevation[i];
            assertEquals(normalDepth, depth, eps);
        }
    }

}
