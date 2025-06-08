package com.movie.booking.system.exception;

public class InvalidScreenException extends Exception {
    public InvalidScreenException(String message) {
        super(message);
    }

    public InvalidScreenException(String message, Throwable cause) {
        super(message, cause);
    }
}
