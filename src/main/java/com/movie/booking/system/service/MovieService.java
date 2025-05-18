package com.movie.booking.system.service;

import com.movie.booking.system.exception.InvalidMovieException;
import com.movie.booking.system.model.Movie;

public interface MovieService {
    void saveMovie(Movie movie) throws InvalidMovieException;

    Movie getMovieById(String id);

    Movie getMovieByName(String name);
}
