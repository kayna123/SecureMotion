package com.smartcar.model;

/**
 * Model class representing a windshield wiper assembly (Front or Rear).
 */
public class Wiper {
    private final String location; // "Front" or "Rear"
    private boolean on;
    private String speedLevel; // "OFF", "LOW", "MEDIUM", "HIGH"

    public Wiper(String location) {
        this.location = location;
        this.on = false;
        this.speedLevel = "OFF";
    }

    public String getLocation() {
        return this.location;
    }

    public boolean isOn() {
        return this.on;
    }

    public void setOn(boolean on) {
        this.on = on;
        if (!on) {
            this.speedLevel = "OFF";
        } else if ("OFF".equals(this.speedLevel)) {
            this.speedLevel = "LOW";
        }
    }

    public String getSpeedLevel() {
        return this.speedLevel;
    }

    public void setSpeedLevel(String speedLevel) {
        if ("OFF".equalsIgnoreCase(speedLevel)) {
            this.on = false;
            this.speedLevel = "OFF";
        } else {
            this.on = true;
            this.speedLevel = speedLevel.toUpperCase();
        }
    }
}
