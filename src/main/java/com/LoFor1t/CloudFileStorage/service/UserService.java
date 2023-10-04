package com.LoFor1t.CloudFileStorage.service;

import com.LoFor1t.CloudFileStorage.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(User userDto);

    User findByUsername(String username);

    List<User> findAllUsers();
}
