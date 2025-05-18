package com.movie.booking.system.repository;

import com.movie.booking.system.model.Show;

public interface ShowRepository {

    Show getShowById(String id);

    void saveShow(Show show);
}
