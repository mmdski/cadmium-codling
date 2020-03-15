package io.github.mmdski.codling;

import io.github.mmdski.codling.crosssection.CrossSection;
import io.github.mmdski.codling.crosssection.CrossSectionProperties;

public class ReachNodeTestCrossSection implements CrossSection {

    public double criticalY(double Q) {
        return 1;
    }

    public double criticalY(double Q, double y0) {
        return 1;
    }

    public double normalY(double Q, double S) {
        return 1;
    }

    public double normalY(double Q, double S, double y0) {
        return 1;
    }

    public CrossSectionProperties properties(double y) {

        CrossSectionProperties properties = new CrossSectionProperties();

        for (CrossSectionProperties.Property p : CrossSectionProperties.Property.values()) {
            properties.setValue(p, 1);
        }

        return properties;
    }

    public double thalweg() {
        return 0;
    }

}
