package com.movie.booking.system.repository;

import com.movie.booking.system.model.Movie;

public interface MovieRepository {
    Movie getMovieById(String id);
}
