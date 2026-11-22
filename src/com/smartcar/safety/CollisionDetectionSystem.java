package com.smartcar.safety;

import com.smartcar.common.SafetyFeature;
import com.smartcar.exception.SafetyViolationException;
import com.smartcar.model.Car;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Subsystem simulating dynamic impact sensors and collision mitigation response.
 */
public class CollisionDetectionSystem extends SafetyFeature {
    private final Car car;
    private final AirbagSystem airbagSystem;

    public CollisionDetectionSystem(Car car, AirbagSystem airbagSystem) {
        super("Collision Detection & Mitigation System");
        this.car = car;
        this.airbagSystem = airbagSystem;
        this.active = true;
    }

    @Override
    public void validateSafetyCondition() throws SafetyViolationException {
        if (car.getAirbag().isDeployed()) {
            throw new SafetyViolationException("Severe impact registered previously. Vehicle is disabled.");
        }
    }

    /**
     * Interactively tests collision scenarios.
     */
    public void simulateCollisionInteractive() {
        System.out.println("\n--- COLLISION DETECTION SENSOR SIMULATOR ---");
        System.out.printf("Current Speed: %.1f km/h%n", car.getCurrentSpeed());
        System.out.println("Select Simulated Impact Severity:");
        System.out.println("1. [NONE]   - Clear path / No obstacle detected");
        System.out.println("2. [MINOR]  - Minor bump / Low-speed contact (e.g. scratch, debris)");
        System.out.println("3. [SEVERE] - High-speed crash / Head-on collision");
        System.out.println("4. Cancel");

        int choice = InputValidator.readInt("Enter Impact Severity (1-4): ", 1, 4);
        switch (choice) {
            case 1:
                System.out.println("\n[RADAR]: Path is clear. Forward radar scan normal.");
                this.alertTriggered = false;
                break;
            case 2:
                handleMinorCollision();
                break;
            case 3:
                handleSevereCollision();
                break;
            case 4:
                System.out.println("Simulation canceled.");
                break;
        }
    }

    private void handleMinorCollision() {
        this.alertTriggered = true;
        System.out.println("\n[!] CAUTION: MINOR IMPACT DETECTED!");
        System.out.println("Bumper proximity sensors recorded minor contact.");
        System.out.println("Notice: Airbags WILL NOT deploy for minor bumps (preserving safety and cost).");
        System.out.println("Action: Please slow down and inspect vehicle exterior.");
        SystemLogger.logWarning("Minor vehicle contact detected.");
    }

    private void handleSevereCollision() {
        this.alertTriggered = true;
        System.out.println("\n[CRASH DETECTED]: HIGH G-FORCE IMPACT OCCURRED!");
        SystemLogger.logAlert("Severe collision detected!");

        // Trigger Airbags
        airbagSystem.deployAirbags();

        // Perform Emergency Stopping
        car.setCurrentSpeed(0.0);
        car.getEngine().setRunning(false);
        System.out.println("[AUTOMATIC RESPONSE]: Fuel pump shut off, engine stalled, emergency brakes locked.");
    }
}
