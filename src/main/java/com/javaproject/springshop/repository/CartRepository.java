package com.javaproject.springshop.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.javaproject.springshop.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);

}
