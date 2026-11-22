package com.smartcar.exception;

/**
 * Thrown when numeric or textual input is out of bounds or malformed.
 */
public class InvalidInputException extends CarException {
    public InvalidInputException(String message) {
        super(message);
    }
}
