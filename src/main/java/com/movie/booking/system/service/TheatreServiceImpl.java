package com.movie.booking.system.service;

import com.movie.booking.system.model.Theatre;
import com.movie.booking.system.repository.TheatreRepository;
import org.springframework.stereotype.Service;

@Service
public class TheatreServiceImpl implements TheatreService {

    private final TheatreRepository theatreRepository;

    public TheatreServiceImpl(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    @Override
    public void saveTheatre(Theatre theatre) {
        theatreRepository.saveTheatre(theatre);
    }

    @Override
    public Theatre getTheatreById(String id) {
        return theatreRepository.getTheatreById(id);
    }
}
