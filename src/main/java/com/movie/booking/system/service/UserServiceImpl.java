package com.movie.booking.system.service;

import com.movie.booking.system.model.User;
import com.movie.booking.system.repository.UserRepository;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        userRepository.saveUser(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
