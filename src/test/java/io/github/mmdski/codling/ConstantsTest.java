package io.github.mmdski.codling;

import org.junit.Test;

import static org.junit.Assert.*;

public class ConstantsTest {

    @Test
    public void testManningK() {
        assertEquals(1, Constants.manningK(), 0);
    }

    @Test
    public void testGravity() {
        assertEquals(9.81, Constants.gravity(), 0);
    }
}
