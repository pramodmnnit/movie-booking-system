package com.movie.booking.system;

import com.movie.booking.system.exception.InvalidMovieException;
import com.movie.booking.system.model.Movie;
import com.movie.booking.system.model.MovieStatus;
import com.movie.booking.system.repository.MovieRepositoryImpl;
import com.movie.booking.system.service.MovieService;
import com.movie.booking.system.service.MovieServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MovieServiceTest {

    private MovieService movieService;

    private static Movie getInactiveMovie() {
        Movie inactiveMovie = new Movie();
        inactiveMovie.setId("2");
        inactiveMovie.setName("The Dark Knight");
        inactiveMovie.setDuration(152);
        inactiveMovie.setGenre("Action");
        inactiveMovie.setReleaseDate("2008-07-18");
        inactiveMovie.setLanguage("English");
        inactiveMovie.setDescription("When the menace known as the Joker emerges from his mysterious past, he wreaks havoc and chaos on the people of Gotham.");
        inactiveMovie.setStatus(MovieStatus.INACTIVE);
        return inactiveMovie;
    }

    private static Movie getActiveMovie() {
        Movie activeMovie = new Movie();
        activeMovie.setId("1");
        activeMovie.setName("Inception");
        activeMovie.setDuration(148);
        activeMovie.setGenre("Sci-Fi");
        activeMovie.setReleaseDate("2010-07-16");
        activeMovie.setLanguage("English");
        activeMovie.setDescription("A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a CEO.");
        activeMovie.setStatus(MovieStatus.ACTIVE);
        return activeMovie;
    }

    @BeforeEach
    void setUp() throws InvalidMovieException {
        this.movieService = new MovieServiceImpl(new MovieRepositoryImpl());

        Movie activeMovie = getActiveMovie();
        this.movieService.saveMovie(activeMovie);

        Movie inactiveMovie = getInactiveMovie();

        this.movieService.saveMovie(inactiveMovie);


    }

    @AfterEach
    void tearDown() {
        // Clean up resources, if needed
    }

    @Test
    void getActiveMovieByName() {
        Movie movie = movieService.getMovieByName("Inception");
        assert movie != null;
        assert movie.getStatus() == MovieStatus.ACTIVE;
    }

    @Test
    void getInactiveMovieByName() {
        Movie movie = movieService.getMovieByName("The Dark Knight");
        assert movie == null;
    }

    @Test
    void saveNullMovie() {
        try {
            movieService.saveMovie(null);
        } catch (InvalidMovieException e) {
            assert e.getMessage().equals("Movie cannot be null");
        }
    }
}
