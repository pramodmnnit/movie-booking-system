package com.movie.booking.system.exception;

public class InvalidShowException extends Exception {
    public InvalidShowException(String message) {
        super(message);
    }

    public InvalidShowException(String message, Throwable cause) {
        super(message, cause);
    }
}
