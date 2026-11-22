package com.smartcar.safety;

import com.smartcar.common.SafetyFeature;
import com.smartcar.exception.SafetyViolationException;
import com.smartcar.model.Car;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Safety subsystem that continuously monitors driving speed against speed thresholds.
 */
public class SpeedLimitSystem extends SafetyFeature {
    private final Car car;

    public SpeedLimitSystem(Car car) {
        super("Speed Limit Alert System");
        this.car = car;
        this.active = true;
    }

    @Override
    public void validateSafetyCondition() throws SafetyViolationException {
        if (car.getCurrentSpeed() > car.getSpeedLimit()) {
            this.alertTriggered = true;
            throw new SafetyViolationException(String.format(
                    "OVERSPEED VIOLATION! Speed %.1f km/h exceeds maximum configured limit of %.1f km/h!",
                    car.getCurrentSpeed(), car.getSpeedLimit()));
        }
        this.alertTriggered = false;
    }

    /**
     * Interactively checks or configures speed limit settings.
     */
    public void speedLimitMenuInteractive() {
        System.out.println("\n--- SPEED LIMIT MONITORING & CONFIGURATION ---");
        System.out.printf("Current Speed: %.1f km/h | Active Speed Limit: %.1f km/h%n",
                car.getCurrentSpeed(), car.getSpeedLimit());

        System.out.println("1. Test / Update Vehicle Speed");
        System.out.println("2. Configure Speed Limit Threshold");
        System.out.println("3. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 3);
        if (choice == 1) {
            double speed = InputValidator.readDouble("Enter vehicle speed in km/h: ", 0.0, 250.0);
            car.setCurrentSpeed(speed);
            checkSpeedThreshold();
        } else if (choice == 2) {
            double newLimit = InputValidator.readDouble("Enter new speed limit in km/h (40.0 to 180.0): ", 40.0, 180.0);
            car.setSpeedLimit(newLimit);
            System.out.printf("Speed limit updated to: %.1f km/h%n", newLimit);
            SystemLogger.log("Speed limit reconfigured to " + newLimit + " km/h.");
            checkSpeedThreshold();
        }
    }

    /**
     * Checks current speed against limit and prints alert if exceeded.
     */
    public void checkSpeedThreshold() {
        double current = car.getCurrentSpeed();
        double limit = car.getSpeedLimit();

        if (current > limit) {
            this.alertTriggered = true;
            double excess = current - limit;
            System.out.println("\n[!] OVERSPEED WARNING: Slow down immediately!");
            System.out.printf("Vehicle is traveling at %.1f km/h, which is %.1f km/h over the %.1f km/h limit!%n",
                    current, excess, limit);
            SystemLogger.logAlert(String.format("Speed limit exceeded! Speed: %.1f km/h, Limit: %.1f km/h", current, limit));
        } else {
            this.alertTriggered = false;
            System.out.printf("Speed Status: SAFE (%.1f km/h within limit of %.1f km/h)%n", current, limit);
        }
    }
}
