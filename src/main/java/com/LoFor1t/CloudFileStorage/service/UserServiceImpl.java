package com.LoFor1t.CloudFileStorage.service;

import com.LoFor1t.CloudFileStorage.entity.Role;
import com.LoFor1t.CloudFileStorage.entity.User;
import com.LoFor1t.CloudFileStorage.repository.RoleRepository;
import com.LoFor1t.CloudFileStorage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {

        Role role = roleRepository.findByName("ROLE_USER");
        if (role == null) {
            role = checkRoleExist();
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(role));

        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();

    }

//    private UserDto mapToUserDto(User user) {
//        return UserDto
//                .builder()
//                .username(user.getUsername())
//                .build();
//    }

    private Role checkRoleExist() {
        Role role = Role.builder().name("ROLE_USER").build();
        return roleRepository.save(role);
    }

}
