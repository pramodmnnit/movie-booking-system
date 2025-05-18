package com.movie.booking.system.service;

import com.movie.booking.system.model.Show;
import com.movie.booking.system.repository.ShowRepository;

public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;

    public ShowServiceImpl(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public void saveShow(Show show) {
        showRepository.saveShow(show);

    }
}
