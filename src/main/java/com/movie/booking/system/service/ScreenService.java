package com.movie.booking.system.service;

import com.movie.booking.system.model.Screen;
import com.movie.booking.system.model.Show;

import java.util.List;

public interface ScreenService {
    void saveScreen(Screen screen);

    Screen getScreenById(String id);

    List<Screen> getAllScreens();

    void updateScreen(Screen screen);

    boolean deleteScreen(String id);

    List<Show> getShowsForScreen(String screenId);
} 