package com.smartcar.model;

/**
 * Model class encapsulating the vehicle engine state.
 * Demonstrates encapsulation with private attributes and public getters/setters.
 */
public class Engine {
    private boolean running;
    private double temperatureCelsius;
    private int rpm;

    public Engine() {
        this.running = false;
        this.temperatureCelsius = 25.0; // Ambient resting temperature
        this.rpm = 0;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning(boolean running) {
        this.running = running;
        if (running) {
            this.rpm = 800; // Idle RPM
            this.temperatureCelsius = 90.0; // Operating temperature
        } else {
            this.rpm = 0;
            this.temperatureCelsius = 35.0;
        }
    }

    public double getTemperatureCelsius() {
        return this.temperatureCelsius;
    }

    public void setTemperatureCelsius(double temperatureCelsius) {
        this.temperatureCelsius = temperatureCelsius;
    }

    public int getRpm() {
        return this.rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }
}
