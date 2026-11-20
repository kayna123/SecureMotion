package com.smartcar.model;

/**
 * Model class representing the car's fuel tank and levels.
 * Enforces business constraints (0% to 100%).
 */
public class FuelTank {
    public static final double LOW_FUEL_THRESHOLD = 15.0; // static constant
    private double fuelPercentage;
    private final double totalCapacityLitres;

    public FuelTank() {
        this.fuelPercentage = 65.0; // Default demo level
        this.totalCapacityLitres = 45.0;
    }

    public FuelTank(double initialPercentage, double capacityLitres) {
        this.totalCapacityLitres = capacityLitres;
        setFuelPercentage(initialPercentage);
    }

    public double getFuelPercentage() {
        return this.fuelPercentage;
    }

    public void setFuelPercentage(double percentage) {
        if (percentage < 0.0) {
            this.fuelPercentage = 0.0;
        } else if (percentage > 100.0) {
            this.fuelPercentage = 100.0;
        } else {
            this.fuelPercentage = percentage;
        }
    }

    public boolean isLowFuel() {
        return this.fuelPercentage <= LOW_FUEL_THRESHOLD;
    }

    public boolean isEmpty() {
        return this.fuelPercentage <= 0.0;
    }

    public double getRemainingLitres() {
        return (this.fuelPercentage / 100.0) * this.totalCapacityLitres;
    }

    public double getTotalCapacityLitres() {
        return this.totalCapacityLitres;
    }
}
