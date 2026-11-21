package com.smartcar.comfort;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.model.Car;
import com.smartcar.model.Headlight;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Service controlling vehicle exterior illumination and beam modes.
 */
public class HeadlightControlSystem extends BaseCarSystem {
    private final Car car;

    public HeadlightControlSystem(Car car) {
        super("Exterior Lighting System");
        this.car = car;
    }

    /**
     * Interactively controls headlights.
     */
    public void headlightMenuInteractive() {
        Headlight lights = car.getHeadlights();
        System.out.println("\n--- HEADLIGHT & LIGHTING CONTROL ---");
        System.out.println("Headlights Status: " + (lights.isOn() ? "[ON]" : "[OFF]"));
        System.out.println("Active Beam Mode:  [" + lights.getBeamMode() + " BEAM]");

        System.out.println("1. Turn Headlights ON");
        System.out.println("2. Turn Headlights OFF");
        System.out.println("3. Switch Beam Mode (LOW / HIGH)");
        System.out.println("4. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 4);
        switch (choice) {
            case 1:
                lights.setOn(true);
                this.active = true;
                System.out.println("[LIGHTS]: LED Projector Headlights illuminated (ON).");
                SystemLogger.log("Headlights turned ON.");
                break;
            case 2:
                lights.setOn(false);
                this.active = false;
                System.out.println("[LIGHTS]: Headlights switched OFF.");
                SystemLogger.log("Headlights turned OFF.");
                break;
            case 3:
                if (!lights.isOn()) {
                    System.out.println("Headlights are currently OFF. Turn them ON first.");
                } else {
                    String current = lights.getBeamMode();
                    String newMode = "LOW".equals(current) ? "HIGH" : "LOW";
                    lights.setBeamMode(newMode);
                    System.out.println("[LIGHTS]: Beam switched to " + newMode + " BEAM.");
                    SystemLogger.log("Headlight beam changed to " + newMode);
                }
                break;
            case 4:
                break;
        }
    }
}
