package com.smartcar.engine;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.exception.SafetyViolationException;
import com.smartcar.model.Car;
import com.smartcar.model.Gear;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Service managing vehicle speed, acceleration, braking, and emergency stop simulation.
 */
public class SpeedControlSystem extends BaseCarSystem {
    private final Car car;

    public SpeedControlSystem(Car car) {
        super("Speed Control System");
        this.car = car;
        this.active = true;
    }

    /**
     * Updates current vehicle speed with safety validations.
     */
    public void adjustSpeedInteractive() {
        System.out.println("\n--- SPEED REGULATION SYSTEM ---");
        System.out.printf("Current Speed: %.1f km/h | Gear: %s | Engine: %s%n",
                car.getCurrentSpeed(), car.getCurrentGear(),
                car.getEngine().isRunning() ? "RUNNING" : "STOPPED");

        System.out.println("1. Accelerate / Set Speed (km/h)");
        System.out.println("2. Apply Brakes to Complete Stop (0 km/h)");
        System.out.println("3. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 3);
        try {
            if (choice == 1) {
                double targetSpeed = InputValidator.readDouble("Enter desired speed in km/h (0.0 to 220.0): ", 0.0, 220.0);
                setSpeed(targetSpeed);
            } else if (choice == 2) {
                applyBrakes();
            }
        } catch (SafetyViolationException e) {
            System.out.println("\n[SPEED VIOLATION] " + e.getMessage());
            SystemLogger.logWarning("Speed adjustment blocked: " + e.getMessage());
        }
    }

    /**
     * Safely sets vehicle speed.
     */
    public void setSpeed(double targetSpeed) throws SafetyViolationException {
        if (targetSpeed > 0.0) {
            if (!car.getEngine().isRunning()) {
                throw new SafetyViolationException("Cannot accelerate: Engine is OFF!");
            }
            if (car.getCurrentGear() == Gear.PARK || car.getCurrentGear() == Gear.NEUTRAL) {
                throw new SafetyViolationException("Cannot move while in " + car.getCurrentGear() + " gear! Shift to DRIVE (D) or REVERSE (R).");
            }
        }

        car.setCurrentSpeed(targetSpeed);
        System.out.printf("Vehicle speed adjusted to: %.1f km/h%n", targetSpeed);
        SystemLogger.log("Vehicle speed updated to " + targetSpeed + " km/h.");

        // Simulate mileage consumption when driving
        if (targetSpeed > 0.0) {
            car.addKilometers(5.0); // Simulated trip increment
        }
    }

    /**
     * Applies brakes to bring the car to a full stop.
     */
    public void applyBrakes() {
        car.setCurrentSpeed(0.0);
        System.out.println("[BRAKES]: Brakes engaged. Vehicle brought to complete STOP (0.0 km/h).");
        SystemLogger.log("Vehicle stopped via brakes.");
    }

    /**
     * Performs an immediate emergency stop (used during collision or extreme hazard).
     */
    public void performEmergencyStop() {
        car.setCurrentSpeed(0.0);
        System.out.println("[EMERGENCY BRAKE]: ABS and Emergency Stop Activated!");
        SystemLogger.logAlert("Emergency stop executed.");
    }
}
