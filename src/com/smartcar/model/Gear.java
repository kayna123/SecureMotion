package com.smartcar.model;

/**
 * Enumeration representing automatic transmission gear modes.
 * Demonstrates Java Enum, constructors, and instance variables.
 */
public enum Gear {
    PARK("P", "Park - Wheels locked"),
    REVERSE("R", "Reverse - Backing up"),
    NEUTRAL("N", "Neutral - Free rolling"),
    DRIVE("D", "Drive - Forward movement");

    private final String code;
    private final String description;

    Gear(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.code + " (" + this.name() + ")";
    }
}
