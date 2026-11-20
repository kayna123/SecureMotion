package com.smartcar.model;

/**
 * Model class representing a vehicle door.
 * Holds position, locked state, open state, and rear-door flag for child lock.
 */
public class Door {
    private final String position;
    private boolean locked;
    private boolean open;
    private final boolean rearDoor;

    public Door(String position, boolean rearDoor) {
        this.position = position;
        this.rearDoor = rearDoor;
        this.locked = true; // Default locked for security
        this.open = false;
    }

    public String getPosition() {
        return this.position;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isRearDoor() {
        return this.rearDoor;
    }
}
