package com.smartcar.model;

/**
 * Model class representing the car's supplementary restraint airbag system.
 */
public class Airbag {
    private boolean deployed;
    private boolean armed;

    public Airbag() {
        this.deployed = false;
        this.armed = true; // Armed and active by default
    }

    public boolean isDeployed() {
        return this.deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }

    public boolean isArmed() {
        return this.armed;
    }

    public void setArmed(boolean armed) {
        this.armed = armed;
    }

    public void reset() {
        this.deployed = false;
        this.armed = true;
    }
}
