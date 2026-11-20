package com.smartcar.exception;

/**
 * Thrown when an action violates vehicle safety rules.
 * Examples: stopping engine while driving, opening doors at speed, shifting to Park while moving.
 */
public class SafetyViolationException extends CarException {
    public SafetyViolationException(String message) {
        super(message);
    }
}
