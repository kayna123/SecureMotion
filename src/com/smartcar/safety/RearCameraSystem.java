package com.smartcar.safety;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.model.Car;
import com.smartcar.model.Gear;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Service simulating the car's backup / rear-view camera.
 * Operates when transmission is in Reverse and engine is running.
 */
public class RearCameraSystem extends BaseCarSystem {
    private final Car car;

    public RearCameraSystem(Car car) {
        super("Rear-View Camera System");
        this.car = car;
        this.active = false;
    }

    /**
     * Interactively tests the rear camera.
     */
    public void activateRearCameraInteractive() {
        System.out.println("\n--- REAR-VIEW CAMERA FEED SIMULATION ---");

        if (!car.getEngine().isRunning()) {
            System.out.println("[CAMERA ERROR]: Engine is OFF. Camera system requires auxiliary engine power.");
            return;
        }

        if (car.getCurrentGear() != Gear.REVERSE) {
            System.out.println("[NOTICE]: Current Gear is " + car.getCurrentGear() + ".");
            System.out.println("Rear-view camera automatically engages when REVERSE (R) gear is selected.");
            boolean shift = InputValidator.readBoolean("Would you like to shift to REVERSE (R) now?");
            if (shift) {
                if (car.getCurrentSpeed() > 0.0) {
                    System.out.println("Cannot shift to REVERSE while moving forward! Stop first.");
                    return;
                }
                car.setCurrentGear(Gear.REVERSE);
                System.out.println("Shifted to REVERSE. Camera engaging...");
            } else {
                System.out.println("Camera test aborted.");
                return;
            }
        }

        // Camera feed display simulation
        this.active = true;
        renderCameraOverlay();

        boolean obstacleSeen = InputValidator.readBoolean("Is an obstacle visible in the simulated camera frame?");
        if (obstacleSeen) {
            System.out.println("\n[GUIDELINES RED ALERT]: Pedestrian / Object detected behind vehicle!");
            System.out.println("[!!!] DO NOT REVERSE! Apply brake pedal.");
            SystemLogger.logWarning("Rear camera obstacle flagged by driver.");
        } else {
            System.out.println("\n[GUIDELINES GREEN]: Path behind vehicle is CLEAR. Safe to reverse with caution.");
            SystemLogger.log("Rear camera path clear.");
        }
    }

    private void renderCameraOverlay() {
        System.out.println("+==================================================+");
        System.out.println("| [CAM 1 - REAR HD WIDE ANGLE 1080P]   [LIVE 30FPS]|");
        System.out.println("|                                                  |");
        System.out.println("|     |  /                            \\  |         |");
        System.out.println("|     | /     DYNAMIC TRAJECTORY LINES \\ |         |");
        System.out.println("|     |/         [ GREEN ZONE ]         \\|         |");
        System.out.println("|     |          [ YELLOW ZONE ]         |         |");
        System.out.println("|     |           [ RED STOP ]           |         |");
        System.out.println("+==================================================+");
    }
}
