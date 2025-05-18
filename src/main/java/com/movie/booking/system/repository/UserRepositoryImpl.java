package com.movie.booking.system.repository;

import com.movie.booking.system.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> userMap = new HashMap<>();

    @Override
    public void saveUser(User user) {
        if (Objects.nonNull(user)) {
            this.userMap.put(user.getUsername(), user);
        }
    }

    @Override
    public User getUserById(int id) {
        return null;
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }
}
