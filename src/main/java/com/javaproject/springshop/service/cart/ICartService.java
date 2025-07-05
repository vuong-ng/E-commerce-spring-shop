package com.javaproject.springshop.service.cart;

import java.math.BigDecimal;

import com.javaproject.springshop.model.Cart;
import com.javaproject.springshop.model.User;

public interface ICartService {
    Cart getCart(Long id);

    void clearCart(Long id);

    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User iuser);

    Cart getCartByUserId(Long userId);
}
