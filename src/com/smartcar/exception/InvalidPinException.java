package com.smartcar.exception;

/**
 * Thrown when an incorrect or invalid authentication PIN is provided.
 * Demonstrates exception subclassing and error propagation.
 */
public class InvalidPinException extends CarException {
    private final int remainingAttempts;

    public InvalidPinException(String message, int remainingAttempts) {
        super(message);
        this.remainingAttempts = remainingAttempts;
    }

    public int getRemainingAttempts() {
        return this.remainingAttempts;
    }
}
