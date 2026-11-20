package com.smartcar.security;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.exception.SafetyViolationException;
import com.smartcar.model.Car;
import com.smartcar.model.Door;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Service managing central door locking and individual door latches.
 * Enforces automatic speed-sensing safety locks.
 */
public class DoorLockSystem extends BaseCarSystem {
    private final Car car;

    public DoorLockSystem(Car car) {
        super("Central Door Locking System");
        this.car = car;
    }

    /**
     * Interactively operates door locks.
     */
    public void doorLockMenuInteractive() {
        System.out.println("\n--- CENTRAL DOOR LOCKING SYSTEM ---");
        System.out.println("Overall Car Lock Status: " + (car.isUnlocked() ? "[UNLOCKED]" : "[LOCKED]"));

        System.out.println("\nIndividual Door Statuses:");
        for (Door d : car.getDoors()) {
            System.out.printf("  %-25s : %s | %s%n",
                    d.getPosition(),
                    d.isLocked() ? "LOCKED" : "UNLOCKED",
                    d.isOpen() ? "OPEN" : "CLOSED");
        }

        System.out.println("\n1. Unlock All Doors");
        System.out.println("2. Lock All Doors");
        System.out.println("3. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 3);
        try {
            if (choice == 1) {
                unlockAllDoors();
            } else if (choice == 2) {
                lockAllDoors();
            }
        } catch (SafetyViolationException e) {
            System.out.println("\n[DOOR SAFETY ERROR] " + e.getMessage());
            SystemLogger.logWarning("Door lock operation blocked: " + e.getMessage());
        }
    }

    public void unlockAllDoors() throws SafetyViolationException {
        if (car.getCurrentSpeed() > 0.0) {
            throw new SafetyViolationException("HAZARD: Unlocking doors while driving at " + car.getCurrentSpeed() + " km/h is prohibited by speed-sensing locks!");
        }
        car.setUnlocked(true);
        for (Door d : car.getDoors()) {
            d.setLocked(false);
        }
        System.out.println("\n[CENTRAL LOCKING]: *CLICK* All doors unlocked.");
        SystemLogger.log("All doors unlocked.");
    }

    public void lockAllDoors() {
        car.setUnlocked(false);
        for (Door d : car.getDoors()) {
            d.setLocked(true);
        }
        System.out.println("\n[CENTRAL LOCKING]: *THUD* All doors locked securely.");
        SystemLogger.log("All doors locked.");
    }
}
