package com.smartcar.model;

/**
 * Model class representing the car's Air Conditioning system.
 */
public class AirConditioner {
    private boolean on;
    private int temperatureCelsius;
    private int fanSpeed; // 1 to 5

    public AirConditioner() {
        this.on = false;
        this.temperatureCelsius = 22; // Comfortable default
        this.fanSpeed = 2;
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

    public int getFanSpeed() {
        return this.fanSpeed;
    }

    public void setFanSpeed(int fanSpeed) {
        if (fanSpeed >= 1 && fanSpeed <= 5) {
            this.fanSpeed = fanSpeed;
        }
    }
}
