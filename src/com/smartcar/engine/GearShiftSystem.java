package com.smartcar.engine;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.exception.SafetyViolationException;
import com.smartcar.model.Car;
import com.smartcar.model.Gear;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Service managing gear transitions and enforcing transmission safety locks.
 */
public class GearShiftSystem extends BaseCarSystem {
    private final Car car;

    public GearShiftSystem(Car car) {
        super("Gear Shift System");
        this.car = car;
        this.active = true;
    }

    /**
     * Interactively asks user which gear to shift into.
     */
    public void changeGearInteractive() {
        System.out.println("\n--- TRANSMISSION GEAR SELECTION ---");
        System.out.println("Current Gear: " + car.getCurrentGear());
        System.out.println("1. [P] - PARK (Vehicle Stationary)");
        System.out.println("2. [R] - REVERSE (Backing up)");
        System.out.println("3. [N] - NEUTRAL (Idling / Towing)");
        System.out.println("4. [D] - DRIVE (Forward driving)");
        System.out.println("5. Cancel");

        int choice = InputValidator.readInt("Select Gear Option (1-5): ", 1, 5);
        try {
            switch (choice) {
                case 1:
                    shiftToGear(Gear.PARK);
                    break;
                case 2:
                    shiftToGear(Gear.REVERSE);
                    break;
                case 3:
                    shiftToGear(Gear.NEUTRAL);
                    break;
                case 4:
                    shiftToGear(Gear.DRIVE);
                    break;
                case 5:
                    System.out.println("Gear change canceled.");
                    break;
            }
        } catch (SafetyViolationException e) {
            System.out.println("\n[TRANSMISSION ERROR] " + e.getMessage());
            SystemLogger.logWarning("Unsafe gear shift rejected: " + e.getMessage());
        }
    }

    /**
     * Shifts to requested gear validating safety locks.
     */
    public void shiftToGear(Gear targetGear) throws SafetyViolationException {
        if (car.getCurrentGear() == targetGear) {
            System.out.println("Already in gear: " + targetGear);
            return;
        }

        // Safety Condition 1: Must be stationary to shift into PARK or REVERSE
        if ((targetGear == Gear.PARK || targetGear == Gear.REVERSE) && car.getCurrentSpeed() > 0.0) {
            throw new SafetyViolationException("Cannot shift into " + targetGear.name() + " while moving at " + car.getCurrentSpeed() + " km/h! Bring car to complete stop (0 km/h) first.");
        }

        // Safety Condition 2: Engine must be running to engage DRIVE or REVERSE
        if ((targetGear == Gear.DRIVE || targetGear == Gear.REVERSE) && !car.getEngine().isRunning()) {
            throw new SafetyViolationException("Cannot shift into " + targetGear.name() + ": Engine is OFF! Start engine first.");
        }

        car.setCurrentGear(targetGear);
        System.out.println("\n[GEAR SHIFT]: Successfully shifted to " + targetGear.getDescription());
        SystemLogger.log("Gear shifted to " + targetGear);

        // If shifted to PARK, enforce speed is 0
        if (targetGear == Gear.PARK) {
            car.setCurrentSpeed(0.0);
        }
    }
}
