package com.smartcar.model;

/**
 * Model class representing a single vehicle tyre.
 * Tracks location, current pressure, and evaluates safety thresholds.
 */
public class Tyre {
    public static final double MIN_RECOMMENDED_PSI = 30.0;
    public static final double MAX_RECOMMENDED_PSI = 35.0;

    private final String position;
    private double currentPressurePsi;

    public Tyre(String position, double initialPressure) {
        this.position = position;
        this.currentPressurePsi = initialPressure;
    }

    public String getPosition() {
        return this.position;
    }

    public double getCurrentPressurePsi() {
        return this.currentPressurePsi;
    }

    public void setCurrentPressurePsi(double currentPressurePsi) {
        this.currentPressurePsi = currentPressurePsi;
    }

    public boolean isLowPressure() {
        return this.currentPressurePsi < MIN_RECOMMENDED_PSI;
    }

    public boolean isHighPressure() {
        return this.currentPressurePsi > MAX_RECOMMENDED_PSI;
    }

    public boolean isNormalPressure() {
        return !isLowPressure() && !isHighPressure();
    }

    public String getPressureStatus() {
        if (isLowPressure()) {
            return "Low Pressure Warning!";
        } else if (isHighPressure()) {
            return "High Pressure Warning!";
        } else {
            return "Normal";
        }
    }
}
