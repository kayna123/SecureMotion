package com.smartcar.common;

/**
 * Abstract base class for all vehicle subsystems.
 * Demonstrates abstraction, inheritance, constructor chaining, and the use of 'this'.
 */
public abstract class BaseCarSystem implements VehicleFeature, Displayable {
    protected String systemName;
    protected boolean active;

    public BaseCarSystem(String systemName) {
        this.systemName = systemName;
        this.active = false;
    }

    @Override
    public void activate() {
        this.active = true;
        System.out.println("[" + this.systemName + "] Activated.");
    }

    @Override
    public void deactivate() {
        this.active = false;
        System.out.println("[" + this.systemName + "] Deactivated.");
    }

    @Override
    public boolean isActive() {
        return this.active;
    }

    @Override
    public String getFeatureName() {
        return this.systemName;
    }

    @Override
    public void displayStatus() {
        String statusText = this.active ? "ACTIVE / ON" : "INACTIVE / OFF";
        System.out.println(String.format("%-25s : %s", this.systemName, statusText));
    }
}
