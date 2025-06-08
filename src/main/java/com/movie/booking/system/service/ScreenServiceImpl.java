package com.movie.booking.system.service;

import com.movie.booking.system.exception.VenueException;
import com.movie.booking.system.model.Screen;
import com.movie.booking.system.model.Show;
import com.movie.booking.system.repository.ScreenRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreenServiceImpl implements ScreenService {
    private final ScreenRepository screenRepository;

    public ScreenServiceImpl(ScreenRepository screenRepository) {
        this.screenRepository = screenRepository;
    }

    @Override
    public void saveScreen(Screen screen) {
        if (screen == null) {
            throw new VenueException("Screen cannot be null");
        }
        if (screen.getId() == null || screen.getId().trim().isEmpty()) {
            throw new VenueException("Screen ID cannot be null or empty");
        }
        if (screen.getName() == null || screen.getName().trim().isEmpty()) {
            throw new VenueException("Screen name cannot be null or empty");
        }
        screenRepository.saveScreen(screen);
    }

    @Override
    public Screen getScreenById(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new VenueException("Screen ID cannot be null or empty");
        }
        return screenRepository.getScreenById(id);
    }

    @Override
    public List<Screen> getAllScreens() {
        return screenRepository.getAllScreens();
    }

    @Override
    public void updateScreen(Screen screen) {
        if (screen == null) {
            throw new VenueException("Screen cannot be null");
        }
        if (screen.getId() == null || screen.getId().trim().isEmpty()) {
            throw new VenueException("Screen ID cannot be null or empty");
        }
        if (screen.getName() == null || screen.getName().trim().isEmpty()) {
            throw new VenueException("Screen name cannot be null or empty");
        }
        Screen existingScreen = screenRepository.getScreenById(screen.getId());
        if (existingScreen == null) {
            throw new VenueException("Screen with ID " + screen.getId() + " does not exist");
        }
        screenRepository.saveScreen(screen);
    }

    @Override
    public boolean deleteScreen(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new VenueException("Screen ID cannot be null or empty");
        }
        return screenRepository.deleteScreen(id);
    }

    @Override
    public List<Show> getShowsForScreen(String screenId) {
        if (screenId == null || screenId.trim().isEmpty()) {
            throw new VenueException("Screen ID cannot be null or empty");
        }
        Screen screen = screenRepository.getScreenById(screenId);
        if (screen == null) {
            throw new VenueException("Screen with ID " + screenId + " does not exist");
        }
        return screen.getShows();
    }
} 