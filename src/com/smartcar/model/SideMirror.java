package com.smartcar.model;

/**
 * Model class representing an external side mirror.
 * Tracks folding state and angular adjustment.
 */
public class SideMirror {
    private final String side; // "Left" or "Right"
    private boolean folded;
    private int tiltAngle; // 0 to 45 degrees

    public SideMirror(String side) {
        this.side = side;
        this.folded = false;
        this.tiltAngle = 15;
    }

    public String getSide() {
        return this.side;
    }

    public boolean isFolded() {
        return this.folded;
    }

    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    public int getTiltAngle() {
        return this.tiltAngle;
    }

    public void setTiltAngle(int tiltAngle) {
        this.tiltAngle = tiltAngle;
    }
}
