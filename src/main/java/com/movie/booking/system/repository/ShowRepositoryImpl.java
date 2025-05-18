package com.movie.booking.system.repository;

import com.movie.booking.system.model.Show;

import java.util.HashMap;
import java.util.Map;

public class ShowRepositoryImpl implements ShowRepository {
    private final Map<String, Show> showMap = new HashMap<>();

    @Override
    public Show getShowById(String id) {
        return null;
    }
}
