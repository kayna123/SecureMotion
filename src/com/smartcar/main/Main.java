package com.smartcar.main;

import com.smartcar.controller.CarMenuController;
import com.smartcar.model.Car;
import com.smartcar.security.PinAuthenticationSystem;
import com.smartcar.util.StringFormatter;

/**
 * Main application entry point for Smart Car Safety and Control System.
 * Simulates automobile initialization, biometric/PIN access control, and vehicle run-state.
 */
public class Main {
    public static void main(String[] args) {
        // Print Initial Welcome Header
        System.out.println(StringFormatter.createHeader("SMART CAR SAFETY & CONTROL SYSTEM"));
        System.out.println("  Educational Automotive Simulation Platform (Core Java)");
        System.out.println("  Second-Year B.Tech Java Mini-Project (Chitkara University)\n");

        // Single shared car state across the entire simulation
        Car car = new Car();

        // Security Authentication
        PinAuthenticationSystem authSystem = new PinAuthenticationSystem(car);
        boolean isAuthenticated = authSystem.authenticateInteractive();

        if (isAuthenticated) {
            // Launch main car control menu
            CarMenuController controller = new CarMenuController(car);
            controller.startMenuLoop();
        } else {
            System.out.println("\n[SYSTEM TERMINATED]: Car immobilized due to security lockout.");
            System.out.println("Please contact authorized service center with vehicle ownership proof.");
        }
    }
}
