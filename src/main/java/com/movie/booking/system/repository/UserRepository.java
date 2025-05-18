package com.movie.booking.system.repository;

import com.movie.booking.system.model.User;

public interface UserRepository {
    void saveUser(User user);

    User getUserById(int id);

    User getUserByUsername(String username);
}
