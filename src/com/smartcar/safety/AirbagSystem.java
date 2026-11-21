package com.smartcar.safety;

import com.smartcar.common.SafetyFeature;
import com.smartcar.exception.SafetyViolationException;
import com.smartcar.model.Airbag;
import com.smartcar.model.Car;
import com.smartcar.util.StringFormatter;
import com.smartcar.util.SystemLogger;

/**
 * Safety subsystem managing supplemental restraint (SRS) airbag deployment.
 * Deploys exclusively during severe impacts; prohibited on minor obstacles.
 */
public class AirbagSystem extends SafetyFeature {
    private final Car car;

    public AirbagSystem(Car car) {
        super("SRS Airbag Deployment System");
        this.car = car;
        this.active = true;
    }

    @Override
    public void validateSafetyCondition() throws SafetyViolationException {
        if (car.getAirbag().isDeployed()) {
            throw new SafetyViolationException("Airbags already deployed! Vehicle requires towing and professional servicing.");
        }
    }

    /**
     * Deploys the airbags under severe collision circumstances.
     */
    public void deployAirbags() {
        Airbag airbag = car.getAirbag();
        if (airbag.isDeployed()) {
            System.out.println("Airbags are already deployed.");
            return;
        }

        airbag.setDeployed(true);
        this.alertTriggered = true;

        System.out.println(StringFormatter.formatEmergency("AIRBAGS DEPLOYED!"));
        System.out.println(">>> [BOOM!] Pyrotechnic inflators triggered in 30 milliseconds!");
        System.out.println(">>> Front Driver & Passenger SRS Cushions Fully Inflated.");
        System.out.println(">>> Hazard lights flashing. Emergency SOS call initiated.");
        SystemLogger.logAlert("CRITICAL: Airbags deployed due to severe collision!");
    }

    /**
     * Resets airbags (service workshop mode).
     */
    public void resetAirbags() {
        car.getAirbag().reset();
        this.alertTriggered = false;
        System.out.println("\n[SRS SERVICE]: Airbag modules reloaded and sensors re-armed.");
        SystemLogger.log("Airbag modules reset by service technician.");
    }

    public void displayAirbagStatusInteractive() {
        System.out.println("\n--- SRS AIRBAG SYSTEM STATUS ---");
        boolean deployed = car.getAirbag().isDeployed();
        System.out.println("Airbag Status: " + (deployed ? "[DEPLOYED - REQUIRES SERVICE]" : "[ARMED & OPERATIONAL - STANDBY]"));
        System.out.println("Sensor Health: Nominal (Accelerometers Ready)");

        if (deployed) {
            System.out.println("\nAirbags have fired. Would you like to simulate workshop reset? (y/n)");
            if (com.smartcar.util.InputValidator.readBoolean("Reset Airbags?")) {
                resetAirbags();
            }
        }
    }
}
