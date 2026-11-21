package com.smartcar.safety;

import com.smartcar.common.SafetyFeature;
import com.smartcar.exception.SafetyViolationException;
import com.smartcar.model.Car;
import com.smartcar.model.Door;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Safety subsystem managing electronic rear door child safety locks.
 */
public class ChildLockSystem extends SafetyFeature {
    private final Car car;

    public ChildLockSystem(Car car) {
        super("Child Safety Lock System");
        this.car = car;
        this.active = false;
    }

    @Override
    public void validateSafetyCondition() throws SafetyViolationException {
        // Child safety integrity validation
        if (car.getCurrentSpeed() > 0 && !car.isChildLockEnabled()) {
            SystemLogger.logWarning("Vehicle in motion without child lock enabled.");
        }
    }

    /**
     * Interactively configures child lock and allows testing rear door handles.
     */
    public void childLockMenuInteractive() {
        System.out.println("\n--- REAR DOOR CHILD SAFETY LOCK ---");
        System.out.println("Child Lock Status: " + (car.isChildLockEnabled() ? "[ENABLED]" : "[DISABLED]"));

        System.out.println("1. Enable Child Lock");
        System.out.println("2. Disable Child Lock");
        System.out.println("3. Test Opening Rear Door from INSIDE");
        System.out.println("4. Back to Main Menu");

        int choice = InputValidator.readInt("Enter Choice: ", 1, 4);
        switch (choice) {
            case 1:
                enableChildLock();
                break;
            case 2:
                disableChildLock();
                break;
            case 3:
                testRearDoorInsideHandle();
                break;
            case 4:
                break;
        }
    }

    public void enableChildLock() {
        car.setChildLockEnabled(true);
        this.active = true;
        System.out.println("\n[CHILD LOCK ACTIVATED]: Rear interior door handles disengaged.");
        System.out.println("Rear passengers (children) cannot open rear doors from inside.");
        SystemLogger.log("Child lock enabled.");
    }

    public void disableChildLock() {
        car.setChildLockEnabled(false);
        this.active = false;
        System.out.println("\n[CHILD LOCK DEACTIVATED]: Rear interior door handles restored.");
        SystemLogger.log("Child lock disabled.");
    }

    public void testRearDoorInsideHandle() {
        System.out.println("\n[TEST]: Passenger inside rear seat attempts to pull the door latch...");
        if (car.isChildLockEnabled()) {
            System.out.println("[BLOCKED]: Door handle moves freely with NO EFFECT. Door remains locked & closed.");
            System.out.println("Child lock prevented accidental opening while seated.");
        } else {
            Door rearDoor = car.getDoors()[2]; // Rear Left door
            if (car.getCurrentSpeed() > 0.0) {
                System.out.println("[DANGER]: Vehicle moving! Speed locks prevent opening.");
            } else {
                rearDoor.setOpen(true);
                System.out.println("[OPENED]: Rear door popped open from inside handle.");
                rearDoor.setOpen(false); // Close it back
            }
        }
    }
}
