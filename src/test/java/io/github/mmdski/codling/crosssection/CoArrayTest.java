package io.github.mmdski.codling.crosssection;

import org.junit.Test;
import static org.junit.Assert.*;

public class CoArrayTest {

    /**
     * Test addY method
     */
    @Test
    public void testAddY() {

        double[] y = { 1, 1.5, 1.25, 1 };
        double[] z = { 0, 0.25, 0.5, 1 };
        double[] expectedY = { 2, 2.5, 2.25, 2 };
        double delta = 0;
        double addY = 1;

        CoArray ca = new CoArray(y, z);

        CoArray c = ca.addY(addY);
        assertArrayEquals(expectedY, c.y(), delta);
        assertArrayEquals(z, c.z(), delta);
        assertEquals(ca.minY() + addY, c.minY(), delta);

        boolean illegalArgumentExceptionCaught;
        c = null;
        illegalArgumentExceptionCaught = false;
        try {
            c = ca.addY(Double.NaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }
    }

    /**
     * Test construction
     */
    @Test
    public void testConstruction() {

        double[] y = { 1, 1.5, 1.25, 1 };
        double[] z = { 0, 0.25, 0.5, 1 };
        double delta = 0;

        CoArray ca = new CoArray(y, z);

        assertArrayEquals(y, ca.y(), delta);
        assertArrayEquals(z, ca.z(), delta);
        assertEquals(1, ca.minY(), delta);

        /* test copy constructor */
        CoArray c = new CoArray(ca);
        assertArrayEquals(ca.y(), c.y(), delta);
        assertArrayEquals(ca.z(), c.z(), delta);
        assertEquals(ca.minY(), c.minY(), delta);

        c = null;

        boolean illegalArgumentExceptionCaught;
        boolean nullPointerExceptionCaught;

        // test failure of short array
        double[] yShort = { 0 };
        double[] zShort = { 0 };

        illegalArgumentExceptionCaught = false;
        try {
            c = new CoArray(y, zShort);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            c = new CoArray(yShort, z);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            c = new CoArray(yShort, zShort);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        // test failure of null arrays
        double[] yNull = null;
        double[] zNull = null;

        nullPointerExceptionCaught = false;
        try {
            c = new CoArray(yNull, z);
        } catch (NullPointerException e) {
            nullPointerExceptionCaught = true;
        } finally {
            assertTrue(nullPointerExceptionCaught);
        }

        nullPointerExceptionCaught = false;
        try {
            c = new CoArray(y, zNull);
        } catch (NullPointerException e) {
            nullPointerExceptionCaught = true;
        } finally {
            assertTrue(nullPointerExceptionCaught);
        }

        nullPointerExceptionCaught = false;
        try {
            c = new CoArray(yNull, zNull);
        } catch (NullPointerException e) {
            nullPointerExceptionCaught = true;
        } finally {
            assertTrue(nullPointerExceptionCaught);
        }

        // test failure of out of order z
        double[] zOrder = { 0, 0.5, 0.25, 1 };

        illegalArgumentExceptionCaught = false;
        try {
            c = new CoArray(y, zOrder);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        // test zero-width z
        double[] zZero = { 1, 1, 1, 1 };

        illegalArgumentExceptionCaught = false;
        try {
            c = new CoArray(y, zZero);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* test failure when y and z are not equal */
        double[] yThree = { 1, 1.5, 1.25 };
        double[] zThree = { 0, 0.25, 0.5 };

        illegalArgumentExceptionCaught = true;
        try {
            c = new CoArray(y, zThree);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = true;
        try {
            c = new CoArray(yThree, z);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        /* test null argument to copy constructor */
        nullPointerExceptionCaught = true;
        try {
            c = new CoArray(null);
        } catch (NullPointerException e) {
            nullPointerExceptionCaught = true;
        } finally {
            assertTrue(nullPointerExceptionCaught);
        }

        // suppress warnings
        if (c == null)
            ;
    }

    /**
     * Test successful subArray call
     */
    @Test
    public void testSubArray() {

        double[] y = { 1.5, 1, 1, 1.5 };
        double[] z = { 0, 1, 2, 3 };
        double zLo = 0.5;
        double zHi = 2.5;

        double[] yExpected = { 1.25, 1, 1, 1.25 };
        double[] zExpected = { 0.5, 1, 2, 2.5 };
        double delta = 0;

        CoArray ca = new CoArray(y, z);
        CoArray subArray = ca.subArray(zLo, zHi);

        assertArrayEquals(yExpected, subArray.y(), delta);
        assertArrayEquals(zExpected, subArray.z(), delta);
        assertEquals(1, subArray.minY(), delta);

        subArray = null;

        /* test the high-end of mid for findLow coverage */
        double zLoHi = 2;
        double[] yExpectedHi = { 1, 1.25 };
        double[] zExpectedHi = { 2, 2.5 };

        subArray = ca.subArray(zLoHi, zHi);

        assertArrayEquals(yExpectedHi, subArray.y(), delta);
        assertArrayEquals(zExpectedHi, subArray.z(), delta);
    }

    /**
     * Test calls to subArray that throw IllegalArgumentException
     */
    @Test
    public void testSubArrayIllegalArgument() {

        boolean illegalArgumentExceptionCaught;
        double[] y = { 1.5, 1, 1, 1.5 };
        double[] z = { 0, 1, 2, 3 };
        double zLo = 0.5;
        double zHi = 2.5;

        CoArray subArray = null;
        CoArray ca = new CoArray(y, z);

        // test equal zLo
        illegalArgumentExceptionCaught = false;
        try {
            subArray = ca.subArray(zLo, zLo);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        // test zLo and zHi less than smallest z
        double zLoLo = -2;
        double zHiLo = -1;

        illegalArgumentExceptionCaught = false;
        try {
            subArray = ca.subArray(zLoLo, zHiLo);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        // test zLo less than smallest z, zHi less than highest z
        illegalArgumentExceptionCaught = false;
        try {
            subArray = ca.subArray(zLoLo, zHi);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        double zHiHi = 5;

        // test zLo greater than smallest z, zHi less than highest zsubArray
        illegalArgumentExceptionCaught = false;
        try {
            subArray = ca.subArray(zLo, zHiHi);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        // test zLo and zHi as NaN
        double zLoNaN = Double.NaN;
        double zHiNaN = Double.NaN;

        illegalArgumentExceptionCaught = false;
        try {
            subArray = ca.subArray(zLoNaN, zHi);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            subArray = ca.subArray(zLo, zHiNaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        illegalArgumentExceptionCaught = false;
        try {
            subArray = ca.subArray(zLoNaN, zHiNaN);
        } catch (IllegalArgumentException e) {
            illegalArgumentExceptionCaught = true;
        } finally {
            assertTrue(illegalArgumentExceptionCaught);
        }

        // suppress warnings
        if (subArray == null)
            ;
    }

    /**
     * Test the subArray method with the last coordinate as the minY
     */
    @Test
    public void testSubArrayLastMin() {

        double[] y = { 3, 2, 1, 3 };
        double[] z = { 0, 1, 2, 3 };
        double[] yExpected = { 3, 2, 1 };
        double[] zExpected = { 0, 1, 2 };
        double delta = 0;

        double zLo = 0;
        double zHi = 2;

        CoArray c = new CoArray(y, z);
        CoArray subArray = c.subArray(zLo, zHi);

        assertArrayEquals(yExpected, subArray.y(), delta);
        assertArrayEquals(zExpected, subArray.z(), delta);
        assertEquals(1, subArray.minY(), delta);
    }
}
