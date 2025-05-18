package com.movie.booking.system.repository;

public interface TheatreRepository {
    TheatreRepository getTheatreById(String id);

    TheatreRepository getTheatreByStatus(String status);
}
