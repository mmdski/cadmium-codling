package io.github.mmdski.codling;

import io.github.mmdski.codling.crosssection.CrossSection;

/**
 * Contains references to cross sections, longitudinal distances, and thalweg
 * elevations
 */
public class Reach {

    ReachNode[] reachNodes;

    /**
     * Constructs a new reach
     *
     * @param x                   Distance downstream of each node
     * @param thalweg             Thalweg elevation of each node
     * @param crossSectionNumbers Index of a cross section in
     *                            <code>crossSections</code> for each node
     * @param crossSections       Array of cross sections
     */
    public Reach(double[] x, double[] thalweg, int[] crossSectionNumbers, CrossSection[] crossSections) {

        /* these arrays must be the same length */
        int length = x.length;

        if (length != thalweg.length)
            throw new IllegalArgumentException();

        if (length != crossSectionNumbers.length)
            throw new IllegalArgumentException();

        if (length < 2)
            throw new IllegalArgumentException();

        CrossSection xs;
        reachNodes = new ReachNode[length];

        /*
         * fill first node. if an array index exception occurs, assume the cross section
         * index doesn't exist. throw an illegal argument exception
         */
        try {
            xs = crossSections[crossSectionNumbers[0]];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException();
        }
        reachNodes[0] = new ReachNode(x[0], thalweg[0], xs);

        for (int i = 1; i < length; i++) {

            /*
             * x must be defined in descending order. upstream to downstream
             */
            if (x[i] < x[i - 1])
                throw new IllegalArgumentException();

            try {
                xs = crossSections[crossSectionNumbers[i]];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new IllegalArgumentException();
            }
            reachNodes[i] = new ReachNode(x[i], thalweg[i], xs);
        }

    }

    /**
     * Returns the distance downstream of each node in this reach
     *
     * @return The distance downstream of all nodes
     */
    public double[] distanceDownstream() {

        int n = reachNodes.length;

        double[] distanceDownstream = new double[n];

        for (int i = 0; i < n; i++) {
            distanceDownstream[i] = reachNodes[i].distanceDownstream();
        }

        return distanceDownstream;
    }

    /**
     * Returns the number of nodes in this reach
     *
     * @return The number of nodes
     */
    public int length() {
        return reachNodes.length;
    }

    /**
     * Hydraulic properties of a reach node
     *
     * @param nodeIndex   Node index
     * @param discharge   Discharge for computing properties
     * @param wsElevation Water surface elevation for computing properties
     *
     * @return Hydraulic properties of a reach node
     */
    public ReachNodeProperties nodeProperties(int nodeIndex, double discharge, double wsElevation) {

        if (!Double.isFinite(discharge))
            throw new IllegalArgumentException();

        if (!Double.isFinite(wsElevation))
            throw new IllegalArgumentException();

        if (nodeIndex < 0 || nodeIndex > reachNodes.length - 1)
            throw new ArrayIndexOutOfBoundsException();

        double depth = wsElevation - reachNodes[nodeIndex].thalweg();

        return reachNodes[nodeIndex].properties(discharge, depth);
    }

    /**
     * Returns the thalweg elevations of all nodes in this reach
     *
     * @return Thalweg elevation of all nodes
     */
    public double[] thalweg() {

        int n = reachNodes.length;

        double[] thalweg = new double[n];

        for (int i = 0; i < n; i++) {
            thalweg[i] = reachNodes[i].thalweg();
        }

        return thalweg;
    }

}
