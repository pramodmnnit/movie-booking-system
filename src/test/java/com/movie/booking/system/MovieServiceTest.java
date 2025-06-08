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

import static org.junit.jupiter.api.Assertions.*;

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
        assertNotNull(movie, "Movie should not be null");
        assertEquals(MovieStatus.ACTIVE, movie.getStatus(), "Movie status should be ACTIVE");
        assertEquals("Inception", movie.getName(), "Movie name should match");
        assertEquals(148, movie.getDuration(), "Movie duration should match");
    }

    @Test
    void getInactiveMovieByName() {
        Movie movie = movieService.getMovieByName("The Dark Knight");
        assertNull(movie, "Inactive movie should not be returned");
    }

    @Test
    void saveNullMovie() {
        InvalidMovieException exception = assertThrows(InvalidMovieException.class,
                () -> movieService.saveMovie(null),
                "Saving null movie should throw InvalidMovieException");
        assertEquals("Movie cannot be null or have empty fields", exception.getMessage(), "Exception message should match");
    }

    @Test
    void getMovieByNonExistentName() {
        Movie movie = movieService.getMovieByName("NonExistentMovie");
        assertNull(movie, "Non-existent movie should return null");
    }

    @Test
    void saveMovieWithNullName() {
        Movie movie = getActiveMovie();
        movie.setName(null);

        InvalidMovieException exception = assertThrows(InvalidMovieException.class,
                () -> movieService.saveMovie(movie),
                "Saving movie with null name should throw InvalidMovieException");
        assertEquals("Movie cannot be null or have empty fields", exception.getMessage(), "Exception message should match");
    }

    @Test
    void saveMovieWithEmptyName() {
        Movie movie = getActiveMovie();
        movie.setName("");

        InvalidMovieException exception = assertThrows(InvalidMovieException.class,
                () -> movieService.saveMovie(movie),
                "Saving movie with empty name should throw InvalidMovieException");
        assertEquals("Movie cannot be null or have empty fields", exception.getMessage(), "Exception message should match");
    }
}
