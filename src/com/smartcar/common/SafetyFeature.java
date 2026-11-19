package com.smartcar.common;

import com.smartcar.exception.SafetyViolationException;

/**
 * Abstract class representing dedicated automotive safety systems.
 * Extends BaseCarSystem and enforces safety condition validation.
 */
public abstract class SafetyFeature extends BaseCarSystem {
    protected boolean alertTriggered;

    public SafetyFeature(String systemName) {
        super(systemName);
        this.alertTriggered = false;
    }

    /**
     * Abstract method to validate if operation can proceed safely.
     * Subclasses implement their custom safety rules.
     */
    public abstract void validateSafetyCondition() throws SafetyViolationException;

    public boolean isAlertTriggered() {
        return this.alertTriggered;
    }

    public void clearAlert() {
        this.alertTriggered = false;
    }
}
