package com.javaproject.springshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaproject.springshop.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
