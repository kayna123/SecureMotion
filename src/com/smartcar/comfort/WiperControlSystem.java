package com.smartcar.comfort;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.model.Car;
import com.smartcar.model.Wiper;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Service managing front and rear windshield wipers and intermittent speeds.
 */
public class WiperControlSystem extends BaseCarSystem {
    private final Car car;

    public WiperControlSystem(Car car) {
        super("Windshield Wiper System");
        this.car = car;
    }

    /**
     * Interactively controls front windshield wiper.
     */
    public void frontWiperMenuInteractive() {
        controlWiperInteractive(car.getFrontWiper(), "FRONT");
    }

    /**
     * Interactively controls rear windshield wiper.
     */
    public void rearWiperMenuInteractive() {
        controlWiperInteractive(car.getRearWiper(), "REAR");
    }

    private void controlWiperInteractive(Wiper wiper, String label) {
        System.out.println("\n--- " + label + " WIPER CONTROL ---");
        System.out.println("Status:       " + (wiper.isOn() ? "[SWIPING]" : "[PARKED / OFF]"));
        System.out.println("Speed Setting: " + wiper.getSpeedLevel());

        System.out.println("1. Turn Wiper OFF");
        System.out.println("2. Set Wiper Speed: LOW (Intermittent)");
        System.out.println("3. Set Wiper Speed: MEDIUM (Normal Rain)");
        System.out.println("4. Set Wiper Speed: HIGH (Heavy Rain)");
        System.out.println("5. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 5);
        switch (choice) {
            case 1:
                wiper.setOn(false);
                wiper.setSpeedLevel("OFF");
                System.out.println("[" + label + " WIPER]: Blades parked (OFF).");
                SystemLogger.log(label + " wiper switched OFF.");
                break;
            case 2:
                wiper.setOn(true);
                wiper.setSpeedLevel("LOW");
                System.out.println("[" + label + " WIPER]: Wiping at LOW speed.");
                SystemLogger.log(label + " wiper set to LOW.");
                break;
            case 3:
                wiper.setOn(true);
                wiper.setSpeedLevel("MEDIUM");
                System.out.println("[" + label + " WIPER]: Wiping at MEDIUM speed.");
                SystemLogger.log(label + " wiper set to MEDIUM.");
                break;
            case 4:
                wiper.setOn(true);
                wiper.setSpeedLevel("HIGH");
                System.out.println("[" + label + " WIPER]: Wiping rapidly at HIGH speed!");
                SystemLogger.log(label + " wiper set to HIGH.");
                break;
            case 5:
                break;
        }
    }
}
