package com.javaproject.springshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaproject.springshop.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    boolean existsByName(String name);
}
