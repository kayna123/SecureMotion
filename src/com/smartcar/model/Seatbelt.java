package com.smartcar.model;

/**
 * Model class representing the seatbelt safety sensor.
 */
public class Seatbelt {
    private boolean fastened;

    public Seatbelt() {
        this.fastened = false; // By default unfastened when getting into car
    }

    public boolean isFastened() {
        return this.fastened;
    }

    public void setFastened(boolean fastened) {
        this.fastened = fastened;
    }
}
