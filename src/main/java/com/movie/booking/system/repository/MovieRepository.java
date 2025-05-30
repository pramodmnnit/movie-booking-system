package com.movie.booking.system.repository;

import com.movie.booking.system.exception.InvalidMovieException;
import com.movie.booking.system.model.Movie;

public interface MovieRepository {
    Movie getMovieById(String id);

    Movie getMovieByName(String name);

    void saveMovie(Movie movie) throws InvalidMovieException;
}
