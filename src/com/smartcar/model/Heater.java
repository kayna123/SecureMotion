package com.smartcar.model;

/**
 * Model class representing the cabin heater system.
 */
public class Heater {
    private boolean on;
    private int temperatureCelsius;

    public Heater() {
        this.on = false;
        this.temperatureCelsius = 24;
    }

    public boolean isOn() {
        return this.on;
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    public int getTemperatureCelsius() {
        return this.temperatureCelsius;
    }

    public void setTemperatureCelsius(int temperatureCelsius) {
        if (temperatureCelsius < 16) {
            this.temperatureCelsius = 16;
        } else if (temperatureCelsius > 30) {
            this.temperatureCelsius = 30;
        } else {
            this.temperatureCelsius = temperatureCelsius;
        }
    }
}
