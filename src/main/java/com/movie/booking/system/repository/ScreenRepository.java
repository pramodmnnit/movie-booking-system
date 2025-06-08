package com.movie.booking.system.repository;

import com.movie.booking.system.model.Screen;

import java.util.List;

public interface ScreenRepository {
    /**
     * Save a screen
     *
     * @param screen the screen to save
     */
    void saveScreen(Screen screen);

    /**
     * Get a screen by its ID
     *
     * @param id the screen ID
     * @return the screen if found, null otherwise
     */
    Screen getScreenById(String id);

    /**
     * Get all screens
     *
     * @return list of all screens
     */
    List<Screen> getAllScreens();

    /**
     * Delete a screen by its ID
     *
     * @param id the screen ID
     * @return true if deleted successfully, false if screen doesn't exist
     */
    boolean deleteScreen(String id);
}
