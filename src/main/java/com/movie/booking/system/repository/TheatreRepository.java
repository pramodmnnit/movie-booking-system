package com.movie.booking.system.repository;

import com.movie.booking.system.model.Theatre;

public interface TheatreRepository {
    void saveTheatre(Theatre theatre);

    Theatre getTheatreById(String id);
}
