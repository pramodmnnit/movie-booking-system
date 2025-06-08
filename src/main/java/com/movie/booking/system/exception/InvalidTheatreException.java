package com.movie.booking.system.exception;

public class InvalidTheatreException extends Exception {
    public InvalidTheatreException(String message) {
        super(message);
    }

    public InvalidTheatreException(String message, Throwable cause) {
        super(message, cause);
    }
}
