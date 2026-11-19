package com.smartcar.exception;

/**
 * Base custom checked exception class for the Smart Car System.
 * Demonstrates custom exception hierarchy and exception handling.
 */
public class CarException extends Exception {
    public CarException(String message) {
        super(message);
    }

    public CarException(String message, Throwable cause) {
        super(message, cause);
    }
}
