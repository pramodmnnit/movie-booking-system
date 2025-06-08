package com.movie.booking.system.repository;

import com.movie.booking.system.exception.InvalidMovieException;
import com.movie.booking.system.model.Movie;
import com.movie.booking.system.model.MovieStatus;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Repository
public class MovieRepositoryImpl implements MovieRepository {

    private final Map<String, Movie> movieMap = new HashMap<>();

    @Override
    public Movie getMovieById(String id) {
        return movieMap.get(id);
    }

    @Override
    public Movie getMovieByName(String name) {
        return movieMap.values().stream()
                .filter(movie -> movie.getName().equalsIgnoreCase(name))
                .filter(movie -> movie.getStatus().equals(MovieStatus.ACTIVE))
                .findFirst()
                .orElse(null);
    }

    @Override
    public void saveMovie(Movie movie) throws InvalidMovieException {
        if (Objects.nonNull(movie)) {
            movieMap.put(movie.getId(), movie);
        } else {
            throw new InvalidMovieException("Movie cannot be null");
        }
    }
}
