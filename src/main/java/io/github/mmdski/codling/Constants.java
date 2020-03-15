package io.github.mmdski.codling;

/**
 * Physical constants
 */
final public class Constants {
    static private double MANNING_K = 1;
    static private double GRAVITY = 9.81;

    private Constants() {
        ;
    }

    /**
     * Acceleration due to gravity
     *
     * @return Acceleration due to gravity
     */
    static public double gravity() {
        return GRAVITY;
    }

    /**
     * Unit conversion factor for Manning's n (k value)
     *
     * @return Manning's n unit conversion factor
     */
    static public double manningK() {
        return MANNING_K;
    }
}
