package io.github.mmdski.codling;

import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.*;

public class ReachNodePropertiesTest {

    /**
     * Test Property values
     *
     */
    @Test
    public void testPropertyValues() {
        String[] properties = { "X", "Y", "DEPTH", "DISCHARGE", "VELOCITY", "FRICTION_SLOPE", "VELOCITY_HEAD" };

        int n = properties.length;

        ReachNodeProperties.Property p;
        for (int i = 0; i < n; i++) {
            p = ReachNodeProperties.Property.valueOf(properties[i]);
            assertEquals(i, p.value());
        }
    }

    /**
     * Test result construction
     */
    @Test
    public void testReachNodeProperties() {
        ReachNodeProperties res = new ReachNodeProperties();
        assertTrue(res.getClass() == ReachNodeProperties.class);
    }

    /**
     * Test set and get value
     */
    @Test
    public void testValues() {
        ReachNodeProperties res = new ReachNodeProperties();
        Random random = new Random();
        int n = ReachNodeProperties.Property.values().length;
        double[] values = new double[n];

        double delta = 0;

        for (ReachNodeProperties.Property p : ReachNodeProperties.Property.values()) {
            values[p.value()] = random.nextFloat();
            res.setValue(p, values[p.value()]);
        }

        for (ReachNodeProperties.Property p : ReachNodeProperties.Property.values()) {
            assertEquals(values[p.value()], res.value(p), delta);
        }
    }
}
