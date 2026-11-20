package com.smartcar.model;

/**
 * Master domain entity representing the complete Smart Car.
 * Acts as the single source of truth for the vehicle's entire operational state.
 * Contains aggregated component objects demonstrating composition and encapsulation.
 */
public class Car {
    private final String vin;
    private boolean unlocked;
    private boolean childLockEnabled;
    private double currentSpeed; // in km/h
    private double speedLimit;   // in km/h (default 80 km/h)
    private double kilometersSinceLastService;

    // Aggregated component models
    private final Engine engine;
    private final FuelTank fuelTank;
    private Gear currentGear;
    private final Door[] doors;
    private final SideMirror leftMirror;
    private final SideMirror rightMirror;
    private final Headlight headlights;
    private final Tyre[] tyres;
    private final Airbag airbag;
    private final Wiper frontWiper;
    private final Wiper rearWiper;
    private final Trunk trunk;
    private final AirConditioner airConditioner;
    private final Heater heater;
    private final Seatbelt driverSeatbelt;

    public Car() {
        this.vin = "SC2026-JAVA-9988";
        this.unlocked = false;
        this.childLockEnabled = false;
        this.currentSpeed = 0.0;
        this.speedLimit = 80.0; // Default speed limit
        this.kilometersSinceLastService = 4200.0; // Initial simulated mileage

        this.engine = new Engine();
        this.fuelTank = new FuelTank();
        this.currentGear = Gear.PARK;

        // Initialize 4 doors
        this.doors = new Door[] {
            new Door("Front Left (Driver)", false),
            new Door("Front Right (Passenger)", false),
            new Door("Rear Left", true),
            new Door("Rear Right", true)
        };

        // Initialize side mirrors
        this.leftMirror = new SideMirror("Driver Side (Left)");
        this.rightMirror = new SideMirror("Passenger Side (Right)");

        this.headlights = new Headlight();

        // Initialize 4 tyres with nominal recommended PSI values
        this.tyres = new Tyre[] {
            new Tyre("Front Left", 32.5),
            new Tyre("Front Right", 32.0),
            new Tyre("Rear Left", 33.0),
            new Tyre("Rear Right", 32.8)
        };

        this.airbag = new Airbag();
        this.frontWiper = new Wiper("Front Windshield");
        this.rearWiper = new Wiper("Rear Windshield");
        this.trunk = new Trunk();
        this.airConditioner = new AirConditioner();
        this.heater = new Heater();
        this.driverSeatbelt = new Seatbelt();
    }

    // Getters and setters for vehicle state
    public String getVin() {
        return this.vin;
    }

    public boolean isUnlocked() {
        return this.unlocked;
    }

    public void setUnlocked(boolean unlocked) {
        this.unlocked = unlocked;
        // Lock or unlock all doors accordingly
        for (Door door : this.doors) {
            door.setLocked(!unlocked);
        }
        this.trunk.setLocked(!unlocked);
    }

    public boolean isChildLockEnabled() {
        return this.childLockEnabled;
    }

    public void setChildLockEnabled(boolean childLockEnabled) {
        this.childLockEnabled = childLockEnabled;
    }

    public double getCurrentSpeed() {
        return this.currentSpeed;
    }

    public void setCurrentSpeed(double currentSpeed) {
        if (currentSpeed < 0.0) {
            this.currentSpeed = 0.0;
        } else {
            this.currentSpeed = currentSpeed;
        }
    }

    public double getSpeedLimit() {
        return this.speedLimit;
    }

    public void setSpeedLimit(double speedLimit) {
        this.speedLimit = speedLimit;
    }

    public double getKilometersSinceLastService() {
        return this.kilometersSinceLastService;
    }

    public void setKilometersSinceLastService(double km) {
        this.kilometersSinceLastService = km;
    }

    public void addKilometers(double km) {
        if (km > 0) {
            this.kilometersSinceLastService += km;
        }
    }

    public Engine getEngine() {
        return this.engine;
    }

    public FuelTank getFuelTank() {
        return this.fuelTank;
    }

    public Gear getCurrentGear() {
        return this.currentGear;
    }

    public void setCurrentGear(Gear currentGear) {
        this.currentGear = currentGear;
    }

    public Door[] getDoors() {
        return this.doors;
    }

    public SideMirror getLeftMirror() {
        return this.leftMirror;
    }

    public SideMirror getRightMirror() {
        return this.rightMirror;
    }

    public Headlight getHeadlights() {
        return this.headlights;
    }

    public Tyre[] getTyres() {
        return this.tyres;
    }

    public Airbag getAirbag() {
        return this.airbag;
    }

    public Wiper getFrontWiper() {
        return this.frontWiper;
    }

    public Wiper getRearWiper() {
        return this.rearWiper;
    }

    public Trunk getTrunk() {
        return this.trunk;
    }

    public AirConditioner getAirConditioner() {
        return this.airConditioner;
    }

    public Heater getHeater() {
        return this.heater;
    }

    public Seatbelt getDriverSeatbelt() {
        return this.driverSeatbelt;
    }
}
