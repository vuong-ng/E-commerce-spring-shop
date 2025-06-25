package com.javaproject.springshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaproject.springshop.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long>{

    void deleteAllByCartId(Long id);

}
