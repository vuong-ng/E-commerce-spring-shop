package com.javaproject.springshop.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaproject.springshop.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String role);
}
