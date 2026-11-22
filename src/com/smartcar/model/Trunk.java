package com.smartcar.model;

/**
 * Model class representing the car trunk (dikki).
 */
public class Trunk {
    private boolean open;
    private boolean locked;

    public Trunk() {
        this.open = false;
        this.locked = true;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isLocked() {
        return this.locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
