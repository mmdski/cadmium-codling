package io.github.mmdski.codling.crosssection;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for coordinate
 */
public class CoordinateTest {

    /**
     * Test coordinate construction
     */
    @Test
    public void testConstruction() {

        double y = 0;
        double z = 0;
        double delta = 0;

        Coordinate c = new Coordinate(y, z);
        assertEquals(y, c.y(), delta);
        assertEquals(z, c.z(), delta);

        /* test copy constructor */
        Coordinate c1 = new Coordinate(c);
        assertEquals(c.y(), c1.y(), delta);
        assertEquals(c.z(), c1.z(), delta);

        /* test constructor fail */
        boolean illegalArgumentExceptionCaught = false;

        Coordinate c2 = null;

        try {
            c2 = new Coordinate(y, Double.NaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            c2 = new Coordinate(Double.NaN, z);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            c2 = new Coordinate(Double.NaN, Double.NaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        // test copy constructor fail
        illegalArgumentExceptionCaught = false;
        try {
            c2 = new Coordinate(null);
        } catch (NullPointerException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* suppress warnings */
        if (c2 == null)
            ;
    }

    /**
     * Test y interpolation with z value
     */
    @Test
    public void testInterpY() {

        double zInterp = 0.25;
        double expectedY = 0.5;
        double delta = 0;

        Coordinate c1 = new Coordinate(0, 0);
        Coordinate c2 = new Coordinate(1, 0.5);

        Coordinate c1_interp = c1.interpY(c2, zInterp);
        assertEquals(expectedY, c1_interp.y(), delta);
        assertEquals(zInterp, c1_interp.z(), delta);

        Coordinate c2_interp = c2.interpY(c1, zInterp);
        assertEquals(expectedY, c2_interp.y(), delta);
        assertEquals(zInterp, c2_interp.z(), delta);

        Coordinate c = null;
        boolean illegalArgumentExceptionCaught = false;
        try {
            c = c1.interpY(c2, -1);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            c = c1.interpY(c2, 2);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            c = c2.interpY(c1, -1);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            c = c2.interpY(c1, 2);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* test null coordinate */
        boolean nullPointerExceptionCaught = false;
        try {
            c = c1.interpY(null, zInterp);
        } catch (NullPointerException e) {
            nullPointerExceptionCaught = true;
        } finally {
            assertTrue(nullPointerExceptionCaught);
        }

        /* suppress warnings */
        if (c == null)
            ;
    }

    @Test
    public void testInterpZ() {

        double expectedZ = 0.25;
        double yInterp = 0.5;
        double delta = 0;

        Coordinate c1 = new Coordinate(0, 0);
        Coordinate c2 = new Coordinate(1, 0.5);

        Coordinate c1_interp = c1.interpZ(c2, yInterp);
        assertEquals(expectedZ, c1_interp.z(), delta);
        assertEquals(yInterp, c1_interp.y(), delta);

        Coordinate c2_interp = c2.interpZ(c1, yInterp);
        assertEquals(expectedZ, c2_interp.z(), delta);
        assertEquals(yInterp, c2_interp.y(), delta);

        boolean illegalArgumentExceptionCaught = false;
        Coordinate c = null;
        try {
            c = c1.interpZ(c2, -1);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            c = c1.interpZ(c2, 2);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            c = c2.interpZ(c1, -1);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            c = c2.interpZ(c1, 2);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        boolean nullPointerExceptionCaught = false;
        try {
            c = c1.interpZ(null, yInterp);
        } catch (NullPointerException e) {
            nullPointerExceptionCaught = true;
        } finally {
            assertTrue(nullPointerExceptionCaught);
        }

        /* suppress warnings */
        if (c == null)
            ;
    }

}
