package com.movie.booking.system.exception;

public class VenueException extends RuntimeException {
    public VenueException(String message) {
        super(message);
    }

    public VenueException(String message, Throwable cause) {
        super(message, cause);
    }
} 