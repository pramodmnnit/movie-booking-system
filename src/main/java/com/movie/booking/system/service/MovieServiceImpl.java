package com.movie.booking.system.service;

import com.movie.booking.system.exception.InvalidMovieException;
import com.movie.booking.system.model.Movie;
import com.movie.booking.system.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void saveMovie(Movie movie) throws InvalidMovieException {
        if (movie == null || movie.getId() == null || movie.getName() == null || movie.getName().isEmpty()) {
            throw new InvalidMovieException("Movie cannot be null or have empty fields");
        }
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
