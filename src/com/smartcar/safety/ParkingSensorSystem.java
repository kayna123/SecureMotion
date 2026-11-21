package com.smartcar.safety;

import com.smartcar.common.SafetyFeature;
import com.smartcar.exception.SafetyViolationException;
import com.smartcar.util.InputValidator;
import com.smartcar.util.SystemLogger;

/**
 * Ultrasonic parking distance sensor simulation.
 * Evaluates obstacle proximity and outputs tiered audible/visual warning tones.
 */
public class ParkingSensorSystem extends SafetyFeature {
    private double lastMeasuredDistanceCm;

    public ParkingSensorSystem() {
        super("Ultrasonic Parking Sensor System");
        this.lastMeasuredDistanceCm = 150.0;
        this.active = false;
    }

    @Override
    public void validateSafetyCondition() throws SafetyViolationException {
        if (this.lastMeasuredDistanceCm <= 20.0) {
            throw new SafetyViolationException("CRITICAL PROXIMITY: Collision imminent if vehicle proceeds!");
        }
    }

    /**
     * Interactively tests the parking sensor with obstacle distances.
     */
    public void parkAssistInteractive() {
        System.out.println("\n--- ULTRASONIC PARK ASSIST MODE ---");
        this.active = true;
        System.out.println("Park Assist Sensors: ACTIVATED (Scanning 360-degree perimeter)");

        double distance = InputValidator.readDouble("Enter distance from obstacle (cm) [0 to 500]: ", 0.0, 500.0);
        this.lastMeasuredDistanceCm = distance;
        evaluateProximity(distance);
    }

    /**
     * Evaluates obstacle distance and prints corresponding terminal beep patterns.
     */
    public void evaluateProximity(double distanceCm) {
        System.out.println("\nDistance Sensor Reading: " + distanceCm + " cm");

        if (distanceCm > 100.0) {
            this.alertTriggered = false;
            System.out.println("[STATUS]: Safe distance. No immediate obstacles detected.");
        } else if (distanceCm >= 51.0) {
            this.alertTriggered = true;
            System.out.println("[ALERT]: Obstacle nearby! (Distance: " + distanceCm + " cm)");
            System.out.println("Beep...... Beep...... (Slow Interval)");
            SystemLogger.log("Park Sensor: Obstacle nearby at " + distanceCm + " cm.");
        } else if (distanceCm >= 21.0) {
            this.alertTriggered = true;
            System.out.println("\n[CAUTION]: Approaching object!");
            System.out.println("Beep! Beep! Beep! Beep! (Fast Interval)");
            SystemLogger.logWarning("Park Sensor: Obstacle close at " + distanceCm + " cm.");
        } else {
            this.alertTriggered = true;
            System.out.println("\n**************************************");
            System.out.println("BEEP! BEEP! BEEP! BEEP! STOP!");
            System.out.println("WARNING: Obstacle too close (" + distanceCm + " cm)!");
            System.out.println("STOP THE CAR IMMEDIATELY!");
            System.out.println("**************************************");
            SystemLogger.logAlert("Park Sensor: Hazard! Obstacle within " + distanceCm + " cm.");
        }
    }

    public double getLastMeasuredDistanceCm() {
        return this.lastMeasuredDistanceCm;
    }
}
