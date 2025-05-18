package com.movie.booking.system.exception;

public class InvalidMovieException extends Exception {
    public InvalidMovieException(String message) {
        super(message);
    }

    public InvalidMovieException(String message, Throwable cause) {
        super(message, cause);
    }
}
