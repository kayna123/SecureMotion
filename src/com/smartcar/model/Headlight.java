package com.smartcar.model;

/**
 * Model class representing car headlights.
 * Tracks power status and high/low beam mode.
 */
public class Headlight {
    private boolean on;
    private String beamMode; // "LOW" or "HIGH"

    public Headlight() {
        this.on = false;
        this.beamMode = "LOW";
    }

    public boolean isOn() {
        return this.on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public String getBeamMode() {
        return this.beamMode;
    }

    public void setBeamMode(String beamMode) {
        if ("HIGH".equalsIgnoreCase(beamMode)) {
            this.beamMode = "HIGH";
        } else {
            this.beamMode = "LOW";
        }
    }
}
