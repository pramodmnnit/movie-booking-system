package com.movie.booking.system.repository;

import com.movie.booking.system.model.Theatre;

import java.util.HashMap;
import java.util.Map;

public class TheatreRepositoryImpl implements TheatreRepository {

    private final Map<String, Theatre> theatreMap = new HashMap<>();

    @Override
    public TheatreRepository getTheatreById(String id) {
        return null;
    }

    @Override
    public TheatreRepository getTheatreByStatus(String status) {
        return null;
    }
}
