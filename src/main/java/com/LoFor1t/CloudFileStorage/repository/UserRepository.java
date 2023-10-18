package com.LoFor1t.CloudFileStorage.repository;

import com.LoFor1t.CloudFileStorage.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Long getUserIdByUsername(String username);
}
