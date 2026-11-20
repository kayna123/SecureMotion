package com.smartcar.engine;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.model.Car;
import com.smartcar.model.FuelTank;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Service for fuel level monitoring, alerts, and simulated refueling.
 */
public class FuelMonitoringSystem extends BaseCarSystem {
    private final Car car;

    public FuelMonitoringSystem(Car car) {
        super("Fuel Monitoring System");
        this.car = car;
        this.active = true;
    }

    /**
     * Interactively updates and displays fuel status.
     */
    public void monitorFuelInteractive() {
        System.out.println("\n--- FUEL MONITORING & MANAGEMENT ---");
        System.out.printf("Current Fuel Level: %.1f%% (%.1f Litres / %.1f L)%n",
                car.getFuelTank().getFuelPercentage(),
                car.getFuelTank().getRemainingLitres(),
                car.getFuelTank().getTotalCapacityLitres());

        System.out.println("1. Update / Set Current Fuel Level (%)");
        System.out.println("2. Refuel Car to 100%");
        System.out.println("3. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 3);
        if (choice == 1) {
            double percent = InputValidator.readDouble("Enter current fuel percentage (0.0 to 100.0): ", 0.0, 100.0);
            car.getFuelTank().setFuelPercentage(percent);
            evaluateFuelStatus();
        } else if (choice == 2) {
            car.getFuelTank().setFuelPercentage(100.0);
            System.out.println("Fuel tank completely filled to 100% (Full Tank)!");
            SystemLogger.log("Vehicle refueled to 100%.");
        }
    }

    /**
     * Evaluates current fuel level and triggers warnings if below 15%.
     */
    public void evaluateFuelStatus() {
        FuelTank tank = car.getFuelTank();
        double percent = tank.getFuelPercentage();

        if (tank.isEmpty()) {
            System.out.println("\n[!] CRITICAL WARNING: FUEL TANK IS EMPTY (0.0%)!");
            System.out.println("Engine cannot start. Please refuel immediately.");
            SystemLogger.logAlert("Fuel tank empty (0%).");
            if (car.getEngine().isRunning()) {
                car.getEngine().setRunning(false);
                car.setCurrentSpeed(0.0);
                System.out.println("[!] Engine stalled due to fuel exhaustion.");
            }
        } else if (tank.isLowFuel()) {
            System.out.println("\n[!] LOW FUEL WARNING: Fuel level is " + percent + "% (< 15.0%)!");
            System.out.println("Please visit the nearest fuel station promptly.");
            SystemLogger.logWarning("Low fuel warning: " + percent + "% remaining.");
        } else {
            System.out.printf("Fuel Level Normal: %.1f%% (Estimated range: %.0f km)%n",
                    percent, percent * 6.5);
        }
    }
}
