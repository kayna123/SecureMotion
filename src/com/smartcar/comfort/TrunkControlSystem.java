package com.smartcar.comfort;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.exception.SafetyViolationException;
import com.smartcar.model.Car;
import com.smartcar.model.Trunk;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Service managing electronic trunk (dikki) open, close, and lock operations.
 * Enforces safety constraint preventing trunk opening while vehicle is in motion.
 */
public class TrunkControlSystem extends BaseCarSystem {
    private final Car car;

    public TrunkControlSystem(Car car) {
        super("Power Tailgate / Trunk System");
        this.car = car;
    }

    /**
     * Interactively operates the trunk.
     */
    public void trunkMenuInteractive() {
        Trunk trunk = car.getTrunk();
        System.out.println("\n--- CAR TRUNK (DIKKI) CONTROL ---");
        System.out.println("Trunk Status: " + (trunk.isOpen() ? "[OPEN / AJAR]" : "[CLOSED]"));
        System.out.println("Trunk Lock:   " + (trunk.isLocked() ? "[LOCKED]" : "[UNLOCKED]"));

        System.out.println("1. Open Trunk");
        System.out.println("2. Close Trunk");
        System.out.println("3. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 3);
        try {
            if (choice == 1) {
                openTrunk();
            } else if (choice == 2) {
                closeTrunk();
            }
        } catch (SafetyViolationException e) {
            System.out.println("\n[SAFETY LOCKOUT] " + e.getMessage());
            SystemLogger.logWarning("Trunk operation blocked: " + e.getMessage());
        }
    }

    public void openTrunk() throws SafetyViolationException {
        if (car.getCurrentSpeed() > 0.0) {
            throw new SafetyViolationException("CRITICAL SAFETY HAZARD: Cannot open trunk while vehicle is in motion at " + car.getCurrentSpeed() + " km/h!");
        }
        if (!car.isUnlocked() && car.getTrunk().isLocked()) {
            throw new SafetyViolationException("Trunk is securely locked. Unlock car first.");
        }

        car.getTrunk().setOpen(true);
        System.out.println("\n[TRUNK]: Motorized tailgate unlatched and OPENED.");
        SystemLogger.log("Trunk opened.");
    }

    public void closeTrunk() {
        car.getTrunk().setOpen(false);
        System.out.println("\n[TRUNK]: Tailgate pulled down and latched securely (CLOSED).");
        SystemLogger.log("Trunk closed.");
    }
}
