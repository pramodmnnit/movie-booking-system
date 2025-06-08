package com.movie.booking.system.repository;

import com.movie.booking.system.model.Show;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Repository
public class ShowRepositoryImpl implements ShowRepository {
    private final Map<String, Show> showMap = new HashMap<>();

    @Override
    public Show getShowById(String id) {

        return showMap.get(id);
    }

    @Override
    public void saveShow(Show show) {
        if (Objects.nonNull(show)) {
            showMap.put(show.getId(), show);
        }
    }
}
