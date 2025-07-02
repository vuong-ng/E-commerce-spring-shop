package com.javaproject.springshop.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.javaproject.springshop.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    
}
