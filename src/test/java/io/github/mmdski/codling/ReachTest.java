package io.github.mmdski.codling;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

import io.github.mmdski.codling.crosssection.CrossSection;

public class ReachTest {

    @Test
    public void Reach() {

        Reach reach;
        boolean illegalArgumentExceptionCaught;

        double[] x = { 0, 1000, 2000 };
        double[] thalweg = { 2, 1, 0 };
        double delta = 0;
        int[] crossSectionNumbers = { 0, 0, 0 };
        ReachNodeTestCrossSection xs = new ReachNodeTestCrossSection();
        CrossSection[] crossSections = { xs };

        reach = new Reach(x, thalweg, crossSectionNumbers, crossSections);

        assertEquals(x.length, reach.length());
        assertArrayEquals(x, reach.distanceDownstream(), delta);
        assertArrayEquals(thalweg, reach.thalweg(), delta);

        /*
         * Non-equal arrays
         */
        illegalArgumentExceptionCaught = false;
        try {
            reach = new Reach(Arrays.copyOf(x, 1), thalweg, crossSectionNumbers, crossSections);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            reach = new Reach(x, Arrays.copyOf(thalweg, 1), crossSectionNumbers, crossSections);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            reach = new Reach(x, thalweg, Arrays.copyOf(crossSectionNumbers, 1), crossSections);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* Short arrays */
        illegalArgumentExceptionCaught = false;
        try {
            reach = new Reach(Arrays.copyOf(x, 1), Arrays.copyOf(thalweg, 1), Arrays.copyOf(crossSectionNumbers, 1),
                    crossSections);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* bad cross section numbers */
        illegalArgumentExceptionCaught = false;
        int[] allBadCrossSectionNumbers = { 1, 1, 1 };
        try {
            reach = new Reach(x, thalweg, allBadCrossSectionNumbers, crossSections);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        int[] secondBadCrossSectionNumbers = { 0, 2, 3 };
        try {
            reach = new Reach(x, thalweg, secondBadCrossSectionNumbers, crossSections);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        double[] outOfOrderX = { 1000, 0, 2000 };
        try {
            reach = new Reach(outOfOrderX, thalweg, crossSectionNumbers, crossSections);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

    }

}
