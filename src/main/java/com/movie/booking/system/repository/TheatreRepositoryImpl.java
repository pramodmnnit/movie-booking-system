package com.movie.booking.system.repository;

import com.movie.booking.system.model.Theatre;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;


@Repository
public class TheatreRepositoryImpl implements TheatreRepository {

    private final Map<String, Theatre> theatreMap = new HashMap<>();

    @Override
    public void saveTheatre(Theatre theatre) {
        theatreMap.put(theatre.id, theatre);
    }

    @Override
    public Theatre getTheatreById(String id) {

        return theatreMap.get(id);
    }

}
