package com.smartcar.maintenance;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.model.Car;
import com.smartcar.model.Tyre;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Tyre Pressure Monitoring System (TPMS).
 * Reads and validates 4-wheel pressures (30-35 PSI recommended).
 * Demonstrates 1D and 2D Array handling from the course plan.
 */
public class TyrePressureMonitoringSystem extends BaseCarSystem {
    private final Car car;

    public TyrePressureMonitoringSystem(Car car) {
        super("Tyre Pressure Monitoring System (TPMS)");
        this.car = car;
        this.active = true;
    }

    /**
     * Interactively prompts user for 4 tyre pressures and evaluates safety.
     */
    public void checkTyrePressureInteractive() {
        System.out.println("\n--- TYRE PRESSURE MONITORING SYSTEM (TPMS) ---");
        System.out.println("Standard Recommended Range: 30.0 PSI to 35.0 PSI");

        Tyre[] tyres = car.getTyres();

        System.out.println("\n1. View Current 4-Tyre Pressure Status");
        System.out.println("2. Enter / Calibrate Pressures for All 4 Tyres");
        System.out.println("3. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 3);
        if (choice == 1) {
            displayTyreMatrix();
        } else if (choice == 2) {
            inputAllTyrePressures();
            displayTyreMatrix();
        }
    }

    private void inputAllTyrePressures() {
        Tyre[] tyres = car.getTyres();
        System.out.println("\nPlease enter tyre pressure for all four tyres:");
        for (Tyre tyre : tyres) {
            double psi = InputValidator.readDouble("Enter " + tyre.getPosition() + " Tyre Pressure (PSI) [10 to 60]: ", 10.0, 60.0);
            tyre.setCurrentPressurePsi(psi);
        }
        SystemLogger.log("Tyre pressures updated manually via TPMS.");
    }

    /**
     * Demonstrates 2D array representation of the 4-wheel vehicle layout.
     */
    public void displayTyreMatrix() {
        Tyre[] tyres = car.getTyres();
        // 2D Array layout representing [Axle][Side]:
        // Row 0: Front Axle (Left, Right)
        // Row 1: Rear Axle (Left, Right)
        Tyre[][] tyreLayout = new Tyre[][] {
            { tyres[0], tyres[1] },
            { tyres[2], tyres[3] }
        };

        System.out.println("\n================ 4-WHEEL TPMS TELEMETRY ================");
        for (int row = 0; row < tyreLayout.length; row++) {
            String axle = (row == 0) ? "FRONT AXLE" : "REAR AXLE ";
            System.out.println(" [" + axle + "]");
            for (int col = 0; col < tyreLayout[row].length; col++) {
                Tyre t = tyreLayout[row][col];
                System.out.printf("   %-12s : %5.1f PSI  -->  [%s]%n",
                        t.getPosition(),
                        t.getCurrentPressurePsi(),
                        t.getPressureStatus());
            }
        }
        System.out.println("========================================================");

        // Check if any tyre needs attention
        boolean warningFound = false;
        for (Tyre t : tyres) {
            if (!t.isNormalPressure()) {
                warningFound = true;
                System.out.println("[!] ALERT: " + t.getPosition() + " has " + t.getPressureStatus() + " (" + t.getCurrentPressurePsi() + " PSI)!");
                SystemLogger.logWarning(t.getPosition() + " " + t.getPressureStatus());
            }
        }
        if (!warningFound) {
            System.out.println("[STATUS]: All 4 tyres are within optimal operating pressure (30-35 PSI).");
        }
    }
}
