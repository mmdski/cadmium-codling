package io.github.mmdski.codling.crosssection;

import java.util.Random;

import org.junit.Test;

import static org.junit.Assert.*;

public class CrossSectionPropertiesTest {

    /**
     * Test Property values
     *
     */
    @Test
    public void testPropertyValues() {
        String[] properties = { "DEPTH", "AREA", "TOP_WIDTH", "WETTED_PERIMETER", "HYDRAULIC_DEPTH", "HYDRAULIC_RADIUS",
                "CONVEYANCE", "VELOCITY_COEFF", "CRITICAL_FLOW" };

        int n = properties.length;

        CrossSectionProperties.Property p;
        for (int i = 0; i < n; i++) {
            p = CrossSectionProperties.Property.valueOf(properties[i]);
            assertEquals(i, p.value());
        }
    }

    /**
     * Test result construction
     */
    @Test
    public void testCrossSectionProperties() {
        CrossSectionProperties res = new CrossSectionProperties();
        assertTrue(res.getClass() == CrossSectionProperties.class);
    }

    /**
     * Test set and get value
     */
    @Test
    public void testValues() {
        CrossSectionProperties res = new CrossSectionProperties();
        Random random = new Random();
        int n = CrossSectionProperties.Property.values().length;
        double[] values = new double[n];

        double delta = 0;

        for (CrossSectionProperties.Property p : CrossSectionProperties.Property.values()) {
            values[p.value()] = random.nextFloat();
            res.setValue(p, values[p.value()]);
        }

        for (CrossSectionProperties.Property p : CrossSectionProperties.Property.values()) {
            assertEquals(values[p.value()], res.value(p), delta);
        }
    }
}
