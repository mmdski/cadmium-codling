package io.github.mmdski.codling.crosssection;

import org.junit.Test;
import static org.junit.Assert.*;
import io.github.mmdski.codling.crosssection.CrossSectionProperties.Property;
import io.github.mmdski.codling.Constants;

public class RectangularCrossSectionTest {

    @Test
    public void testConstruction() {

        double width = 10;
        double roughness = 0.003;
        boolean illegalArgumentExceptionCaught;

        RectangularCrossSection crossSection;

        crossSection = new RectangularCrossSection(width, roughness);
        assertEquals(0, crossSection.thalweg(), 0);

        /* test invalid zero values */
        illegalArgumentExceptionCaught = false;
        try {
            crossSection = new RectangularCrossSection(0, roughness);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection = new RectangularCrossSection(width, 0);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection = new RectangularCrossSection(0, 0);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* test invalid non-finite values */
        illegalArgumentExceptionCaught = false;
        try {
            crossSection = new RectangularCrossSection(Double.NaN, roughness);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection = new RectangularCrossSection(width, Double.NaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection = new RectangularCrossSection(Double.NaN, Double.NaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }
    }

    @Test
    public void testProperties() {

        boolean illegalArgumentExceptionCaught;

        double eps = 0;
        double width = 10;
        double roughness = 0.003;
        double depth;
        double area;
        double perimeter;
        double radius;
        double conveyance;
        double criticalFlow;

        CrossSectionProperties properties;
        RectangularCrossSection crossSection = new RectangularCrossSection(width, roughness);

        double maxDepth = 20;

        for (depth = 0; depth <= maxDepth; depth += 10) {
            properties = crossSection.properties(depth);

            area = width * depth;
            perimeter = 2 * depth + width;
            radius = area / perimeter;
            conveyance = Constants.manningK() / roughness * area * Math.pow(radius, 2.0 / 3.0);
            criticalFlow = area * Math.sqrt(Constants.gravity() * depth);

            assertEquals(depth, properties.value(Property.DEPTH), eps);
            assertEquals(area, properties.value(Property.AREA), eps);
            assertEquals(width, properties.value(Property.TOP_WIDTH), eps);
            assertEquals(perimeter, properties.value(Property.WETTED_PERIMETER), eps);
            assertEquals(depth, properties.value(Property.HYDRAULIC_DEPTH), eps);
            assertEquals(radius, properties.value(Property.HYDRAULIC_RADIUS), eps);
            assertEquals(conveyance, properties.value(Property.CONVEYANCE), eps);
            assertEquals(1, properties.value(Property.VELOCITY_COEFF), eps);
            assertEquals(criticalFlow, properties.value(Property.CRITICAL_FLOW), eps);

        }

        illegalArgumentExceptionCaught = false;
        try {
            properties = crossSection.properties(Double.NaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

    }

    @Test
    public void testNormalY() {

        double roughness = 0.030;
        double depth = 10;
        double width = 10;
        double slope = 0.001;
        double area = depth * width;
        double perimeter = 2 * depth + width;
        double radius = area / perimeter;
        double eps = 0.003;

        boolean illegalArgumentExceptionCaught;

        double normalFlow = Constants.manningK() / roughness * area * Math.pow(radius, 2. / 3.) * Math.sqrt(slope);

        RectangularCrossSection crossSection = new RectangularCrossSection(width, roughness);
        assertEquals(depth, crossSection.normalY(normalFlow, slope), eps);

        /* test invalid zero values */
        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(0, slope);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(normalFlow, 0);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(0, 0);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* test invalid non-finite values */
        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(Double.NaN, slope);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(normalFlow, Double.NaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(Double.NaN, Double.NaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* test normalY(double, double, double) for invalid zero values */
        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(0, slope, depth);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(normalFlow, 0, depth);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(0, 0, depth);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* test normalY(double, double, double) for invalid non-finite values */
        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(Double.NaN, slope, depth);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(normalFlow, Double.NaN, depth);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(Double.NaN, Double.NaN, depth);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* test normalY(double, double, double) for invalid y0 value */
        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(normalFlow, slope, 0);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection.normalY(normalFlow, slope, Double.NaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }
    }

    @Test
    public void testCriticalY() {

        double roughness = 0.030;
        double depth = 5;
        double width = 10;

        double eps = 0.003;

        boolean illegalArgumentExceptionCaught;

        RectangularCrossSection crossSection = new RectangularCrossSection(width, roughness);
        CrossSectionProperties properties = crossSection.properties(depth);
        double criticalFlow = properties.value(Property.CRITICAL_FLOW);

        assertEquals(depth, crossSection.criticalY(criticalFlow), eps);
        assertEquals(depth, crossSection.criticalY(criticalFlow, depth), eps);

        /* test invalid zero values */
        illegalArgumentExceptionCaught = false;
        try {
            crossSection.criticalY(0);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* test invalid non-finite values */
        illegalArgumentExceptionCaught = false;
        try {
            crossSection.criticalY(Double.NaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* test criticalY(double, double) for invalid zero values */
        illegalArgumentExceptionCaught = false;
        try {
            crossSection.criticalY(0, depth);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* test criticalY(double, double) for invalid non-finite values */
        illegalArgumentExceptionCaught = false;
        try {
            crossSection.criticalY(Double.NaN, depth);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* test criticalY(double, double) for invalid y0 values */
        illegalArgumentExceptionCaught = false;
        try {
            crossSection.criticalY(criticalFlow, Double.NaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            crossSection.criticalY(criticalFlow, -1);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }
    }

}
