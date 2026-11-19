package com.smartcar.common;

/**
 * Interface representing a standard vehicle feature.
 * Demonstrates Java Interface abstraction and contract-based design.
 */
public interface VehicleFeature {
    void activate();
    void deactivate();
    boolean isActive();
    String getFeatureName();
}
