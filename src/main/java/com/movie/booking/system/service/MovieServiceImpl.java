package com.movie.booking.system.service;

import com.movie.booking.system.exception.InvalidMovieException;
import com.movie.booking.system.model.Movie;
import com.movie.booking.system.repository.MovieRepository;

public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void saveMovie(Movie movie) throws InvalidMovieException {
        movieRepository.saveMovie(movie);
    }

    @Override
    public Movie getMovieById(String id) {
        return movieRepository.getMovieById(id);
    }

    @Override
    public Movie getMovieByName(String name) {
        return movieRepository.getMovieByName(name);
    }
}
