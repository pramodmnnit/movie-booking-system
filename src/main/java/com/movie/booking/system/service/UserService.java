package com.movie.booking.system.service;

import com.movie.booking.system.model.User;

public interface UserService {
    void saveUser(User user);

    User getUserById(String id);

    User getUserByUsername(String username);

}
