package com.smartcar.safety;

import com.smartcar.common.SafetyFeature;
import com.smartcar.exception.SafetyViolationException;
import com.smartcar.model.Car;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Safety subsystem managing seatbelt detection and fastening status.
 * Extends SafetyFeature and enforces buckle compliance.
 */
public class SeatbeltSystem extends SafetyFeature {
    private final Car car;

    public SeatbeltSystem(Car car) {
        super("Seatbelt Monitoring System");
        this.car = car;
        this.active = true;
    }

    @Override
    public void validateSafetyCondition() throws SafetyViolationException {
        if (!car.getDriverSeatbelt().isFastened()) {
            this.alertTriggered = true;
            throw new SafetyViolationException("WARNING: Driver seatbelt is unfastened! Buckle up immediately for safety.");
        }
        this.alertTriggered = false;
    }

    /**
     * Interactively checks and manages seatbelt status.
     */
    public void seatbeltMenuInteractive() {
        System.out.println("\n--- SEATBELT DETECTION SYSTEM ---");
        boolean fastened = car.getDriverSeatbelt().isFastened();
        System.out.println("Current Driver Seatbelt Status: " + (fastened ? "[FASTENED - SAFE]" : "[UNFASTENED - ALERT]"));

        if (!fastened) {
            System.out.println("[!] SEATBELT WARNING CHIME: *DING DING DING*");
            System.out.println("    Please fasten seatbelt to ensure passive safety.");
        }

        System.out.println("1. Fasten Seatbelt");
        System.out.println("2. Unfasten Seatbelt");
        System.out.println("3. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 3);
        if (choice == 1) {
            fastenSeatbelt();
        } else if (choice == 2) {
            unfastenSeatbelt();
        }
    }

    public void fastenSeatbelt() {
        car.getDriverSeatbelt().setFastened(true);
        this.alertTriggered = false;
        System.out.println("\n[CLICK]: Seatbelt securely fastened! Safety icon turns GREEN.");
        SystemLogger.log("Driver seatbelt fastened.");
    }

    public void unfastenSeatbelt() {
        car.getDriverSeatbelt().setFastened(false);
        this.alertTriggered = true;
        System.out.println("\n[UNCLICK]: Seatbelt unfastened! Warning light turns RED.");
        SystemLogger.logWarning("Driver unfastened seatbelt.");
    }
}
