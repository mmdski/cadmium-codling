package io.github.mmdski.codling;

import org.junit.Test;
import static org.junit.Assert.*;

import io.github.mmdski.codling.crosssection.CrossSection;

public class ReachNodeTest {

    @Test
    public void testReachNode() {

        ReachNode node;
        boolean illegalArgumentExceptionCaught;

        double x = 0;
        double thalweg = 10;
        double delta = 0;
        CrossSection xs = new ReachNodeTestCrossSection();

        node = new ReachNode(x, thalweg, xs);
        assertEquals(thalweg, node.thalweg(), delta);
        assertEquals(x, node.distanceDownstream(), delta);

        illegalArgumentExceptionCaught = false;
        try {
            node = new ReachNode(Double.NaN, thalweg, xs);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            node = new ReachNode(x, Double.NaN, xs);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            node = new ReachNode(x, thalweg, null);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }
    }

    @Test
    public void testProperties() {

        ReachNode node;
        boolean illegalArgumentExceptionCaught;
        double p;

        double x = 0;
        double thalweg = 10;
        double discharge = 1;
        double depth = 1;
        double y = thalweg + discharge;
        CrossSection xs = new ReachNodeTestCrossSection();

        node = new ReachNode(x, thalweg, xs);

        ReachNodeProperties properties;

        properties = node.properties(discharge, y);

        p = properties.value(ReachNodeProperties.Property.X);
        assertEquals(x, p, 0);

        p = properties.value(ReachNodeProperties.Property.Y);
        assertEquals(thalweg, p, 0);

        p = properties.value(ReachNodeProperties.Property.DEPTH);
        assertEquals(depth, p, 0);

        p = properties.value(ReachNodeProperties.Property.DISCHARGE);
        assertEquals(discharge, p, 0);

        p = properties.value(ReachNodeProperties.Property.VELOCITY);
        assertEquals(1, p, 0);

        p = properties.value(ReachNodeProperties.Property.FRICTION_SLOPE);
        assertEquals(1, p, 0);

        p = properties.value(ReachNodeProperties.Property.VELOCITY_HEAD);
        assertEquals(1 / (2 * Constants.gravity()), p, 0);

        illegalArgumentExceptionCaught = false;
        try {
            properties = node.properties(Double.NaN, y);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            properties = node.properties(discharge, Double.NaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

    }
}
