package com.smartcar.engine;

import com.smartcar.common.BaseCarSystem;
import com.smartcar.exception.SafetyViolationException;
import com.smartcar.model.Car;
import com.smartcar.model.Gear;
import com.smartcar.util.SystemLogger;

/**
 * Controller service for starting and stopping vehicle engine.
 * Enforces safety conditions: unlocked car, sufficient fuel, park/neutral gear, and zero speed on stop.
 */
public class EngineControlSystem extends BaseCarSystem {
    private final Car car;

    public EngineControlSystem(Car car) {
        super("Engine Control System");
        this.car = car;
    }

    /**
     * Starts the engine after validating safety checks.
     */
    public void startEngine() throws SafetyViolationException {
        if (!car.isUnlocked()) {
            throw new SafetyViolationException("Cannot start engine: Vehicle is LOCKED! Please unlock first.");
        }
        if (car.getFuelTank().isEmpty()) {
            throw new SafetyViolationException("Cannot start engine: Fuel tank is completely EMPTY!");
        }
        if (car.getCurrentGear() != Gear.PARK && car.getCurrentGear() != Gear.NEUTRAL) {
            throw new SafetyViolationException("Cannot start engine: Transmission must be in PARK (P) or NEUTRAL (N). Current: " + car.getCurrentGear());
        }
        if (car.getEngine().isRunning()) {
            System.out.println("Engine is ALREADY RUNNING.");
            return;
        }

        car.getEngine().setRunning(true);
        this.active = true;
        SystemLogger.log("Engine started successfully. Idling at 800 RPM.");
        System.out.println("\n[ENGINE]: VROOOM! Engine started successfully.");
        System.out.println("Current RPM: " + car.getEngine().getRpm() + " | Temp: " + car.getEngine().getTemperatureCelsius() + " deg C");

        if (!car.getDriverSeatbelt().isFastened()) {
            System.out.println("[!] SAFETY ADVISORY: Driver seatbelt is NOT fastened! Please buckle up before driving.");
            SystemLogger.logWarning("Engine running without driver seatbelt fastened.");
        }
    }

    /**
     * Stops the engine safely.
     */
    public void stopEngine() throws SafetyViolationException {
        if (!car.getEngine().isRunning()) {
            System.out.println("Engine is ALREADY STOPPED.");
            return;
        }
        if (car.getCurrentSpeed() > 0.0) {
            throw new SafetyViolationException("CRITICAL SAFETY HAZARD: Cannot turn off engine while vehicle is moving at " + car.getCurrentSpeed() + " km/h!");
        }

        car.getEngine().setRunning(false);
        this.active = false;
        SystemLogger.log("Engine stopped safely.");
        System.out.println("\n[ENGINE]: Engine turned OFF safely.");
    }
}
