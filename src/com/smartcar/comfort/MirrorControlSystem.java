package com.smartcar.comfort;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.model.Car;
import com.smartcar.model.SideMirror;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Service managing power folding and angle positioning of side rearview mirrors.
 */
public class MirrorControlSystem extends BaseCarSystem {
    private final Car car;

    public MirrorControlSystem(Car car) {
        super("Power Mirror Control System");
        this.car = car;
        this.active = true;
    }

    /**
     * Interactively controls side mirrors.
     */
    public void mirrorMenuInteractive() {
        SideMirror left = car.getLeftMirror();
        SideMirror right = car.getRightMirror();

        System.out.println("\n--- SIDE MIRROR CONTROL SYSTEM ---");
        System.out.println("Left Mirror:  " + (left.isFolded() ? "[FOLDED]" : "[UNFOLDED / OPEN]") + " (Tilt: " + left.getTiltAngle() + " deg)");
        System.out.println("Right Mirror: " + (right.isFolded() ? "[FOLDED]" : "[UNFOLDED / OPEN]") + " (Tilt: " + right.getTiltAngle() + " deg)");

        System.out.println("1. Unfold / Open Both Mirrors");
        System.out.println("2. Fold Both Mirrors (Parking Mode)");
        System.out.println("3. Adjust Mirror Tilt Angle");
        System.out.println("4. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 4);
        switch (choice) {
            case 1:
                left.setFolded(false);
                right.setFolded(false);
                System.out.println("[MIRRORS]: Both side mirrors unfolded and extended.");
                SystemLogger.log("Side mirrors unfolded.");
                break;
            case 2:
                left.setFolded(true);
                right.setFolded(true);
                System.out.println("[MIRRORS]: Both side mirrors folded inward.");
                SystemLogger.log("Side mirrors folded.");
                break;
            case 3:
                int angle = InputValidator.readInt("Enter mirror tilt angle in degrees (0 to 45): ", 0, 45);
                left.setTiltAngle(angle);
                right.setTiltAngle(angle);
                System.out.println("[MIRRORS]: Both mirrors calibrated to " + angle + " deg angle.");
                break;
            case 4:
                break;
        }
    }
}
