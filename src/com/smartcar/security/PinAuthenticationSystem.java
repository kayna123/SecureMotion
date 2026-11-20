package com.smartcar.security;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.exception.InvalidPinException;
import com.smartcar.model.Car;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Security authentication service requiring 4-digit PIN code.
 * Implements lockout logic after 3 failed attempts.
 */
public class PinAuthenticationSystem extends BaseCarSystem {
    public static final String DEFAULT_PIN = "1234";
    public static final int MAX_ATTEMPTS = 3;

    private final Car car;
    private String configuredPin;
    private int failedAttempts;
    private boolean lockedOut;

    public PinAuthenticationSystem(Car car) {
        super("PIN Security Authentication System");
        this.car = car;
        this.configuredPin = DEFAULT_PIN;
        this.failedAttempts = 0;
        this.lockedOut = false;
    }

    /**
     * Authenticates the user interactively before granting car access.
     * Returns true if unlocked, false if locked out.
     */
    public boolean authenticateInteractive() {
        System.out.println("====================================");
        System.out.println(" SMART CAR PIN SECURITY ACCESS");
        System.out.println(" (Default Demo PIN: 1234)");
        System.out.println("====================================");

        while (failedAttempts < MAX_ATTEMPTS) {
            String enteredPin = InputValidator.readString("Enter 4-Digit Car PIN: ");
            try {
                verifyPin(enteredPin);
                car.setUnlocked(true);
                this.failedAttempts = 0;
                System.out.println("\n>>> [AUTHENTICATION SUCCESS] <<<");
                System.out.println("Car Unlocked Successfully! Welcome aboard.");
                SystemLogger.log("Car unlocked via valid PIN.");
                return true;
            } catch (InvalidPinException e) {
                System.out.println("[ACCESS DENIED] " + e.getMessage());
                SystemLogger.logWarning("Failed unlock attempt: " + (MAX_ATTEMPTS - failedAttempts) + " attempts remain.");
                if (failedAttempts >= MAX_ATTEMPTS) {
                    this.lockedOut = true;
                    System.out.println("\n[!] SECURITY LOCKOUT TRIGGERED!");
                    System.out.println("Maximum attempts exceeded (3/3). Immobilizer active.");
                    SystemLogger.logAlert("Security lockout triggered after 3 bad PIN attempts.");
                    return false;
                }
            }
        }
        return false;
    }

    public void verifyPin(String enteredPin) throws InvalidPinException {
        if (enteredPin == null || !enteredPin.equals(this.configuredPin)) {
            this.failedAttempts++;
            int remaining = MAX_ATTEMPTS - this.failedAttempts;
            throw new InvalidPinException("Incorrect PIN entered! Attempts left: " + remaining, remaining);
        }
    }

    public boolean isLockedOut() {
        return this.lockedOut;
    }

    public void resetLockout() {
        this.failedAttempts = 0;
        this.lockedOut = false;
    }
}
