package com.movie.booking.system.repository;

import com.movie.booking.system.model.Screen;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ScreenRepositoryImpl implements ScreenRepository {
    private final Map<String, Screen> screenMap;

    public ScreenRepositoryImpl() {
        this.screenMap = new HashMap<>();
    }

    @Override
    public void saveScreen(Screen screen) {
        screenMap.put(screen.getId(), screen);
    }

    @Override
    public Screen getScreenById(String id) {
        return screenMap.get(id);
    }

    @Override
    public List<Screen> getAllScreens() {
        return new ArrayList<>(screenMap.values());
    }

    @Override
    public boolean deleteScreen(String id) {
        return screenMap.remove(id) != null;
    }
}
