package com.smartcar.maintenance;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.model.Car;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Service schedule tracking and routine maintenance reminder.
 * Notifies driver when vehicle reaches or exceeds 10,000 km milestone.
 */
public class ServiceReminderSystem extends BaseCarSystem {
    public static final double SERVICE_INTERVAL_KM = 10000.0;
    private final Car car;

    public ServiceReminderSystem(Car car) {
        super("Maintenance & Service Reminder System");
        this.car = car;
        this.active = true;
    }

    /**
     * Interactively checks and manages service schedule.
     */
    public void serviceReminderInteractive() {
        System.out.println("\n--- VEHICLE SERVICE SCHEDULE & REMINDER ---");
        System.out.printf("Driven Since Last Service: %,.1f km%n", car.getKilometersSinceLastService());
        System.out.printf("Service Maintenance Threshold: %,.1f km%n", SERVICE_INTERVAL_KM);

        evaluateServiceStatus();

        System.out.println("\n1. Update Kilometers Driven Since Last Service");
        System.out.println("2. Reset Service Counter (Post-Service Routine)");
        System.out.println("3. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 3);
        if (choice == 1) {
            double km = InputValidator.readDouble("Enter kilometers driven since last service (0 to 100,000): ", 0.0, 100000.0);
            car.setKilometersSinceLastService(km);
            evaluateServiceStatus();
            SystemLogger.log("Service odometer updated to " + km + " km.");
        } else if (choice == 2) {
            car.setKilometersSinceLastService(0.0);
            System.out.println("\n[SERVICE CENTER]: Oil replaced, filters cleaned, full diagnostic passed.");
            System.out.println("Service counter successfully reset to 0.0 km. Next service at 10,000 km.");
            SystemLogger.log("Vehicle service counter reset to 0 km.");
        }
    }

    /**
     * Checks if service distance threshold has been crossed.
     */
    public void evaluateServiceStatus() {
        double driven = car.getKilometersSinceLastService();
        if (driven >= SERVICE_INTERVAL_KM) {
            double overdue = driven - SERVICE_INTERVAL_KM;
            System.out.println("\n[!] SERVICE DUE REMINDER [!]");
            System.out.printf("Vehicle has reached %,.1f km (overdue by %,.1f km)!%n", driven, overdue);
            System.out.println("Action Required: Engine oil change, brake inspection, and air filter replacement.");
            SystemLogger.logWarning("Vehicle service overdue at " + driven + " km.");
        } else {
            double remaining = SERVICE_INTERVAL_KM - driven;
            System.out.printf("Service Status: HEALTHY. %,.1f km remaining until next routine maintenance.%n", remaining);
        }
    }
}
