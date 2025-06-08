package com.movie.booking.system.repository;

import com.movie.booking.system.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Repository
public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> userMap = new HashMap<>();

    @Override
    public void saveUser(User user) {
        if (Objects.nonNull(user)) {
            this.userMap.put(user.getId(), user);
        }
    }

    @Override
    public User getUserById(String id) {
        return userMap.get(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }
}
