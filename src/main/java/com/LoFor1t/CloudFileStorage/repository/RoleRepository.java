package com.LoFor1t.CloudFileStorage.repository;

import com.LoFor1t.CloudFileStorage.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
