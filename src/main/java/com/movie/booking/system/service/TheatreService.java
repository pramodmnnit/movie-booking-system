package com.movie.booking.system.service;

import com.movie.booking.system.model.Theatre;

public interface TheatreService {
    void saveTheatre(Theatre theatre);

    Theatre getTheatreById(String id);
}
